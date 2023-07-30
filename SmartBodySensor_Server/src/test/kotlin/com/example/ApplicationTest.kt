package com.example

import com.example.plugins.*
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import java.io.File
import java.io.FileNotFoundException
import java.util.ArrayList
import kotlin.test.*

class ApplicationTest {
    @OptIn(InternalAPI::class)
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/")
        {
            // Handle requests here!

        }.apply {
            // Handle responses here!
            assertEquals(HttpStatusCode.OK, status)
        }
        var jsonString = ""
        try {
            val fileName = "log_2.json"
            jsonString = File(fileName).readText(Charsets.UTF_8)

            val sType = object : TypeToken<ArrayList<JsonEntry>>(){}.type
            val jsonLog : ArrayList<JsonEntry> = Gson().fromJson<ArrayList<JsonEntry>>(jsonString, sType)

            println(jsonLog)
        } catch (e: FileNotFoundException)
        {
            e.printStackTrace()
        }
        client.post("/")
        {
            contentType(ContentType.Application.Json)
            body = jsonString
            //println(body)
        }.apply {
            println("CLIENT POST!")
            assertEquals(HttpStatusCode.OK, status)
            println(bodyAsText())
            assertEquals(jsonString, bodyAsText())
        }
        client.put {

        }.apply {

        }
    }
}
