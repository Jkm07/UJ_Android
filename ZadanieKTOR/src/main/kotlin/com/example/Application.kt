package com.example

import Enviroment
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import com.example.dao.*

fun main(args: Array<String>) {
    EngineMain.main(args)
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}
fun Application.module() {
    Enviroment.stripeKey = environment.config.property("ktor.security.key").getString()
    Enviroment.secretKey = environment.config.property("ktor.security.secret_key").getString()
    DatabaseFactory.init()
    configureHTTP()
    configureSerialization()
    configureRouting()
}
