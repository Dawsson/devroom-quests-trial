package gg.dawson.quests.bukkit

import gg.dawson.quests.api.QuestAPI
import gg.dawson.quests.api.quests.db.MongoDatabase
import gg.dawson.quests.bukkit.commands.QuestCommands
import gg.dawson.quests.bukkit.listeners.BlockBreakListener
import gg.dawson.quests.bukkit.listeners.ConnectionListener
import gg.dawson.quests.bukkit.listeners.EntityDeathListener
import gg.dawson.quests.bukkit.listeners.QuestListener
import gg.flyte.twilight.Twilight
import gg.flyte.twilight.scheduler.async
import gg.flyte.twilight.twilight
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.runBlocking
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler
import kotlin.time.Duration.Companion.seconds

class BasicQuests : JavaPlugin() {

    companion object {
        val instance by lazy { Twilight.plugin as BasicQuests }
        lateinit var questAPI: QuestAPI
    }

    override fun onEnable() {
        twilight(this)
        questAPI = QuestAPI(MongoDatabase(config.getString("mongo-uri") ?: throw InvalidConfigurationException()))

        asyncLaunch {
            questAPI.startCacheTask(this, 10.seconds)
        }

        config.options().copyDefaults(true)
        saveDefaultConfig()

        BukkitCommandHandler.create(this).apply {
            register(QuestCommands())
            registerBrigadier()
        }

        BlockBreakListener()
        QuestListener()
        ConnectionListener()
        EntityDeathListener()
    }

    override fun onDisable() { runBlocking { questAPI.saveAll() } }

    fun asyncLaunch(block: suspend () -> Unit) = async { runBlocking { block.invoke() } }
}