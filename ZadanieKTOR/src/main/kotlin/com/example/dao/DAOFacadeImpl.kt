package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.*
import kotlinx.coroutines.runBlocking
import org.h2.util.ParserUtil.JOIN
import org.jetbrains.exposed.sql.*

class DAOFacadeImpl : DAOFacade {
    private fun resultRowToProduct(row: ResultRow) = Product(
        id = row[Products.id],
        name = row[Products.name],
        price = row[Products.price],
        category = row[Products.category],
        desc = row[Products.desc],
    )

    private fun resultRowToProductWithCategory(row: ResultRow) = ProductWithCategory(
        id = row[Products.id],
        name = row[Products.name],
        price = row[Products.price],
        category = Category(
            id = row[Categories.id],
            name = row[Categories.name] ,
            code = row[Categories.code],
            desc = row[Categories.desc]
        ),
        desc = row[Products.desc]
    )
    override suspend fun allProducts(): List<Product> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    override suspend fun allProductsWithCategory(): List<ProductWithCategory> = dbQuery {
        Join(Products, Categories, onColumn = Products.category, otherColumn = Categories.id, joinType = JoinType.LEFT).selectAll().map (:: resultRowToProductWithCategory)
    }

    override suspend fun product(id: Int): Product? = dbQuery {
        Products
            .select { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    override suspend fun addNewProduct(name: String, price: Int, category: Int, desc: String): Product? = dbQuery {
        val catId = Categories.select{Categories.id.eq(category)}.single()[Categories.id]
        val insertStatement = Products.insert {
            it[Products.name] = name
            it[Products.price] = price
            it[Products.category] = catId
            it[Products.desc] = desc
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToProduct)
    }

    override suspend fun editProduct(id: Int, name: String, price: Int, category: Int, desc: String): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            it[Products.name] = name
            it[Products.price] = price
            it[Products.category] = category
            it[Products.desc] = desc
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
            addNewCategory("Jedzenie", "veg-000", "Rzeczy do jedzenia")
            addNewCategory("Zabawki", "dairy-000", "Bioincle")
            addNewCategory("Samochody", "dairy-000", "brum brum")
            addNewCategory("nieaktywna", "dairy-000", "")
        }
        if(allProducts().isEmpty()) {
            addNewProduct("Ziemniak", 2, 1, "бульба")
            addNewProduct("Ogór", 3, 1, "Kiszony")
            addNewProduct("Pierogi", 14, 1, "ruskie")

            addNewProduct("tahu", 50, 2, "żywioł: ogien")
            addNewProduct("vakama", 40, 2, "żywioł: ogien")
            addNewProduct("kongu", 75, 2, "żywioł: ruskie")

            addNewProduct("SP30", 200000, 3, "ferrari")
            addNewProduct("AMG", 50000, 3, "mercedes")
            addNewProduct("maluch", 2000, 3, "fiat")
        }
    }
}