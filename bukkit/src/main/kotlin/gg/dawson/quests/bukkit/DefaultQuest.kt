package gg.dawson.quests.bukkit

import gg.dawson.quests.api.quests.model.Quest

enum class DefaultQuest(val quest: Quest) {
    BLOCK_BREAK_QUEST(
        Quest(
            name = "Block Breaker",
            level = 1,
            description = "Break blocks!",
            type = QuestTypes.BLOCK_BREAKER,
            maxCount = 10
        )
    ),
    ENTITY_KILL_QUEST(
        Quest(
            name = "Entity Slayer",
            level = 2,
            description = "Kill the entities!",
            type = QuestTypes.ENTITY_SLAYER,
            maxCount = 3
        )
    ),
    NO_CACHE_ENTITY_KILL_QUEST(
        Quest(
            name = "Entity UNCACHED Slayer",
            level = 2,
            description = "Kill the entities! BUT UNCACHED!!!!",
            type = QuestTypes.ENTITY_SLAYER,
            maxCount = 5,
            shouldCache = false
        )
    )
}

object QuestTypes {
    val BLOCK_BREAKER = "Block Breaker"
    val ENTITY_SLAYER = "Entity Slayer"
}
