package com.example.zadaniebazydanych

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.loginpage.LoginPage
import com.example.zadaniebazydanych.loginpage.SignUpPage
import com.example.zadaniebazydanych.mainpage.MainActivity
import com.google.android.material.tabs.TabLayout
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.hamcrest.CoreMatchers.allOf
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
class SwitchFragmentTest {


    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Before
    fun setUp()
    {
        mockkObject(NetworkAdapter)
        UserInfo.Username = "User"
        UserInfo.Type = "basic"

        var c1 = NetworkAdapter.Category(1, "Category 1", "Code 1", "Desc 1")
        var c2 = NetworkAdapter.Category(2, "Category 2", "Code 2", "Desc 2")
        var c3 = NetworkAdapter.Category(3, "Category 3", "Code 3", "Desc 3")
        var c4 = NetworkAdapter.Category(4, "Category 4", "Code 4", "Desc 4")

        coEvery { NetworkAdapter.getCategories() } returns retrofit2.Response.success(NetworkAdapter.CategoryArray(
            arrayOf(c1, c2, c3, c4)
        ))

        coEvery { NetworkAdapter.getProducts() } returns retrofit2.Response.success(NetworkAdapter.ProductArray(
            arrayOf(NetworkAdapter.Product(1,"Product 1 c1", 10, c1, "Desc1"),
                NetworkAdapter.Product(2,"Product 2 c1", 100, c1, "Desc2"),
                NetworkAdapter.Product(3,"Product 3 c1", 23, c1, "Desc3"),
                NetworkAdapter.Product(4,"Product 4 c2", 10, c2, "Desc4"),
                NetworkAdapter.Product(5,"Product 5 c2", 120, c2, "Desc5"),
                NetworkAdapter.Product(6,"Product 6 c2", 30, c2, "Desc6"),
                NetworkAdapter.Product(7,"Product 7 c3", 102, c3, "Desc7"),
                NetworkAdapter.Product(8,"Product 8 c3", 1321, c3, "Desc8"),
                NetworkAdapter.Product(9,"Product 9 c3", 112, c3, "Desc9"),)
        ))

        coEvery { NetworkAdapter.getOrders() } returns retrofit2.Response.success(arrayOf())

        onView(withId(R.id.fragment_products)).check(matches(isDisplayed()))
    }

    @After
    fun finish()
    {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(0))
        onView(withId(R.id.fragment_products)).check(matches(isDisplayed()))
        unmockkAll()
    }

    @Test
    fun switch1() {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(1))
        onView(withId(R.id.fragment_basket)).check(matches(isDisplayed()))
    }

    @Test
    fun switch2() {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(2))
        onView(withId(R.id.fragment_category)).check(matches(isDisplayed()))
    }

    @Test
    fun switch3() {
        onView(withId(R.id.tablayout)).perform(selectTabAtPosition(3))
        onView(withId(R.id.fragment_orders)).check(matches(isDisplayed()))
    }

    private fun selectTabAtPosition(tabIndex: Int): ViewAction {
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

}