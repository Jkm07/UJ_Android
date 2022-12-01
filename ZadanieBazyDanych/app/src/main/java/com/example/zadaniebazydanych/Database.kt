package com.example.zadaniebazydanych

import android.util.Log
import com.example.zadaniebazydanych.fragment.basket.BasketFragment
import com.example.zadaniebazydanych.model.BasketItem
import com.example.zadaniebazydanych.model.Product
import com.example.zadaniebazydanych.model.Category
import com.example.zadaniebazydanych.model.User
import io.realm.kotlin.InitialDataCallback
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.isValid
import io.realm.kotlin.ext.query
import io.realm.kotlin.types.RealmUUID

object Database {
    val conf = RealmConfiguration.Builder(schema = setOf(Product::class, Category::class, BasketItem::class, User::class)).name("moja_baza").build()
    var realm: Realm = Realm.open(conf);
    lateinit var basket: BasketFragment;
    init {
        if(getProducts().isEmpty()) {
            realm.writeBlocking {
                val c1 = Category().apply {
                    name = "Jedzenie"
                    desc = "Rzeczy do jedzenia"
                    isActive = true
                }
                val c2 = Category().apply {
                    name = "Zabawki"
                    desc = "Bioincle"
                    isActive = true
                }
                val c3 = Category().apply {
                    name = "Samochody"
                    desc = "brum brum"
                    isActive = true
                }
                val c4 = Category().apply {
                    name = "nieaktywna"
                    desc = ""
                    isActive = false
                }
                val c1ap = copyToRealm(c1)
                val c2ap = copyToRealm(c2)
                val c3ap = copyToRealm(c3)
                val c4ap = copyToRealm(c4)

                val c1p1 = Product().apply {
                    name = "Ziemnior"
                    desc = "бульба"
                    category = c1ap
                }

                val c1p2 = Product().apply {
                    name = "Ogór"
                    desc = "Kiszony"
                    category = c1ap
                }

                val c1p3 = Product().apply {
                    name = "Pierogi"
                    desc = "ruskie"
                    category = c1ap
                }

                val c2p1 = Product().apply {
                    name = "tahu"
                    desc = "żywioł: ogien"
                    category = c2ap
                }

                val c2p2 = Product().apply {
                    name = "vakama"
                    desc = "żywioł: woda"
                    category = c2ap
                }

                val c2p3 = Product().apply {
                    name = "kongu"
                    desc = "żwyioł: ruskie"
                    category = c2ap
                }

                val c3p1 = Product().apply {
                    name = "SP30"
                    desc = "ferrari"
                    category = c3ap
                }

                val c3p2 = Product().apply {
                    name = "AMG"
                    desc = "mercedes"
                    category = c3ap
                }

                val c3p3 = Product().apply {
                    name = "maluch"
                    desc = "fiat"
                    category = c3ap
                }
                copyToRealm(c1p1)
                copyToRealm(c1p2)
                copyToRealm(c1p3)

                copyToRealm(c2p1)
                copyToRealm(c2p2)
                copyToRealm(c2p3)

                copyToRealm(c3p1)
                copyToRealm(c3p2)
                copyToRealm(c3p3)
            }
        }
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

    fun getProduct(index: Int): Product {
        return realm.query<Product>().find()[index]
    }

    fun getProduct(id: RealmUUID): Product {
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

    fun getCategory(index: Int): Category {
        return realm.query<Category>().find()[index]
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

    fun resetDatabase() {
        realm.close()
        Realm.deleteRealm(realm.configuration)
        realm = Realm.open(conf)
    }

}