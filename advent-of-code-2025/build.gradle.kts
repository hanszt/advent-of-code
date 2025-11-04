plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.2.20"
}

description = "advent-of-code-2025"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":aoc-utils"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

kotlin {
    jvmToolchain(24)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}
