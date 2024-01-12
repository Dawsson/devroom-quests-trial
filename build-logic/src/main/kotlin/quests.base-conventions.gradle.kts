plugins {
    id("org.jetbrains.kotlin.jvm")
    `java-library`
}

tasks {
    // Variable replacements
    processResources {
        filesMatching(listOf("plugin.yml")) {
            expand("version" to project.version, "description" to project.description)
        }
    }
    javadoc {
        options.encoding = Charsets.UTF_8.name()
        (options as StandardJavadocDocletOptions).addStringOption("Xdoclint:none", "-quiet")
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.compilerArgs.addAll(listOf("-nowarn", "-Xlint:-unchecked", "-Xlint:-deprecation"))
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    test {
        useJUnitPlatform()
    }
}