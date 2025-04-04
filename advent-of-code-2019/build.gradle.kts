import jdk.tools.jlink.resources.plugins

plugins {
    id("org.hzt.quizzes.java-conventions")
}

dependencies {
    api("org.jetbrains.kotlin:kotlin-stdlib:2.1.0")
    api("org.hzt.utils:core:1.0.5.22")
    api("org.hzt.utils:graph-utils:1.0.5.22")
    api(project(":aoc-utils"))
    testImplementation("org.jetbrains.kotlin:kotlin-test:2.1.0")
}

description = "Advent of Code 2019"
