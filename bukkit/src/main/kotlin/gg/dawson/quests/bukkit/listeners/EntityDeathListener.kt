package gg.dawson.quests.bukkit.listeners

import gg.dawson.quests.bukkit.BasicQuests
import gg.dawson.quests.bukkit.QuestTypes
import gg.flyte.twilight.event.event
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent

class EntityDeathListener : Listener {

    init {
        event<EntityDeathEvent> {
            if (entity.killer is Player) {
                val player = entity.killer ?: return@event

                BasicQuests.questAPI.getQuests(player)
                    .filter { it.type == QuestTypes.ENTITY_SLAYER }
                    .filter { !it.isCompleted() }
                    .forEach { BasicQuests.questAPI.incrementProgress(player, it) }

            }
        }
    }

}