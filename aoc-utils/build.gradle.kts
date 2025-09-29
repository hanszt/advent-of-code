plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.2.20"
}

description = "Advent of Code utilities"

dependencies {
    // version provided by the kotlin plugin
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains:annotations:24.0.1")
}

kotlin {
    jvmToolchain(24)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}
