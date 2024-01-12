package gg.dawson.quests.bukkit

import gg.dawson.quests.bukkit.BasicQuests
import gg.flyte.twilight.extension.addItems
import gg.flyte.twilight.extension.slot
import gg.flyte.twilight.inventory.GuiBuilder
import gg.flyte.twilight.itembuilder.ItemBuilder
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import kotlin.math.ceil
import kotlin.math.round

object QuestMenus {

    fun getQuestMenu(player: Player): GuiBuilder {
        val quests = BasicQuests.questAPI.getQuests(player)

        return GuiBuilder(
            text("Quests"),
            ceil(quests.size / 9.0).toInt() * 9,
        ) {
            inventory.apply {
                quests
                    .filter { !it.isCompleted() }
                    .forEach { quest ->
                        slot(
                            firstEmpty(),
                            ItemBuilder(
                                Material.BOOK,
                                name = text("${quest.name} (${quest.level})").color(NamedTextColor.BLUE)
                            ) {
                                lore = mutableListOf(
                                    text("- ${quest.description}").color(NamedTextColor.AQUA),
                                    text("- ${quest.currentCount}/${quest.maxCount}").color(NamedTextColor.AQUA)
                                )
                            }.build()
                        ) {
                            isCancelled = true
                        }
                    }
            }
        }
    }
}


