package com.example.zadaniebazydanych

import android.view.View
import androidx.test.core.app.ActivityScenario.ActivityAction
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zadaniebazydanych.loginpage.SignUpPage
import io.mockk.coEvery
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SignUpTest {

    @get:Rule var activityScenarioRule = activityScenarioRule<SignUpPage>()
    lateinit var decorView: View;


    @Before
    fun setUp()
    {
        activityScenarioRule.scenario.onActivity (ActivityAction<SignUpPage> { activity ->
            ConfigFileHandler.createProp(activity.applicationContext)
            decorView = activity.window.decorView
        })
        mockkObject(NetworkAdapter)
        coEvery { NetworkAdapter.insertUser("good", "good@email", "Password1234") } returns "Success"
        coEvery { NetworkAdapter.insertUser(any(), "existed@email", any()) } returns "Error - account exists"
        coEvery { NetworkAdapter.insertUser("", any(), any()) } returns "Error - username"
        coEvery { NetworkAdapter.insertUser("bad username", any(), any()) } returns "Error - username"
        coEvery { NetworkAdapter.insertUser(any(), "", any()) } returns "Error - email"
        coEvery { NetworkAdapter.insertUser(any(), "badEmailFormat", any()) } returns "Error - email"
        coEvery { NetworkAdapter.insertUser(any(), "bad Email Format", any()) } returns "Error - email"
        coEvery { NetworkAdapter.insertUser(any(), any(), "") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "1aA") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "1111111") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "AAAAAAA") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "aaaaaaa") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "1AAAAAA") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "1aaaaaa") } returns "Error - password"
        coEvery { NetworkAdapter.insertUser(any(), any(), "Aaaaaaa") } returns "Error - password"
    }

    @After
    fun finish()
    {
        onView(withId(R.id.layout_signUpPage)).check(matches(isDisplayed()))
        unmockkAll()
    }

    @Test
    fun createAccountSuccess() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Success")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountExisted() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("existed@email"))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - account exists")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountUsernameEmpty() {
        onView(withId(R.id.login)).perform(typeText(""))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - username")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountUsernameSpace() {
        onView(withId(R.id.login)).perform(typeText("bad username"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - username")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountEmailEmpty() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText(""))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - email")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountEmailBadFormat() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("badEmailFormat"))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - email")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountEmailSpace() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("bad Email Format"))
        onView(withId(R.id.password)).perform(typeText("Password1234"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - email")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordTooShort() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("1aA"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordOnlyNumbers() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("1111111"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordOnlyUpperCase() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("AAAAAAA"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordOnlyDownCase() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("aaaaaaa"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordNoNumbers() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("Aaaaaaa"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordNoUpperCase() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("1aaaaaa"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }

    @Test
    fun createAccountPasswordNoDownCase() {
        onView(withId(R.id.login)).perform(typeText("good"))
        onView(withId(R.id.email)).perform(typeText("good@email"))
        onView(withId(R.id.password)).perform(typeText("1AAAAAA"))
        onView(withId(R.id.button_signUp)).perform(click())
        onView(withText("Error - password")).inRoot(withDecorView(Matchers.not(decorView))).check(matches(isDisplayed()));
    }
}