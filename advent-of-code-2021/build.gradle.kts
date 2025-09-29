plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.2.20"
}

description = "Advent of Code 2021"

dependencies {
    implementation(project(":aoc-utils"))
    implementation("org.hzt.utils:graph-utils:1.0.5.21")
}

kotlin {
    jvmToolchain(24)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all", "-Xwhen-guards")
    }
}
