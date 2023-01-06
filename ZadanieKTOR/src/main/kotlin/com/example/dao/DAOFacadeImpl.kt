package com.example.dao

import com.example.dao.DatabaseFactory.dbQuery
import com.example.models.*
import kotlinx.coroutines.runBlocking
import org.h2.util.ParserUtil.JOIN
import org.jetbrains.exposed.sql.*
import java.text.SimpleDateFormat
import java.util.*

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

    private fun resultRowToUser(row: ResultRow) = User(
        username = row[Users.username],
        email = row[Users.email],
        password = row[Users.password],
        myToken = row[Users.myToken],
        googleToken = row[Users.googleToken],
        gitToken = row[Users.gitToken]
    )

    private fun resultRowToUseShort(row: ResultRow) = UserShort(
        username = row[Users.username],
        email = row[Users.email],
        myToken = row[Users.myToken]
    )

    private fun resultRowToOrderSend(row: ResultRow) = OrderSend(
        email = row[Orders.email],
        realName = row[Orders.realName],
        address = row[Orders.address],
        date = row[Orders.date],
        count = row[Orders.count],
        priceForOne = row[Orders.priceForOne],
        product = row[Orders.product],
        isSucceed = row[Orders.isSucceed]
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

    override suspend fun allUsers(): List<User> = dbQuery {
        Users.selectAll().map(::resultRowToUser)
    }
    override suspend fun addUser(username: String, email: String, password: String): Boolean {
        val count = dbQuery { Users.select{ Users.email eq email}.count()}
        if(count > 0) return false;

        return dbQuery {
            val insertStatment = Users.insert {
                it[Users.username] = username
                it[Users.email] = email
                it[Users.password] = password
                it[Users.myToken] = ""
                it[Users.googleToken] = ""
                it[Users.gitToken] = ""
            }
            insertStatment.resultedValues?.isEmpty() == false
        }
    }

    override suspend fun getUserShort(email: String): UserShort? = dbQuery {
        Users
            .select { Users.email eq email }
            .map(::resultRowToUseShort)
            .singleOrNull()
    }

    override suspend fun getUser(email: String): User? = dbQuery {
        Users
            .select { Users.email eq email }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun generateToken(email: String): Int = dbQuery {
        Users.update( { Users.email eq email }) {
            it[Users.myToken] = getRandomString()
        }
    }

    override suspend fun saveGitToken(email: String, token: String): Int = dbQuery {
        Users.update( { Users.email eq email }) {
            it[Users.gitToken] = token
        }
    }

    override suspend fun saveGoogleToken(email: String, token: String): Int = dbQuery {
        Users.update( { Users.email eq email }) {
            it[Users.googleToken] = token
        }
    }

    override suspend fun getPriceOfOrder(order: Order): Int {
        return order.basket.sumOf { (product(it.id)?.price ?: 0) * it.count }
    }

    override suspend fun allOrders(): List<OrderSend> = dbQuery {
        Orders.selectAll().map(::resultRowToOrderSend)
    }

    override suspend fun addNewOrder(
        email: String,
        realName: String,
        address: String,
        count: Int,
        price: Int,
        product: Int,
        isSucceed: Boolean
    ): OrderSend? = dbQuery{
        val insertStatment = Orders.insert {
            it[Orders.email] = email
            it[Orders.realName] = realName
            it[Orders.address] = address
            it[Orders.count] = count
            it[Orders.priceForOne] = price
            it[Orders.date] = getDate()
            it[Orders.product] = product
            it[Orders.isSucceed] = isSucceed
        }
        insertStatment.resultedValues?.singleOrNull()?.let(::resultRowToOrderSend)
    }

    private fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..32)
            .map { allowedChars.random() }
            .joinToString("")
    }

    private fun getDate(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val date = Date()
        return formatter.format(date)
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