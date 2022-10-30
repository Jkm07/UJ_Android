package com.example.plugins

import com.example.dao.dao
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*

fun Application.configureRouting() {

    routing {
        post("addProduct") {
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val category = formParameters.getOrFail<Int>("category")
            val country = formParameters.getOrFail("country")
            val product = dao.addNewProduct(name, category, country)
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
            call.respond(mapOf("products" to dao.allProducts()))
        }
        get ("categories"){
            call.respond(mapOf("categories" to dao.allCategories()))
        }
        put("product/{id}") {
            val id = call.parameters.getOrFail<Int>("id").toInt()
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val category = formParameters.getOrFail<Int>("category")
            val country = formParameters.getOrFail("country")
            dao.editProduct(id, name, category, country)
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

    }
}
