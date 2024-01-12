package gg.dawson.quests.bukkit.listeners

import gg.dawson.quests.bukkit.BasicQuests
import gg.dawson.quests.api.quests.model.QuestType
import gg.flyte.twilight.event.event
import net.kyori.adventure.text.Component.text
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {
    init {
        event<BlockBreakEvent> {
            BasicQuests.questAPI.getQuests(player)
                .filter { quest -> quest.type == QuestType.BLOCK_BREAK }
                .filter { quest -> !quest.isCompleted() }
                .forEach { quest ->
                    BasicQuests.questAPI.incrementProgress(player, quest) { success ->
                        if (success) {
                            Bukkit.broadcast(text("${player.name} completed ${quest.name}!"))
                            return@incrementProgress
                        }
                    }

                    Bukkit.broadcast(text("Player ${player.name} made progress ${quest.name}!"))
                }

        }
    }
}