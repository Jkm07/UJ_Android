package com.example.zadaniebazydanych

import android.util.Log
import com.example.zadaniebazydanych.fragment.basket.BasketFragment
import com.example.zadaniebazydanych.fragment.categories.CategoriesFragment
import com.example.zadaniebazydanych.fragment.orders.OrdersFragment
import com.example.zadaniebazydanych.fragment.products.ProductsFragment
import com.example.zadaniebazydanych.model.BasketItem
import com.example.zadaniebazydanych.model.Product
import com.example.zadaniebazydanych.model.Category
import com.example.zadaniebazydanych.model.Order
import com.example.zadaniebazydanych.model.User
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID
import kotlinx.coroutines.runBlocking

object Database {
    val conf = RealmConfiguration.Builder(schema = setOf(Product::class, Category::class, BasketItem::class, User::class, Order::class)).name("moja_baza").build()
    var realm: Realm = Realm.open(conf);
    lateinit var basket: BasketFragment;
    lateinit var productList: ProductsFragment
    lateinit var categoryList: CategoriesFragment
    lateinit var ordersList: OrdersFragment

    fun downloadDataFromServer() {
        var products: List<Product> = emptyList()
        var categories: List<Category> = emptyList()
        var orders: List<Order> = emptyList()
        runBlocking {
            try {
                val responseProduct = NetworkAdapter.getProducts()
                    if(responseProduct.isSuccessful) {
                        products = responseProduct.body()?.products?.map {
                            Product().apply{
                                _id = it.id
                                name = it.name
                                price = it.price
                                desc = it.desc
                                category = Category().apply {
                                    _id = it.category.id
                                    name = it.name
                                    desc = it.desc
                                    isActive = true;
                                }
                            }
                        } ?: emptyList()

                    }
                    realm.writeBlocking {
                        for(product in products) {
                            copyToRealm(product, UpdatePolicy.ALL)
                        }
                    }
                    val responseCategory = NetworkAdapter.getCategories()
                    if(responseCategory.isSuccessful) {
                        categories = responseCategory.body()?.categories?.map {
                            Category().apply{
                                _id = it.id
                                name = it.name
                                desc = it.desc
                                isActive = true
                            }
                        } ?: emptyList()

                    }
                    realm.writeBlocking {
                        for(category in categories) {
                            copyToRealm(category, UpdatePolicy.ALL)
                        }
                    }
                val responseOrder = NetworkAdapter.getOrders()
                if(responseOrder.isSuccessful) {
                    orders = responseOrder.body()?.map {
                        Order().apply {
                            email = it.email
                            realName = it.realName
                            date = it.date
                            address = it.address
                            count = it.count
                            priceForOne = it.priceForOne
                            product = getProduct(it.product)
                            isSucceed = it.isSucceed
                        }
                    } ?: emptyList()
                }
                realm.writeBlocking {
                    for(order in orders) {
                        order.product = findLatest(order.product !!)
                        copyToRealm(order, UpdatePolicy.ALL)
                    }
                }
            }
            catch (Ex:Exception){
                Log.e("Error",Ex.localizedMessage)
            }
        }
        productList.Notify()
        categoryList.Notify()
        ordersList.Notify()
    }

    fun insertProduct(name: String, desc: String, category: Category?) {
        realm.writeBlocking {
            copyToRealm(Product().apply {
                this.name = name
                this.desc = desc
                this.category = category
            })
        }
    }

    fun insertProduct(product: Product) {
        realm.writeBlocking {
            copyToRealm(product)
        }
    }

    fun getProducts(): Array<Product> {
        return realm.query<Product>().find().toTypedArray()
    }

    fun getProduct(id: Int): Product {
        return realm.query<Product>("_id = $0", id).find()[0]
    }

    fun updateProduct(id: Int, name: String, desc: String, category: Category?) {
        realm.writeBlocking {
            val item = query<Product>("_id = $0", id).find().first()
            findLatest(item)?.name = name;
            findLatest(item)?.desc = desc;
            findLatest(item)?.category = category;
        }

    }

    fun deleteProduct(id: Int) {
        realm.writeBlocking {
            val item = query<Product>("_id = $0", id).find().first()
            delete(item)
        }
    }

    fun insertCategory(name: String, desc: String, isActive: Boolean){
        realm.writeBlocking {
            copyToRealm(Category().apply {
                this.name = name
                this.desc = desc
                this.isActive = isActive
            })
        }
    }

    fun insertCategory(category: Category){
        realm.writeBlocking {
            copyToRealm(category)
        }
    }

    fun getCategories(): Array<Category> {
        return realm.query<Category>().find().toTypedArray()
    }

    fun getCategory(id: Int): Category {
        return realm.query<Category>("_id = $0", id).find()[0];
    }

    fun updateCategory(id: Int, name: String, desc: String, isActive: Boolean) {
        realm.writeBlocking {
            val item = query<Category>("_id = $0", id).find().first()
            findLatest(item)?.name = name;
            findLatest(item)?.desc = desc;
            findLatest(item)?.isActive = isActive;
        }

    }

    fun deleteCategory(id: Int) {
        realm.writeBlocking {
            val item = query<Category>("_id = $0", id).find().first()
            delete(item)
        }
    }

    fun getBasket(): Array<BasketItem> {
        return realm.query<BasketItem>().find().toTypedArray()
    }

    fun insertItemToBasket(product: Product){
        realm.writeBlocking {
            val basketExist = query<BasketItem>("Product._id = $0", product._id).first().find()
            if(basketExist == null) {
                val latestProduct = findLatest(product)
                val basketItem = BasketItem().apply{
                    Product = latestProduct
                    isActive = true;
                }
                while(query<BasketItem>("_id == $0", basketItem._id).count().find() != 0L) basketItem._id = RealmUUID.random()
                copyToRealm(basketItem)
            }
            else {
                basketExist.count++
            }
        }
        basket.Notify()
    }

    fun deleteItemFromBasket(basketItem: BasketItem) {
        realm.writeBlocking {
            val latestBasket = findLatest(basketItem)
            if(latestBasket?.count?.compareTo(1) == 1) {
                latestBasket.count--
            }
            else {
                delete(latestBasket!!)
            }

        }
        basket.Notify()
    }

    fun getOrders(): Array<Order> {
        return realm.query<Order>().find().toTypedArray()
    }

    fun saveOrdersFromRemote() {
        var orders: List<Order> = emptyList()
        runBlocking {
            val responseOrder = NetworkAdapter.getOrders()
            if(responseOrder.isSuccessful) {
                orders = responseOrder.body()?.map {
                    Order().apply {
                        email = it.email
                        realName = it.realName
                        date = it.date
                        address = it.address
                        count = it.count
                        priceForOne = it.priceForOne
                        product = getProduct(it.product)
                        isSucceed = it.isSucceed
                    }
                } ?: emptyList()
                realm.writeBlocking {
                    for(order in orders) {
                        order.product = findLatest(order.product !!)
                        copyToRealm(order, UpdatePolicy.ALL)
                    }
                }
                ordersList.Notify()
            }
        }
    }

    fun deleteEntireBasket() {
        realm.writeBlocking {
            val basket = query<BasketItem>().find()
            delete(basket)
        }
        basket.Notify()
    }

    fun resetDatabase() {
        realm.close()
        Realm.deleteRealm(realm.configuration)
        realm = Realm.open(conf)
    }

}