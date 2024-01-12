package gg.dawson.quests.api.quests.db

import gg.dawson.quests.api.quests.model.Quest
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface QuestDatabase {

    suspend fun getQuests(playerUUID: String): Flow<Quest>
    suspend fun saveQuests(playerUUID: String, quests: List<Quest>)

}