import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.1.0"
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
}

description = "advent-of-code-2024"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)
    implementation(project(":aoc-utils"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

compose.desktop {
    application {
        mainClass = "org.hzt.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "physics-sim"
            packageVersion = "1.0.0"
        }
    }
}
