package com.example.zadaniebazydanych

import android.view.View
import androidx.test.core.app.ActivityScenario
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
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.fragment.basket.BasketAdapter
import com.example.zadaniebazydanych.fragment.products.ProductListAdapter
import com.example.zadaniebazydanych.mainpage.MainActivity
import com.example.zadaniebazydanych.payments.StriperHandler
import com.google.android.material.tabs.TabLayout
import io.mockk.*
import net.bytebuddy.matcher.ElementMatchers.any
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.Matchers
import org.junit.*

import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BasketTest {

    private var _categories =  retrofit2.Response.success(NetworkAdapter.CategoryArray(arrayOf(
                                NetworkAdapter.Category(1, "Category 1", "Code 1", "Desc 1"),
                                NetworkAdapter.Category(2, "Category 2", "Code 2", "Desc 2"),
                                NetworkAdapter.Category(3, "Category 3", "Code 3", "Desc 3"),
                                NetworkAdapter.Category(4, "Category 4", "Code 4", "Desc 4"),
                                NetworkAdapter.Category(5, "Category 5", "Code 5", "Desc 5"),
                                NetworkAdapter.Category(6, "Category 6", "Code 6", "Desc 6"),
                                NetworkAdapter.Category(7, "Category 7", "Code 7", "Desc 7"),
                                NetworkAdapter.Category(8, "Category 8", "Code 8", "Desc 8"),
                                NetworkAdapter.Category(9, "Category 9", "Code 9", "Desc 9"),
                                NetworkAdapter.Category(10, "Category 10", "Code 10", "Desc 10"),
                                NetworkAdapter.Category(11, "Category 11", "Code 11", "Desc 11"),
                                NetworkAdapter.Category(12, "Category 12", "Code 12", "Desc 12")
    )))
    val p1 = NetworkAdapter.Product(1,"Product 1 c1", 10, _categories.body()!!.categories[0], "Desc1")
    val p2 = NetworkAdapter.Product(2,"Product 2 c1", 100, _categories.body()!!.categories[0], "Desc2")
    val p3 = NetworkAdapter.Product(3,"Product 3 c1", 23, _categories.body()!!.categories[0], "Desc3")
    private var _products = retrofit2.Response.success(NetworkAdapter.ProductArray(
        arrayOf(
            p1,
            p2,
            p3,
            NetworkAdapter.Product(4,"Product 4 c2", 10, _categories.body()!!.categories[1], "Desc4"),
            NetworkAdapter.Product(5,"Product 5 c2", 120, _categories.body()!!.categories[1], "Desc5"),
            NetworkAdapter.Product(6,"Product 6 c2", 30, _categories.body()!!.categories[1], "Desc6"),
            NetworkAdapter.Product(7,"Product 7 c3", 102, _categories.body()!!.categories[2], "Desc7"),
            NetworkAdapter.Product(8,"Product 8 c3", 1321, _categories.body()!!.categories[2], "Desc8"),
            NetworkAdapter.Product(9,"Product 9 c3", 112, _categories.body()!!.categories[2], "Desc9"),
            NetworkAdapter.Product(10,"Product 10 c3", 112, _categories.body()!!.categories[2], "Desc10"),
            NetworkAdapter.Product(11,"Product 11 c3", 112, _categories.body()!!.categories[2], "Desc11"),
            NetworkAdapter.Product(12,"Product 12 c3", 112, _categories.body()!!.categories[2], "Desc12"),
            NetworkAdapter.Product(13,"Product 13 c3", 112, _categories.body()!!.categories[2], "Desc13"),
            NetworkAdapter.Product(14,"Product 14 c3", 112, _categories.body()!!.categories[2], "Desc14"),)
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
        UserInfo.Token = "token"
        UserInfo.Email = "email@email"

        mockkObject(NetworkAdapter)

        coEvery { NetworkAdapter.getCategories() } returns _categories

        coEvery { NetworkAdapter.getProducts() } returns _products

        coEvery { NetworkAdapter.getOrders() } returns retrofit2.Response.success(arrayOf())

        coEvery { NetworkAdapter.insertProduct(any(), any(), any(), any()) } returns Unit

        coEvery { NetworkAdapter.makeOrder(any(), any(), "good", "good", any()) } returns true

        coEvery { NetworkAdapter.makeOrder(any(), any(), "bad", any(), any()) } returns false

        coEvery { NetworkAdapter.makeOrder(any(), any(), "", any(), any()) } returns false

        coEvery { NetworkAdapter.makeOrder(any(), any(), "good", "", any()) } returns false

        coEvery { NetworkAdapter.makeOrder(any(), any(), any(), any(), any()) } returns false

        mockkObject(StriperHandler)

        coEvery { StriperHandler.handleOperation(any(),any(), any(),any()) } returns Unit;

    }

    @After
    fun finish()
    {
        //unmockkAll()
    }

    @Test
    fun goToBasket()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
    }

    @Test
    fun goToBasketAndClickBuy()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
    }

    @Test
    fun goToBasketAndClickBuyAndBack()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
    }

    @Test
    fun goToBasketAndClickBuyBackNotCancel()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
    }

    @Test
    fun goToBasketAddItem()
    {
        onView(withId(R.id.recyclerViewProducts)).inRoot(
            RootMatchers.withDecorView(
                Matchers.`is`(
                    decorView
                )
            )
        )
            .perform(RecyclerViewActions.actionOnItemAtPosition<ProductListAdapter.ProductViewHolder>(0, clickOnViewChild(R.id.add_button)))
        onView(withId(R.id.fragment_products)).check(matches(isDisplayed()))
    }

    @Test
    fun goToBasketConfirmOrder()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
        onView(withId(R.id.realName)).perform(ViewActions.typeText("good"))
        onView(withId(R.id.address)).perform(ViewActions.typeText("good"))
        onView(withId(R.id.button_confirm)).perform(click())
        onView(withText("Operation done")).inRoot(
            RootMatchers.withDecorView(
                Matchers.not(
                    decorView
                )
            )
        ).check(matches(isDisplayed()));
    }

    @Test
    fun goToBasketOrderRejected()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
        onView(withId(R.id.realName)).perform(ViewActions.typeText("bad"))
        onView(withId(R.id.address)).perform(ViewActions.typeText("good"))
        onView(withId(R.id.button_confirm)).perform(click())
        onView(withText("Operation failed")).inRoot(
            RootMatchers.withDecorView(
                Matchers.not(
                    decorView
                )
            )
        ).check(matches(isDisplayed()));
    }

    @Test
    fun goToBasketOrderRejectedEmptyUsername()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
        onView(withId(R.id.button_buy)).perform(click())
        onView(withId(R.id.layout_makeOrder)).check(matches(isDisplayed()))
        onView(withId(R.id.realName)).perform(ViewActions.typeText(""))
        onView(withId(R.id.address)).perform(ViewActions.typeText("good"))
        onView(withId(R.id.button_confirm)).perform(click())
        onView(withText("Operation failed")).inRoot(
            RootMatchers.withDecorView(
                Matchers.not(
                    decorView
                )
            )
        ).check(matches(isDisplayed()));
    }


    fun selectTabAtPosition(tabIndex: Int): ViewAction {
        return object : ViewAction {
            override fun getDescription() = "with tab at index $tabIndex"

            override fun getConstraints() = allOf(isDisplayed(), isAssignableFrom(TabLayout::class.java))

            override fun perform(uiController: UiController, view: View) {
                val tabLayout = view as TabLayout
                val tabAtIndex: TabLayout.Tab = tabLayout.getTabAt(tabIndex)
                    ?: throw PerformException.Builder()
                        .withCause(Throwable("No tab at index $tabIndex"))
                        .build()

                tabAtIndex.select()
            }
        }
    }

    fun clickOnViewChild(viewId: Int) = object : ViewAction {
        override fun getConstraints() = null

        override fun getDescription() = "Click on a child view with specified id."

        override fun perform(uiController: UiController, view: View) = click().perform(uiController, view.findViewById<View>(viewId))
    }
}