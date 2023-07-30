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

        /* POST */
        post ("/") {
            val formParameters = call.receiveParameters()
            println("CALLED!")
            try {
                val index = formParameters.getOrFail("index").toInt()
                val tempDifference = formParameters.getOrFail("tempDifference").toDouble()
                val voltage = formParameters.getOrFail("voltage").toDouble()
                println("$index $tempDifference $voltage")
                dao.addNewEntry(index, tempDifference, voltage)
            } catch (e: MissingRequestParameterException)
            {
                call.respond(HttpStatusCode.BadRequest)
            }
            call.respond(HttpStatusCode.OK)
        }
    }
}
