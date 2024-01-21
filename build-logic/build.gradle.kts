plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-gradle-plugin", "1.9.20")
    implementation("com.github.johnrengelman", "shadow", "8.1.1")
    implementation(kotlin("reflect"))
}