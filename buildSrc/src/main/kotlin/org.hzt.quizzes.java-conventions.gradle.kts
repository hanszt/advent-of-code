import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult
import kotlin.io.path.Path
import kotlin.io.path.exists

plugins {
    `java-library`
    `maven-publish`
}

group = "org.hzt.quizzes"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_24

val junitVersion = "6.0.0"
val kotestVersion = "5.9.1"
val hztUtilsVersion = "1.0.5.21"

dependencies {
    implementation("org.hzt.utils:core:$hztUtilsVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation("org.junit.platform:junit-platform-commons:$junitVersion")
    testImplementation("org.junit.platform:junit-platform-engine:$junitVersion")
    testImplementation("org.junit.platform:junit-platform-launcher:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
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

    maven {
        name = "github"
        url = uri("https://maven.pkg.github.com/hanszt/*")
        credentials {
            val (username, password) = readMavenCredentials("github")
            this.username = username
            this.password = password
        }
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    test {
        useJUnitPlatform()
        maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        println("maxParallelForks = $maxParallelForks")
    }
    withType<Javadoc> {
        options.encoding = "UTF-8"
    }
    compileJava {
        options.encoding = "UTF-8"
        options.compilerArgumentProviders.add(CommandLineArgumentProvider {

            // Provide compiled Kotlin classes to javac – needed for Java/Kotlin mixed sources to work
            listOf(
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
}

private fun readMavenCredentials(id: String): Credentials {
    project.logger.lifecycle("Reading credentials for $id from maven settings.xml")
    val settingsFile = Path(System.getProperty("user.home"), ".m2/settings.xml")
    if (!settingsFile.exists()) {
        error("Maven SettingsFile not found")
    }
    try {
        val parser = XmlSlurper()
        val settings = parser.parse(settingsFile)
        val credentials = settings.breadthFirst().asSequence()
            .filterIsInstance<GPathResult>()
            .first { id in it.text() }
            .breadthFirst().asSequence()
            .filterIsInstance<GPathResult>()
            .filter { it.name() == "username" || it.name() == "password" }
            .toList()
        if (credentials.size == 2) {
            return Credentials(credentials[0].text(), credentials[1].text())
        }
    } catch (e: Exception) {
        // Log the error for debugging
        project.logger.lifecycle("Error parsing settings.xml: ${e.message}")
    }
    error("Could not find credentials for $id in settingsFile")
}

private data class Credentials(val username: String, val password: String)

java {
    //https://kotlinlang.org/docs/gradle-configure-project.html#configure-with-java-modules-jpms-enabled
    modularity.inferModulePath.set(true)
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(24))
    }
}
