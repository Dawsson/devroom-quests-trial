plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin", "2.0.0-Beta2")
    implementation("com.github.johnrengelman", "shadow", "8.1.1")
}