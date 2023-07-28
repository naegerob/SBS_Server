package com.example.plugins

import com.example.database.dao
import freemarker.cache.ClassTemplateLoader
import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    routing {

        get("/") {
            //call.respond("Gugus")
            call.respond(dao.allEntries())
            //call.respond(FreeMarkerContent("index.ftl", mapOf("jsonentries" to dao.allEntries())))

        }



        post ("/") {
            //val jsonString = call.receive<String>()
            println("POST")
            val formParameters = call.receiveParameters()

            val index = formParameters.getOrFail("index").toInt()
            val tempDifference = formParameters.getOrFail("tempDifference").toDouble()
            val voltage = formParameters.getOrFail("voltage").toDouble()
            val jsonEntries = dao.addNewEntry(index, tempDifference, voltage)

            call.respondRedirect("/jsonentries/${jsonEntries?.id}")
            call.respond(formParameters)

/*
            val formParameters = call.receive<String>()
            call.respond(formParameters)
*/
        }
        // With {} you can extract Values in URL whereas the string within here
        // as index counts and the value can be read by using call.parameter
        post("{id}/{id1}") {
            val id = call.parameters.getOrFail<Int>("id")

            if (call.parameters["id"] == "0")
            {
                println("Zero received")
            }
            if(call.parameters["id1"] == "1")
            {
                println("one received")
            }
            println(id)
            //val formParameters = call.receiveParameters()
            //val formParameters = call.receive<String>()

            //call.respond(formParameters)

        }
        // similar to path parameters.
        // The paramName is given in the URL whereas it is assigned
        // by a value/string ?paramName=ghjk
        post("/query") {
            val name = call.request.queryParameters["paramName"]
            println(name)
        }


        get("/json/gson") {
            call.respond(mapOf("hello" to "world"))

        }

        get("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("entry" to dao.entry(id)))
        }

        get("{id}/edit") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(FreeMarkerContent("edit.ftl", mapOf("entry" to dao.entry(id))))
        }




    }
}
