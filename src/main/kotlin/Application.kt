package com.delcom

import io.ktor.server.application.*
import io.ktor.server.netty.EngineMain

import com.delcom.data.todoModule
import org.koin.ktor.plugin.Koin

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS

import kotlinx.serialization.json.Json
import io.github.cdimascio.dotenv.dotenv

fun main(args: Array<String>) {

    // Load .env
    val dotenv = dotenv {
        directory = "."
        ignoreIfMissing = true // Railway tidak pakai .env file
    }

    dotenv.entries().forEach {
        System.setProperty(it.key, it.value)
    }

    // 👉 Ambil PORT dari Railway
    val port = System.getenv("PORT") ?: "8080"
    System.setProperty("ktor.deployment.port", port)

    EngineMain.main(args)
}

fun Application.module() {

    install(CORS) {
        anyHost()
    }

    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(Koin) {
        modules(todoModule)
    }

    configureRouting()
}
