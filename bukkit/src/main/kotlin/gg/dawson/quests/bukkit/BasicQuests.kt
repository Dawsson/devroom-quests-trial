package gg.dawson.quests.bukkit

import gg.dawson.quests.api.QuestAPI
import gg.dawson.quests.bukkit.commands.QuestCommands
import gg.dawson.quests.bukkit.listeners.BlockBreakListener
import gg.dawson.quests.bukkit.listeners.ConnectionListener
import gg.dawson.quests.bukkit.listeners.EntityDeathListener
import gg.dawson.quests.bukkit.listeners.QuestListener
import gg.dawson.quests.api.quests.db.MongoDatabase
import gg.flyte.twilight.Twilight
import gg.flyte.twilight.scheduler.async
import gg.flyte.twilight.twilight
import kotlinx.coroutines.runBlocking
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.bukkit.BukkitCommandHandler

class BasicQuests : JavaPlugin() {

    companion object {
        val instance by lazy { Twilight.plugin as BasicQuests }
        lateinit var questAPI: QuestAPI
    }

    override fun onEnable() {
        twilight(this)
        questAPI = QuestAPI(MongoDatabase(config.getString("mongo-uri")!!))

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

    fun asyncLaunch(block: suspend () -> Unit) = async { runBlocking { block.invoke() } }
}