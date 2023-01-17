package com.example.zadaniebazydanych

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zadaniebazydanych.auth.UserInfo
import com.example.zadaniebazydanych.loginpage.LoginPage
import com.example.zadaniebazydanych.loginpage.SignUpPage
import io.mockk.coEvery
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import retrofit2.Response
import retrofit2.Retrofit

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginAuthTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<LoginPage>()

    @Before
    fun setUp()
    {
        mockkObject(NetworkAdapter)
        coEvery { NetworkAdapter.authUser("Correct", "Correct") } answers {UserInfo.Username = "Correct"; UserInfo.Type = "basic"; true};
        coEvery { NetworkAdapter.authUser("Incorrect", "Incorrect") } returns false;


        coEvery { NetworkAdapter.getCategories() } returns retrofit2.Response.success(NetworkAdapter.CategoryArray(
            arrayOf()
        ))

        coEvery { NetworkAdapter.getProducts() } returns retrofit2.Response.success(NetworkAdapter.ProductArray(
            arrayOf()
        ))

        coEvery { NetworkAdapter.getOrders() } returns retrofit2.Response.success(arrayOf())
        onView(ViewMatchers.withId(R.id.layout_loginPage)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @After
    fun finish()
    {
        unmockkAll()
    }

    @Test
    fun signUpCorrect() {
        onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("Correct"))
        onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("Correct"))
        onView(ViewMatchers.withId(R.id.button_signIn)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.layout_main)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }

    @Test
    fun signUpIncorrect() {
        onView(ViewMatchers.withId(R.id.email)).perform(ViewActions.typeText("Incorrect"))
        onView(ViewMatchers.withId(R.id.password)).perform(ViewActions.typeText("Incorrect"))
        onView(ViewMatchers.withId(R.id.button_signIn)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.layout_loginPage)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        )
    }
}