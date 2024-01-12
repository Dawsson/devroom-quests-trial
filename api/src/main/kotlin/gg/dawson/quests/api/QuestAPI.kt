package gg.dawson.quests.api

import com.google.common.cache.CacheBuilder
import gg.dawson.quests.api.quests.db.MongoDatabase
import gg.dawson.quests.api.quests.db.QuestDatabase
import gg.dawson.quests.api.quests.events.QuestCompleteEvent
import gg.dawson.quests.api.quests.model.Quest
import it.unimi.dsi.fastutil.Hash
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import org.bukkit.entity.Player

class QuestAPI(private val database: QuestDatabase) {

    val quests = HashMap<Player, ArrayList<Quest>>()

    fun getQuests(player: Player): ArrayList<Quest> {
        return quests[player] ?: ArrayList<Quest>().also {
            quests[player] = it
        }
    }

    fun giveQuest(player: Player, quest: Quest) = getQuests(player).add(quest)

    suspend fun saveQuests(player: Player) {
        quests[player]?.let { database.saveQuests(player.uniqueId.toString(), it) }
    }

    suspend fun loadPlayer(player: Player) {
        quests[player] = database.getQuests(player.uniqueId.toString())
            .toList() as ArrayList<Quest>

    }

    fun incrementProgress(player: Player, quest: Quest, amount: Int = 1, callback: (Boolean) -> Unit = {}) {
        quest.currentCount += amount
        if (quest.currentCount >= quest.maxCount) {
            quest.currentCount = quest.maxCount
            quest.completionDate = System.currentTimeMillis()

            QuestCompleteEvent(player, quest).callEvent()
            callback(true)
        }

        callback(false)
    }

}