rootProject.name = "advent-of-code"

include(":aoc-runner")

include(":advent-of-code-2019")
include(":advent-of-code-2020")
include(":advent-of-code-2021")
include(":advent-of-code-2022")
include(":advent-of-code-2023")
include(":advent-of-code-2024")
include(":advent-of-code-2025")

include(":downloader")
include(":aoc-utils")

// Necessary for compose
pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

    plugins {
        //`extra["..."]` looks in gradle.properties
        kotlin("jvm").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}

//https://docs.gradle.org/current/userguide/platforms.html#sub:version-catalog-declaration:~:text=conflict%20resolution.-,Declaring%20a%20version%20catalog,-Version%20catalogs%20can
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("compose-plugin", extra["compose.version"] as String)
            version("kotlin", extra["kotlin.version"] as String)
            plugin("jetbrainsCompose", "org.jetbrains.compose").versionRef("compose-plugin")
            plugin("kotlinMultiplatform", "org.jetbrains.kotlin.multiplatform").versionRef("kotlin")
            plugin("compose-compiler", "org.jetbrains.kotlin.plugin.compose").versionRef("kotlin")
        }
    }
}
