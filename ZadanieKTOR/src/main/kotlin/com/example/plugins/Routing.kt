package com.example.plugins

import com.example.dao.dao
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {

    routing {
        post("addProduct") {
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val price = formParameters.getOrFail<Int>("price")
            val category = formParameters.getOrFail<Int>("category")
            val desc = formParameters.getOrFail("desc")
            val product = dao.addNewProduct(name, price, category, desc)
            call.respondRedirect("/product/${product?.id}")
        }
        post("addCategory") {
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val code = formParameters.getOrFail("code")
            val desc = formParameters.getOrFail("desc")
            val category = dao.addNewCategory(name, code, desc)
            call.respondRedirect("/category/${category?.id}")
        }
        get("product/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("product" to dao.product(id)))
        }
        get("category/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            call.respond(mapOf("category" to dao.category(id)))
        }
        get ("products"){
            call.respond(mapOf("products" to dao.allProductsWithCategory()))
        }
        get ("categories"){
            call.respond(mapOf("categories" to dao.allCategories()))
        }
        put("product/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val price = formParameters.getOrFail("price")
            val category = formParameters.getOrFail<Int>("category")
            val desc = formParameters.getOrFail("desc")
            dao.editProduct(id, name, price.toInt(), category, desc)
            call.respondRedirect("/product/$id")
        }
        put("category/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val code = formParameters.getOrFail("code")
            val desc = formParameters.getOrFail("desc")
            dao.editCategory(id, name, code, desc)
            call.respondRedirect("/category/$id")
        }
        delete("product/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            dao.deleteProduct(id)
            call.respondRedirect("/products")
        }
        delete("category/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            dao.deleteCategory(id)
            call.respondRedirect("/categories")
        }
        post("addUser") {
            val formParameters = call.receiveParameters()
            val username = formParameters.getOrFail("username")
            val email = formParameters.getOrFail("email")
            val password = formParameters.getOrFail("password")
            val result = dao.addUser(username, email, password)
            if(result) call.respond(HttpStatusCode.Accepted,"User has been created")
            else call.respond(HttpStatusCode.Conflict, "User already exists")
        }
        get("users") {
            call.respond(mapOf("users" to dao.allUsers()))
        }
        post("authBasicUser") {
            val formParameters = call.receiveParameters()
            val email = formParameters.getOrFail("email")
            val password = formParameters.getOrFail("password")
            val user = dao.getUser(email)
            if(user?.password == password) {
                dao.generateToken(email)
                call.respond(mapOf("user" to dao.getUserShort(email)))
            }
            else call.respond(HttpStatusCode.Conflict, "")
        }
        post("authGitUser") {
            val formParameters = call.receiveParameters()
            val email = formParameters.getOrFail("email")
            val username = formParameters.getOrFail("username")
            val token = formParameters.getOrFail("token")
            val user = dao.getUser(email)
            if(user == null) {
                dao.addUser(username, email, "")
            }
            dao.generateToken(email)
            dao.saveGitToken(email, token)
            call.respond(mapOf("user" to dao.getUserShort(email)))
        }
        post("authGoogleUser") {
            val formParameters = call.receiveParameters()
            val email = formParameters.getOrFail("email")
            val username = formParameters.getOrFail("username")
            val token = formParameters.getOrFail("token")
            val user = dao.getUser(email)
            if(user == null) {
                dao.addUser(username, email, "")
            }
            dao.generateToken(email)
            dao.saveGoogleToken(email, token)
            call.respond(mapOf("user" to dao.getUserShort(email)))
        }
    }
}
