plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.1.0"
}

description = "Advent of Code 2022"

dependencies {
    implementation(project(":aoc-utils"))
    implementation("org.hzt.utils:graph-utils:1.0.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}
