package com.delcom

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.statuspages.*


import com.delcom.controllers.TodoController
import com.delcom.data.AppException
import com.delcom.data.ErrorResponse
import com.delcom.helpers.parseMessageToMap
import io.ktor.server.http.content.staticResources
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val todoController: TodoController by inject()

    install(StatusPages) {
        // Tangkap AppException
        exception<AppException> { call, cause ->
            val dataMap: Map<String, List<String>> = parseMessageToMap(cause.message)

            call.respond(
                status = HttpStatusCode.fromValue(cause.code),
                message = ErrorResponse(
                    status = "fail",
                    message = if (dataMap.isEmpty()) cause.message else "Data yang dikirimkan tidak valid!",
                    data = dataMap
                )
            )
        }

        // Tangkap semua Throwable lainnya
        exception<Throwable> { call, cause ->
            call.respond(
                status = HttpStatusCode.fromValue(500),
                message = ErrorResponse(
                    status = "error",
                    message = cause.message ?: "Unknown error",
                )
            )
        }
    }

    routing {
        get("/") {
            call.respondText("11S23041 - Firman B Hutasoit")
        }
        staticResources("/content", "content")

        route("/todos") {
            get {
                todoController.getAllTodos(call)
            }
            post {
                todoController.createTodo(call)
            }
            get("/{id}") {
                todoController.getTodoById(call)
            }
            put("/{id}") {
                todoController.updateTodo(call)
            }
            delete("/{id}") {
                todoController.deleteTodo(call)
            }
        }

    }
}
