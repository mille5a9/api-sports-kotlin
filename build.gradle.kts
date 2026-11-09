plugins {
    id("org.jetbrains.dokka")
}

// Aggregated API reference for every module: ./gradlew :dokkaGenerate -> build/dokka/html
dependencies {
    dokka(project(":api-sports-core"))
    dokka(project(":api-nba-kotlin"))
}

dokka {
    moduleName = "api-sports-kotlin"
}
