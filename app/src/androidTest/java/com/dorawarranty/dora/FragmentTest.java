package com.dorawarranty.dora;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class FragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);


    @Test
    public void warrantyListExist() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.warrantyListRecycler)).check(matches(isDisplayed()));
    }

    @Test
    public void warrantyUnitClick() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.warrantyListRecycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));
    }

    @Test
    public void warrantyUnitManufacturerText() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.warrantyListRecycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productManufacturer)).check(matches(withText(containsString("Производитель"))));
    }

    @Test
    public void warrantyProductWarrantyEnd() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.warrantyListRecycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productWarrantyEnd)).check(matches(withText(containsString("Дата окончания"))));
    }

    @Test
    public void warrantyButtonCheck() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.warrantyListRecycler)).perform(RecyclerViewActions
                .actionOnItemAtPosition(0, click()));
        onView(withId(R.id.productButton)).check(matches(isClickable()));
    }

}
