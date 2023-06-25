package com.example

import com.example.plugins.*
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileReader
import java.util.ArrayList
import kotlin.test.*


class ApplicationTest {
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
            assertEquals("Hello World!", bodyAsText())
        }
        try {
            val fileName = "log_2.json"
            val applicationDirectory = System.getProperty("user.dir")

            val jsonString = File(fileName).readText(Charsets.UTF_8)

            val sType = object : TypeToken<ArrayList<JsonEntry>>(){}.type
            val jsonLog : ArrayList<JsonEntry> = Gson().fromJson<ArrayList<JsonEntry>>(jsonString, sType)

            println(jsonLog)
        } catch (e: FileNotFoundException)
        {
            e.printStackTrace()
        }
        client.post("/")
        {
            this.setBody()
            this.headers {

            }

        }
        .apply {

        }



    }
}
