package com.apps.ridvan.smartgatekeeper;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {
    @Rule
    public ActivityTestRule<LoginActivity> mLoginActivityActivityTestRule = new ActivityTestRule(LoginActivity.class);

    @Test
    public void failedLogin() throws Exception {
        String localUrl = "192.168.43.148";
        String email = "xyz@aaa.com";
        String password = "test";

        onView(withId(R.id.input_url)).check(matches(isDisplayed()));

        onView(withId(R.id.input_url)).perform(clearText());
        onView(withId(R.id.user_login)).perform(clearText());
        onView(withId(R.id.password)).perform(clearText());

        onView(withId(R.id.input_url)).perform(typeText(localUrl));
        onView(withId(R.id.user_login)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(password));

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.input_url)).check(matches(isDisplayed()));
    }

    @Test
    public void successfulLogin() throws Exception {
        String localUrl = "192.168.43.148";
        String email = "rasibic@uclan.ac.uk";
        String password = "test";

        onView(withId(R.id.input_url)).check(matches(isDisplayed()));

        onView(withId(R.id.input_url)).perform(clearText());
        onView(withId(R.id.user_login)).perform(clearText());
        onView(withId(R.id.password)).perform(clearText());

        onView(withId(R.id.input_url)).perform(typeText(localUrl));
        onView(withId(R.id.user_login)).perform(typeText(email));
        onView(withId(R.id.password)).perform(typeText(password));

        onView(withId(R.id.email_sign_in_button)).perform(click());

        onView(withId(R.id.functions_form)).check(matches(isDisplayed()));
    }
}
