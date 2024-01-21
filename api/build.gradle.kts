plugins {
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(libs.paper)

    implementation(libs.twilight)
    implementation(libs.lamp.common)
    implementation(libs.lamp.bukkit)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kmongo)
    implementation(kotlin("reflect"))
}


