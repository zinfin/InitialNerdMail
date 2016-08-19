package com.bignerdranch.android.nerdmail;

import android.support.design.internal.NavigationMenuItemView;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;
import com.bignerdranch.android.nerdmail.controller.DrawerActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.isA;
import static android.support.test.espresso.action.ViewActions.click;
/**
 * Created by sand8529 on 8/18/16.
 */
@RunWith(AndroidJUnit4.class)
public class DrawerActivityTest {
  @Rule
  public ActivityTestRule<DrawerActivity> mActivityRule = new ActivityTestRule<>(DrawerActivity.class);

  @Test
  public void userSeeInboxFirst(){
    String inboxText = mActivityRule.getActivity().getString(R.string.nav_drawer_inbox);
    onView(allOf(withText(inboxText),
        withParent(isAssignableFrom(Toolbar.class))))
        .check(matches(isDisplayed()));
  }
  @Test
  public void inboxItemSelectedFirstInNavigationDrawer(){
    String inboxText = mActivityRule.getActivity().getString(R.string.nav_drawer_inbox);
    //open nav drawer
    DrawerActions.openDrawer(R.id.activity_drawer_layout);
    //check that inbox item is checked
    onView(allOf(withText(inboxText),
        withParent(isAssignableFrom(NavigationMenuItemView.class))))
        .check(matches(isChecked()));
  }
  @Test
  public void selectingImportantItemShowsImportantScreen(){
    String importantText = mActivityRule.getActivity().getString(R.string.nav_drawer_important);
    //open nav drawer
    DrawerActions.openDrawer(R.id.activity_drawer_layout);
    //click on the important item
    onView(allOf(withText(importantText),
        withParent(isAssignableFrom(NavigationMenuItemView.class))))
        .perform(click());
    /// verify
    onView(allOf(withText(importantText),
        withParent(isAssignableFrom(Toolbar.class))))
        .check(matches(isDisplayed()));
  }
  @Test
  public void selectingSpamItemShowsSpamScreen(){
    String spamText = mActivityRule.getActivity().getString(R.string.nav_drawer_spam);
    //open nav drawer
    DrawerActions.openDrawer(R.id.activity_drawer_layout);
    //click on spam item
    onView(allOf(withText(spamText),
        withParent(isAssignableFrom(NavigationMenuItemView.class))))
        .perform(click());
    onView(allOf(withText(spamText),
        withParent(isAssignableFrom(Toolbar.class)))).check(matches(isDisplayed()));

  }
  @Test
  public void selectingAllItemShowsAllScreen(){
    String allText = mActivityRule.getActivity().getString(R.string.nav_drawer_all);
    //open nav drawer
    DrawerActions.openDrawer(R.id.activity_drawer_layout);
    onView(allOf(withText(allText),
        withParent(isAssignableFrom(NavigationMenuItemView.class))))
        .perform(click());
    onView(allOf(withText(allText),
        withParent(isAssignableFrom(Toolbar.class))))
        .check(matches(isDisplayed()));

  }
}
