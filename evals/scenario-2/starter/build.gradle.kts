plugins {
    kotlin("jvm") version "1.9.23"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    // No HTTP client declared yet
    // No logging declared yet
}

application {
    mainClass.set("AudioMeterKt")
}
