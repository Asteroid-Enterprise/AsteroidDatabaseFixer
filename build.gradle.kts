plugins {
    kotlin("jvm") version "2.2.0"

    application
    alias(libs.plugins.shadow)
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation(libs.coroutines)

    // databases
    implementation(libs.sqlite)

    // third party
    implementation(libs.okhttp)
    implementation(libs.gson)
}

tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "me.serbob.asteroiddatabasefixer.MainKt"
    }

    archiveClassifier.set("")
    archiveVersion.set("")
}