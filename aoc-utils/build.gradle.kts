plugins {
    id("org.hzt.quizzes.java-conventions")
    kotlin("jvm") version "2.1.0"
}

dependencies {
    // version provided by the kotlin plugin
    api("org.jetbrains.kotlin:kotlin-stdlib")
    api("org.jetbrains:annotations:24.0.1")
}

description = "Advent of Code utilities"

kotlin {
    jvmToolchain(21)
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xjvm-default=all")
    }
}

java {
    //https://kotlinlang.org/docs/gradle-configure-project.html#configure-with-java-modules-jpms-enabled
    modularity.inferModulePath.set(true)
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgumentProviders.add(CommandLineArgumentProvider {
        // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
        listOf("--patch-module", "advent.of.code.utils=${sourceSets["main"].output.asPath}")
    })
}
