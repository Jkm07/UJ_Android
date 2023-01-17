package com.example.zadaniebazydanych

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.zadaniebazydanych.loginpage.LoginPage
import androidx.test.espresso.matcher.ViewMatchers.*

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class LoginNavigationTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<LoginPage>()


    @Test
    fun goToSignUpAndBack() {
        onView(ViewMatchers.withId(R.id.layout_loginPage)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_signUp)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.layout_signUpPage)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_cancel)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.layout_loginPage)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun goToSignUpAndPressBack() {
        onView(ViewMatchers.withId(R.id.layout_loginPage)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.button_signUp)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.layout_signUpPage)).check(matches(ViewMatchers.isDisplayed()))
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(ViewMatchers.withId(R.id.layout_loginPage)).check(matches(ViewMatchers.isDisplayed()))
    }
}