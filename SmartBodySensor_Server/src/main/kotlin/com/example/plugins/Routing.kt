package com.example.plugins

import com.example.database.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {
    routing {

        /* GET */
        get("/") {
            call.respond(dao.allEntries())

        }
        get("/{id}") {
            val id = call.parameters.getOrFail<Int>("id")
            val jsonEntry = dao.entry(id)
            call.respond(mapOf("entry" to jsonEntry))
        }

        get ("/reset") {
            dao.deleteAll()
            call.respond(HttpStatusCode.OK)
        }

        /* POST */
        post ("/") {
            val formParameters = call.receiveParameters()
            try {
                val index = formParameters.getOrFail("index").toInt()
                val tempDifference = formParameters.getOrFail("tempDifference").toDouble()
                val voltage = formParameters.getOrFail("voltage").toDouble()
                dao.addNewEntry(index, tempDifference, voltage)
            } catch (e: MissingRequestParameterException)
            {
                call.respond(HttpStatusCode.BadRequest)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
