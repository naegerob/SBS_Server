package com.example.plugins

import com.google.gson.GsonBuilder
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import java.text.DateFormat

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        gson {
            /**
             * gson defines formats etc how to send over JSON
             */
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
}
