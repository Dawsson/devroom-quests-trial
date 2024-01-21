package gg.dawson.quests.api.quests.events

import gg.dawson.quests.api.quests.model.Quest
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList
import java.util.*

class QuestCompleteEvent(
    val uuid: UUID,
    val quest: Quest,
) : Event() {
    companion object {
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    fun getPlayer() = Bukkit.getPlayer(uuid)

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}