plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.3.0"
}

description = "advent-of-code-2023"

dependencies {
    implementation(project(":aoc-utils"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

kotlin {
    jvmToolchain(25)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}
