plugins {
    // Automatically downloads the JDK requested by jvmToolchain() if it is not installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.FAIL_ON_PROJECT_REPOS
    repositories {
        mavenCentral()
    }
}

rootProject.name = "api-sports-kotlin"

include(
    "api-sports-core",
    "api-nba-kotlin",
    "api-nfl-kotlin",
)
