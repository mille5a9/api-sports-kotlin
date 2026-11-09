// Command-line demo of the clients. Not published.
plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jlleitschuh.gradle.ktlint")
    application
}

kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(project(":api-nba-kotlin"))
    implementation(project(":api-nfl-kotlin"))
    runtimeOnly(libs.slf4j.nop)
}

application {
    mainClass = "io.github.mille5a9.apisports.samples.MainKt"
}
