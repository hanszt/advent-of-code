plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.3.0"
}

description = "Advent of Code 2020"

dependencies {
    implementation(project(":aoc-utils"))
    implementation("org.hzt.utils:graph-utils:1.0.6.25")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}
