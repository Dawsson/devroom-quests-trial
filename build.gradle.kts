plugins {
    base
    id("quests.build-logic")
}

allprojects {
    group = "gg.dawson"
    version = property("projectVersion") as String
    description = "Trial plugin for devroom"
}

val main = setOf(
//    projects.quests,
    projects.questsApi,
    projects.questsBukkit,
).map { it.dependencyProject }

// val special = setOf().map { it.dependencyProject }

subprojects {
    when (this) {
        in main -> plugins.apply("quests.shadow-conventions")
        // in special -> plugins.apply("quests.base-conventions")
        else -> plugins.apply("quests.standard-conventions")
    }
}
