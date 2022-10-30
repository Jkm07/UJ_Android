package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.*

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        name = row[Products.name],
        category = row[Products.category],
        country = row[Products.country]
    )
    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun product(id: Int): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addNewProduct(name: String, category: Int, country: String): Product? = dbQuery {
        val catId = Categories.select{Categories.id.eq(category)}.single()[Categories.id]
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.category] = catId
            it[Products.country] = country
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(id: Int, name: String, category: Int, country: String): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            it[Products.name] = name
            it[Products.category] = category
            it[Products.country] = country
        } > 0
    }

    override suspend fun deleteProduct(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }


    private fun resultRowToCategory(row: ResultRow) = Category(
        id = row[Categories.id],
        name = row[Categories.name],
        code = row[Categories.code],
        desc = row[Categories.desc]
    )
    override suspend fun allCategories(): List<Category> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    override suspend fun category(id: Int): Category? = dbQuery {
        Categories
            .select { Categories.id eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    override suspend fun addNewCategory(name: String, code: String, desc: String): Category? = dbQuery {
        val insertStatement = Categories.insert {
            it[Categories.name] = name
            it[Categories.code] = code
            it[Categories.desc] = desc
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCategory)
    }

    override suspend fun editCategory(id: Int, name: String, code: String, desc: String): Boolean = dbQuery {
        Categories.update({ Categories.id eq id }) {
            it[Categories.name] = name
            it[Categories.code] = code
            it[Categories.desc] = desc
        } > 0
    }

    override suspend fun deleteCategory(id: Int): Boolean = dbQuery {
        Categories.deleteWhere { Categories.id eq id } > 0
    }
}

val dao: DAOFacade = DAOFacadeImpl().apply {
    runBlocking {
        if(allCategories().isEmpty()) {
            addNewCategory("Warzywa", "veg-000", "Warzywa")
            addNewCategory("Nabiał", "dairy-000", "Nabiał")
        }
        if(allProducts().isEmpty()) {
            addNewProduct("Ziemniak", 1, "Skrobia")
            addNewProduct("Mleko", 2, "Białko")
            addNewProduct("Cebula", 1, "Warstwy")
        }
    }
}