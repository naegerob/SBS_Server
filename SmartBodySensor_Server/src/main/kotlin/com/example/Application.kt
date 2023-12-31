package com.example

import com.example.dao.DatabaseFactory
import com.example.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    DatabaseFactory.init()
    //configureSecurity()
    //configureSockets()
    configureSerialization()
    //configureMonitoring()
    configureRouting()
}
