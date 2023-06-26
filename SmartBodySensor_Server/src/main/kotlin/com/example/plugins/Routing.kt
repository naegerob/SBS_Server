package com.example.plugins

import io.ktor.client.statement.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.*

fun Application.configureRouting() {
    routing {
        get("/") {

            call.respondText("Hello World!")

            println(call.request)
            println(call.application)
            println(call.attributes)
        }
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))
        }
        post ("/") {
            val jsonString = call.receive<String>()
            call.respondText("TEST")

        }

    }
}
