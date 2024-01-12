package gg.dawson.quests.bukkit.listeners

import gg.dawson.quests.api.quests.events.QuestCompleteEvent
import gg.flyte.twilight.event.event
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.event.Listener

class QuestListener : Listener {

    init {
        event<QuestCompleteEvent> {
            Bukkit.broadcast(
                text("Congrats to ")
                    .append(player.displayName().color(NamedTextColor.BLUE))
                    .append(text(" for completing the "))
                    .append(text("${quest.name} (Level: ${quest.level}) quest!"))
            )
        }
    }

}