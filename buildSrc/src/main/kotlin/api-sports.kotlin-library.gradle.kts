// Shared configuration for every library module in this repository.
plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("org.jetbrains.dokka")
    id("org.jlleitschuh.gradle.ktlint")
}

val libs = the<VersionCatalogsExtension>().named("libs")

kotlin {
    jvmToolchain(17)
    explicitApi()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(libs.findLibrary("ktor-client-mock").get())
    testImplementation(libs.findLibrary("kotlinx-coroutines-test").get())
}

tasks.test {
    useJUnitPlatform()
}

dokka {
    dokkaSourceSets.configureEach {
        sourceLink {
            localDirectory = rootDir
            remoteUrl("https://github.com/mille5a9/api-sports-kotlin/tree/master")
        }
    }
}

ktlint {
    filter {
        exclude { it.file.path.contains("/build/generated/") }
    }
}
