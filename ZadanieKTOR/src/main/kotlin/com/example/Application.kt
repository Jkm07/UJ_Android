package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.dao.*
import io.ktor.http.*

import io.ktor.server.plugins.cors.CORS

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(CORS){
        allowHost("0.0.0.0:8080")
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Delete)
    }
    DatabaseFactory.init()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
