package gg.dawson.quests.api.quests.events

import gg.dawson.quests.api.quests.model.Quest
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

class QuestCompleteEvent(
    val player: Player,
    val quest: Quest,
) : Event() {
    companion object {
        val HANDLERS = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return HANDLERS
        }
    }

    override fun getHandlers(): HandlerList {
        return HANDLERS
    }
}