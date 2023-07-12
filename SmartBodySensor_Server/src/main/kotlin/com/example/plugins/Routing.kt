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
        post("{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            //val formParameters = call.receiveParameters()
            val formParameters = call.receive<String>()
            /*val index = formParameters.getOrFail("index").toInt()
            val tempDifference = formParameters.getOrFail("tempDifference").toDouble()
            val voltage = formParameters.getOrFail("voltage").toDouble()

            if(dao.existsEntry(id)) {
                dao.editEntry(id, index, tempDifference, voltage)
            } else {
                dao.addNewEntry(index, tempDifference, voltage)
            }
            when (formParameters.getOrFail("_action")) {
                "update" -> {

                    dao.editEntry(id, index, tempDifference, voltage)
                    call.respondRedirect("/jsonentries/$id")
                }
                "delete" -> {
                    dao.deleteEntry(id)
                    call.respondRedirect("/jsonentries")
                }
            }
            */
            call.respond(formParameters)

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
