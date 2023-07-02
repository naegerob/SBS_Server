package com.example.plugins

import io.ktor.client.statement.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.util.pipeline.*
import com.example.database.dao
import io.ktor.server.util.*
import io.ktor.freemarker.FreeMarkerContent

fun Application.configureRouting() {
    routing {
        get("/") {

            call.respondText("Hello World!")

            println(call.request)
            println(call.application)
            println(call.attributes)
            call.respond(FreeMarkerContent("index.ftl", mapOf("jsonentries" to dao.allEntries())))
        }
        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))





        }
        post ("/") {
            val jsonString = call.receive<String>()
            call.respondText("TEST")

            val formParameters = call.receiveParameters()
            val index = formParameters.getOrFail("index").toInt()
            val tempDifference = formParameters.getOrFail("tempDifference").toDouble()
            val voltage = formParameters.getOrFail("voltage").toDouble()
            val jsonEntries = dao.addNewEntry(index, tempDifference, voltage)
            call.respondRedirect("/jsonentries/${jsonEntries?.id}")

        }

    }
}
