package com.example.zadaniebazydanych

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.fragment.products.ProductListAdapter
import com.example.zadaniebazydanych.loginpage.LoginPage
import com.example.zadaniebazydanych.loginpage.SignUpPage
import com.example.zadaniebazydanych.mainpage.MainActivity
import com.google.android.material.tabs.TabLayout
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.core.Every
import org.junit.*

import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ProductTest {

    private var _categories =  retrofit2.Response.success(NetworkAdapter.CategoryArray(arrayOf(
                                NetworkAdapter.Category(1, "Category 1", "Code 1", "Desc 1"),
                                NetworkAdapter.Category(2, "Category 2", "Code 2", "Desc 2"),
                                NetworkAdapter.Category(3, "Category 3", "Code 3", "Desc 3"),
                                NetworkAdapter.Category(4, "Category 4", "Code 4", "Desc 4"))))
    private var _products = retrofit2.Response.success(NetworkAdapter.ProductArray(
        arrayOf(
            NetworkAdapter.Product(1,"Product 1 c1", 10, _categories.body()!!.categories[0], "Desc1"),
            NetworkAdapter.Product(2,"Product 2 c1", 100, _categories.body()!!.categories[0], "Desc2"),
            NetworkAdapter.Product(3,"Product 3 c1", 23, _categories.body()!!.categories[0], "Desc3"),
            NetworkAdapter.Product(4,"Product 4 c2", 10, _categories.body()!!.categories[1], "Desc4"),
            NetworkAdapter.Product(5,"Product 5 c2", 120, _categories.body()!!.categories[1], "Desc5"),
            NetworkAdapter.Product(6,"Product 6 c2", 30, _categories.body()!!.categories[1], "Desc6"),
            NetworkAdapter.Product(7,"Product 7 c3", 102, _categories.body()!!.categories[2], "Desc7"),
            NetworkAdapter.Product(8,"Product 8 c3", 1321, _categories.body()!!.categories[2], "Desc8"),
            NetworkAdapter.Product(9,"Product 9 c3", 112, _categories.body()!!.categories[2], "Desc9"),)
    ))

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()
    lateinit var decorView: View;

    @Before
    fun setUp()
    {
        activityScenarioRule.scenario.onActivity (ActivityScenario.ActivityAction<MainActivity> { activity ->
            decorView = activity.window.decorView
        })

        UserInfo.Username = "User"
        UserInfo.Type = "basic"

        mockkObject(NetworkAdapter)

        coEvery { NetworkAdapter.getCategories() } returns _categories

        coEvery { NetworkAdapter.getProducts() } returns _products

        coEvery { NetworkAdapter.getOrders() } returns retrofit2.Response.success(arrayOf())

    }

    @After
    fun finish()
    {
        unmockkAll()
    }

    @Test
    fun goToFirstProduct() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(0, click()))
            onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
    }

    @Test
    fun goToLastProduct() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(_products.body()!!.products.size - 1, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
    }

    @Test
    fun goToMidProduct() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(4, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
    }

    @Test
    fun goToFirstProductCheckData() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(0, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
        onView(withId(R.id.product_desc)).check(matches(withText(_products.body()!!.products[0].desc)))
    }

    @Test
    fun goToLastProductCheckData() {
        val idx = _products.body()!!.products.size
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.scrollToLastPosition<ProductListAdapter.ProductViewHolder>())
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(idx - 1, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
        onView(withId(R.id.product_desc)).check(matches(withText(_products.body()!!.products[idx - 1].desc)))
    }

    @Test
    fun goToMidProductCheckData() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(4, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
        onView(withId(R.id.product_desc)).check(matches(withText(_products.body()!!.products[4].desc)))
    }

    @Test
    fun goToMidProductAndBack() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(4, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.layout_main)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_products)).check(matches(isDisplayed()))
    }

    @Test
    fun goToFirstProductAndBack() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(0, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.layout_main)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_products)).check(matches(isDisplayed()))
    }

    @Test
    fun goToLastProductAndBack() {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(_products.body()!!.products.size - 1, click()))
        onView(withId(R.id.layout_product)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.layout_main)).check(matches(isDisplayed()))
        onView(withId(R.id.fragment_products)).check(matches(isDisplayed()))
    }
}