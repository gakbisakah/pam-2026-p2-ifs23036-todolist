val koin_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val dotenv_version: String by project

plugins {
    kotlin("jvm") version "2.3.0"
    id("io.ktor.plugin") version "3.3.2"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.21"

    // 👉 TAMBAHAN: Untuk bikin FAT JAR
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.delcom"
version = "0.0.1"

application {
    // 👉 Pastikan sesuai package
    mainClass.set("com.delcom.ApplicationKt")
}

dependencies {

    // Koin
    implementation("io.insert-koin:koin-ktor:$koin_version")
    implementation("io.insert-koin:koin-logger-slf4j:$koin_version")

    // Ktor
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.ktor:ktor-server-cors")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-host-common")
    implementation("io.ktor:ktor-server-status-pages")

    // Client
    implementation("io.ktor:ktor-client-content-negotiation")

    // Logging
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Env
    implementation("io.github.cdimascio:dotenv-kotlin:$dotenv_version")

    // Test
    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

/*
 👉 Konfigurasi ShadowJar
 Biar hasilnya: build/libs/app-all.jar
*/
tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
    archiveBaseName.set("app")
    archiveClassifier.set("all")
    archiveVersion.set("")
    mergeServiceFiles()
}
