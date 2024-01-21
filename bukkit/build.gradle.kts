plugins {
    alias(libs.plugins.run.paper)
    alias(libs.plugins.kotlinx.serialization)
}

tasks.runServer {
    minecraftVersion("1.20.4")
}

dependencies {
    compileOnly(libs.paper)
    implementation(projects.questsApi)

    implementation(libs.twilight)
    implementation(libs.lamp.common)
    implementation(libs.lamp.bukkit)
    implementation(libs.kmongo)

    implementation(libs.kotlinx.coroutines.core)
    implementation(kotlin("reflect"))
}


