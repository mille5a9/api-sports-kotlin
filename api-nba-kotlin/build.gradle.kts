plugins {
    id("api-sports.kotlin-library")
    id("api-sports.publishing")
    id("com.google.devtools.ksp")
}

dependencies {
    api(project(":api-sports-core"))
    api(libs.autodsl.annotations)
    ksp(libs.autodsl.processor)
}
