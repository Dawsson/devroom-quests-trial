package gg.dawson.quests.api.quests.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Quest(
    @SerializedName("_id")
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val level: Int,
    val description: String,
    val type: QuestType,
    val maxCount: Int,
    var currentCount: Int = 0,
    var completionDate: Long? = null,
) {
    fun isCompleted() = completionDate != null
}
