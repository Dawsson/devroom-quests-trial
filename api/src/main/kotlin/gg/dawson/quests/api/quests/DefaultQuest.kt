package gg.dawson.quests.api.quests

import gg.dawson.quests.api.quests.model.Quest
import gg.dawson.quests.api.quests.model.QuestType

enum class DefaultQuest(val quest: Quest) {
    BLOCK_BREAK_QUEST(
        Quest(
            name = "Block Breaker",
            level = 1,
            description = "Break blocks!",
            type = QuestType.BLOCK_BREAK,
            maxCount = 10
        )
    ),
    ENTITY_KILL_QUEST(
        Quest(
            name = "Entity Slayer",
            level = 2,
            description = "Kill the entities!",
            type = QuestType.MOB_KILL,
            maxCount = 3
        )
    )
}
