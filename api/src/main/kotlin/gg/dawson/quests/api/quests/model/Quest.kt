package gg.dawson.quests.api.quests.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Quest(
    @SerializedName("_id")
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val level: Int,
    val description: String,
    val type: String,
    val maxCount: Int,
    var currentCount: Int = 0,
    var completionDate: Long? = null,
    val shouldCache: Boolean = true,
) {
    fun isCompleted() = completionDate != null
}
