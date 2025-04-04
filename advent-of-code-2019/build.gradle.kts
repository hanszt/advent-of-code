plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.1.0"
}

description = "Advent of Code 2019"

dependencies {
    implementation(project(":aoc-utils"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

//tasks.compileJava {
//    options.encoding = "UTF-8"
//    options.compilerArgumentProviders.add(object : CommandLineArgumentProvider {
//
//         Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
//        override fun asArguments() = listOf(
//            "--patch-module", "advent.of.code.utils=${sourceSets["main"].output.asPath}",
//            "--patch-module", "advent.of.code.twenty.twenty=${sourceSets["main"].output.asPath}",
//            "--patch-module", "advent.of.code.twenty.twenty.one=${sourceSets["main"].output.asPath}",
//            "--patch-module", "advent.of.code.twenty.twenty.two=${sourceSets["main"].output.asPath}",
//            "--patch-module", "advent.of.code.twenty.twenty.three=${sourceSets["main"].output.asPath}",
//            "--patch-module", "advent.of.code.twenty.twenty.four=${sourceSets["main"].output.asPath}"
//        )
//    })
//}
