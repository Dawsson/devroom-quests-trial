enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    // configures repositories for all projects
    repositories {
        maven("https://repo.flyte.gg/releases/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/repositories/snapshots/")
        maven("https://libraries.minecraft.net")
        mavenCentral()
        maven("https://jitpack.io")
    }
    // only use these repos
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
}

pluginManagement {
    plugins {
        id("net.kyori.blossom") version "2.1.0"
        id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
        id("com.github.johnrengelman.shadow") version "8.1.1"
        id("org.jetbrains.kotlin.jvm") version "2.0.0-Beta2"
        id("org.jetbrains.kotlin.plugin.serialization") version "2.0.0-Beta2"
    }
}

rootProject.name = "quests-parent"

includeBuild("build-logic")

setupPPSubproject("api")
setupPPSubproject("bukkit")

fun setupPPSubproject(name: String) {
    setupSubproject("quests-$name") {
        projectDir = file(name)
    }
}

inline fun setupSubproject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

