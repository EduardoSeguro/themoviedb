package com.eduardoseguro.themoviedb

import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.eduardoseguro.themoviedb.adapter.MovieAdapter
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.any
import org.hamcrest.Matcher


class CustomAssertions {
    companion object {
        fun hasItemCount(count: Int): ViewAssertion {
            return RecyclerViewItemCountAssertion(count)
        }

        fun selectMovieByPosition(position: Int): ViewAction {
            return ClickMovieInPosition(position)
        }

        fun typeTextSearchView(query: String): ViewAction {
            return ChangeSearchViewText(query)
        }
    }

    private class RecyclerViewItemCountAssertion(private val count: Int) : ViewAssertion {

        override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
            if (noViewFoundException != null) {
                throw noViewFoundException
            }

            if (view !is RecyclerView) {
                throw IllegalStateException("The asserted view is not RecyclerView")
            }

            if (view.adapter == null) {
                throw IllegalStateException("No adapter is assigned to RecyclerView")
            }

            ViewMatchers.assertThat(
                "RecyclerView item count",
                view.adapter?.itemCount,
                CoreMatchers.equalTo(count)
            )
        }
    }

    private class ClickMovieInPosition(val position: Int) : ViewAction {
        override fun getDescription(): String {
            return ""
        }

        override fun getConstraints(): Matcher<View> {
            return any(View::class.java)
        }

        override fun perform(uiController: UiController?, view: View) {
            val recyclerView = (view as ConstraintLayout).getChildAt(1)
            actionOnItemAtPosition<MovieAdapter.MovieHolder>(position, click()).perform(
                uiController,
                recyclerView
            )
        }
    }

    private class ChangeSearchViewText(val text: String) : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
        }

        override fun getDescription(): String {
            return ""
        }

        override fun perform(
            uiController: UiController,
            view: View
        ) {
            (view as SearchView).setQuery(text, false)
        }
    }
}