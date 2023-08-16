package com.eduardoseguro.themoviedb

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.eduardoseguro.themoviedb.CustomAssertions.Companion.hasItemCount
import com.eduardoseguro.themoviedb.CustomAssertions.Companion.selectMovieByPosition
import com.eduardoseguro.themoviedb.adapter.MovieCategoryAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MovieTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun upDownScroll() {
        Thread.sleep(2000)
        onView(withId(R.id.listCategories))
            .perform(swipeUp())

        onView(withId(R.id.listCategories))
            .perform(swipeDown())
    }

    @Test
    fun leftRightScroll() {
        Thread.sleep(2000)
        onView(withId(R.id.listCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieCategoryAdapter.CategoryHolder>(
                    1,
                    swipeLeft()
                )
            )

        onView(withId(R.id.listCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieCategoryAdapter.CategoryHolder>(
                    0,
                    swipeLeft()
                )
            )

        onView(withId(R.id.listCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieCategoryAdapter.CategoryHolder>(
                    0,
                    swipeRight()
                )
            )
    }

    @Test
    fun checkListSize() {
        Thread.sleep(1000)
        onView(withId(R.id.listCategories))
            .check(hasItemCount(4))
    }

    @Test
    fun selectFirstMovie() {
        Thread.sleep(2500)
        onView(withId(R.id.listCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieCategoryAdapter.CategoryHolder>(
                    0,
                    selectMovieByPosition(0)
                )
            )
    }

    @Test
    fun openAndCloseMovieDetails() {
        Thread.sleep(2000)
        onView(withId(R.id.listCategories))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieCategoryAdapter.CategoryHolder>(
                    2,
                    selectMovieByPosition(3)
                )
            )
        Thread.sleep(1500)
        onView(withId(R.id.imageDetailsBackArrow))
            .perform(click())
        Thread.sleep(1500)
    }

}