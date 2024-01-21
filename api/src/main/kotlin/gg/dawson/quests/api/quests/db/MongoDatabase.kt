package gg.dawson.quests.api.quests.db

import gg.dawson.quests.api.quests.model.Quest
import gg.flyte.twilight.gson.GSON
import kotlinx.coroutines.flow.Flow
import org.bson.Document
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.serialization.SerializationClassMappingTypeService

class MongoDatabase(
    mongoURI: String
) : QuestDatabase {

    private val client by lazy { KMongo.createClient(mongoURI).coroutine }
    private val database by lazy { client.getDatabase("quests") }

    init {
        if (mongoURI.isEmpty()) {
            throw IllegalArgumentException("Mongo URI cannot be empty!")
        }

        System.setProperty(
            "org.litote.mongo.mapping.service",
            SerializationClassMappingTypeService::class.qualifiedName!!
        )
    }

    override suspend fun getQuests(playerUUID: String): Flow<Quest> = getCollection(playerUUID)
        .find()
        .toFlow()

    override suspend fun saveQuests(playerUUID: String, quests: List<Quest>) {
        if (!database.listCollectionNames().contains(playerUUID)) database.createCollection(playerUUID)

        database.getCollection<Document>(playerUUID).apply {
            quests.forEach { quest ->
                if (findOneById(quest.id) != null) replaceOneById(quest.id, quest.toDocument())
                else insertOne(quest.toDocument())
            }
        }
    }

    override suspend fun saveQuest(playerUUID: String, quest: Quest) {
        if (!database.listCollectionNames().contains(playerUUID)) database.createCollection(playerUUID)

        database.getCollection<Document>(playerUUID).apply {
            if (findOneById(quest.id) != null) replaceOneById(quest.id, quest.toDocument())
            else insertOne(quest.toDocument())
        }
    }

    private fun Quest.toDocument() = Document.parse(GSON.toJson(this))
    private fun getCollection(uuid: String) = database.getCollection<Quest>(uuid)
}