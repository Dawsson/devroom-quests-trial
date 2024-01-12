package gg.dawson.quests.bukkit.commands

import gg.dawson.quests.bukkit.BasicQuests
import gg.dawson.quests.bukkit.QuestMenus
import gg.dawson.quests.api.quests.DefaultQuest
import gg.flyte.twilight.gson.GSON
import net.kyori.adventure.text.Component.text
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.DefaultFor
import revxrsal.commands.annotation.Optional
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.annotation.Switch
import revxrsal.commands.bukkit.annotation.CommandPermission

@Command("quests")
class QuestCommands {

    @DefaultFor("~")
    fun questCommand(
        sender: Player
    ) {
        QuestMenus.getQuestMenu(sender).open(sender)
    }

    @Subcommand("list")
    @CommandPermission("quests.list")
    fun listCommand(
        sender: CommandSender,
        @Optional target: Player? = null,
        @Switch("json") json: Boolean = false
    ) {
        BasicQuests.instance.asyncLaunch {
            if (target == null) {
                if (json) {
                    sender.sendMessage(text(GSON.toJson(BasicQuests.questAPI.quests)))
                    return@asyncLaunch
                } else {
                    sender.sendMessage(text("Player not specified").color(NamedTextColor.DARK_RED))
                    return@asyncLaunch
                }

            }

            BasicQuests.questAPI.getQuests(target)
                .also {
                    if (it.isEmpty()) {
                        sender.sendMessage(text("You have no quests"))
                    }
                }.forEach {
                    sender.sendMessage(text("${it.name} - ${it.currentCount}/${it.maxCount}"))
                }

        }
    }

    @Subcommand("give")
    @CommandPermission("quests.give")
    fun giveCommand(
        sender: Player,
        quest: DefaultQuest,
        @Optional target: Player? = null
    ) {
        BasicQuests.instance.asyncLaunch {
            val targetPlayer = target ?: sender
            BasicQuests.questAPI.giveQuest(targetPlayer, quest.quest)

            sender.sendMessage(text("Quest has been given.").color(NamedTextColor.GREEN))
            if (targetPlayer != sender) {
                target?.sendMessage(text("You have been given a quest!").color(NamedTextColor.GREEN))
            }
        }
    }
}
