plugins {
    `java-library`
    `maven-publish`
}

group = "org.hzt.quizzes"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_21

val junitJupiterVersion = "5.11.3"
val kotestVersion = "5.9.1"
val hztUtilsVersion = "1.0.5.21"

dependencies {
    implementation("org.hzt.utils:core:$hztUtilsVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-shared-jvm:$kotestVersion")
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo1.maven.org/maven2/")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

java {
    //https://kotlinlang.org/docs/gradle-configure-project.html#configure-with-java-modules-jpms-enabled
    modularity.inferModulePath.set(true)
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.compilerArgumentProviders.add(object : CommandLineArgumentProvider {

        // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
        override fun asArguments() = listOf(
            "--patch-module", "advent.of.code.utils=${sourceSets["main"].output.asPath}",
            "--patch-module", "advent.of.code.twenty.nineteen=${sourceSets["main"].output.asPath}",
            "--patch-module", "advent.of.code.twenty.twenty=${sourceSets["main"].output.asPath}",
            "--patch-module", "advent.of.code.twenty.twenty.one=${sourceSets["main"].output.asPath}",
            "--patch-module", "advent.of.code.twenty.twenty.two=${sourceSets["main"].output.asPath}",
            "--patch-module", "advent.of.code.twenty.twenty.three=${sourceSets["main"].output.asPath}",
            "--patch-module", "advent.of.code.twenty.twenty.four=${sourceSets["main"].output.asPath}"
        )
    })
}
