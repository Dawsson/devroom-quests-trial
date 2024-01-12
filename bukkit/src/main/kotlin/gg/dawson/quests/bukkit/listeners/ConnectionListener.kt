package gg.dawson.quests.bukkit.listeners

import gg.dawson.quests.bukkit.BasicQuests
import gg.flyte.twilight.event.event
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class ConnectionListener : Listener {

    init {
        event<PlayerJoinEvent> {
            BasicQuests.instance.asyncLaunch {
                BasicQuests.questAPI.loadPlayer(player)
            }
        }

        event<PlayerQuitEvent> {
            BasicQuests.instance.asyncLaunch {
                BasicQuests.questAPI.saveQuests(player)
            }
        }
    }
}