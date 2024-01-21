package gg.dawson.quests.api

import gg.dawson.quests.api.quests.db.QuestDatabase
import gg.dawson.quests.api.quests.events.QuestCompleteEvent
import gg.dawson.quests.api.quests.model.Quest
import gg.flyte.twilight.Twilight
import gg.flyte.twilight.scheduler.async
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.toList
import org.bukkit.Bukkit
import org.bukkit.Warning
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.lang.Runnable
import java.util.UUID
import kotlin.time.Duration

class QuestAPI(val database: QuestDatabase) {

    var quests = HashMap<UUID, ArrayList<Quest>>()
    var updatedQuests = HashMap<UUID, HashSet<Quest>>()

    /**
     * @param delay seconds between each cache check
     */
    @OptIn(DelicateCoroutinesApi::class)
    suspend fun startCacheTask(plugin: JavaPlugin, delay: Duration) {
        GlobalScope.launch {
            while (true) {
                quests = quests.onEach { (uuid, playerQuests) ->

                    updatedQuests[uuid]?.let { database.saveQuests(uuid.toString(), it.toList()) }
                    updatedQuests.remove(uuid)

//                    playerQuests // Quests which should be stored in DB
//                        .filter { !it.shouldCache }
//                        .also { database.saveQuests(uuid.toString(), it) }

                    // quests[uuid] = playerQuests.apply { removeAll(questsToRemove.toSet()) }

                }

                delay(delay)
            }
        }
    }

    fun getQuests(uuid: UUID): ArrayList<Quest> = quests[uuid] ?: ArrayList<Quest>().also { quests[uuid] = it }
    fun getQuests(player: Player): ArrayList<Quest> = getQuests(player.uniqueId)

    fun giveQuest(uuid: UUID, quest: Quest) = getQuests(uuid).add(quest)
    fun giveQuest(player: Player, quest: Quest) = getQuests(player).add(quest)

    suspend fun saveQuests(uuid: UUID) = quests[uuid]?.let { database.saveQuests(uuid.toString(), it) }.also {
        println("Saved quests for ${uuid.toString()}")
    }

    suspend fun saveQuests(player: Player) = saveQuests(player.uniqueId)

    suspend fun saveQuest(uuid: UUID, quest: Quest) = database.saveQuest(uuid.toString(), quest).also {
        println("Saved quest ${quest.id} for ${uuid.toString()}")
    }

    suspend fun saveQuest(player: Player, quest: Quest) = saveQuest(player.uniqueId, quest)

    suspend fun saveAll() = quests.forEach { (uuid, _) -> saveQuests(uuid) }

    suspend fun loadPlayer(uuid: UUID) {
        quests[uuid] = database.getQuests(uuid.toString())
            .toList() as ArrayList<Quest>
    }

    suspend fun loadPlayer(player: Player) = loadPlayer(player.uniqueId)

    @OptIn(DelicateCoroutinesApi::class)
    fun incrementProgress(uuid: UUID, quest: Quest, amount: Int = 1, callback: (Boolean) -> Unit = {}) {
        quest.currentCount += amount
        updatedQuests[uuid]?.add(quest) ?: updatedQuests.put(uuid, hashSetOf(quest))

        if (!quest.shouldCache) {
            GlobalScope.launch {
                database.saveQuest(uuid.toString(), quest)
            }
        }

        if (quest.currentCount >= quest.maxCount) {
            quest.currentCount = quest.maxCount
            quest.completionDate = System.currentTimeMillis()

            QuestCompleteEvent(uuid, quest).callEvent()

            callback(true)
        }

        callback(false)
    }

    fun incrementProgress(player: Player, quest: Quest, amount: Int = 1, callback: (Boolean) -> Unit = {}) =
        incrementProgress(player.uniqueId, quest, amount, callback)

    fun forceComplete(uuid: UUID, quest: Quest) =
        incrementProgress(uuid, quest, quest.maxCount - quest.currentCount)

    fun forceComplete(player: Player, quest: Quest) = forceComplete(player.uniqueId, quest)
}