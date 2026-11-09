plugins {
    id("api-sports.kotlin-library")
    id("api-sports.publishing")
}

dependencies {
    api(libs.ktor.client.core)
    api(libs.kotlinx.serialization.json)
    implementation(libs.ktor.client.cio)
}
