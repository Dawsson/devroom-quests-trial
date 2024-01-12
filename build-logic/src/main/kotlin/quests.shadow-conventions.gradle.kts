import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.jvm.tasks.Jar

plugins {
    id("quests.base-conventions")
    id("com.github.johnrengelman.shadow")
}

tasks {
    named<Jar>("jar") {
        archiveClassifier.set("unshaded")
        from(project.rootProject.file("LICENSE"))
    }
    val shadowJar = named<ShadowJar>("shadowJar") {
        archiveClassifier.set("")
        configureRelocations()
        minimize()
    }
    named("build") {
        dependsOn(shadowJar)
    }
}

fun ShadowJar.configureRelocations() {
    relocate("com.google.gson", "gg.dawson.quests.libs.gson")
    relocate("io.papermc.lib", "gg.dawson.quests.libs.paperlib")
    relocate("gg.flyte.twilight", "gg.dawson.quests.libs.twilight")
}