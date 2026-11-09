// Publishing to Maven Central through the Central Portal (https://central.sonatype.com).
// Coordinates and POM metadata come from gradle.properties (root and per-module).
plugins {
    id("com.vanniktech.maven.publish")
}

mavenPublishing {
    publishToMavenCentral()

    // Sign only when a key is configured, so `publishToMavenLocal` works on any machine.
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }
}
