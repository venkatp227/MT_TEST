package com.mytaxi.android_demo;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.content.Intent;
import android.util.Log;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.matcher.RootMatchers;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

//UserFlowTest  names it UserFlowTest since we have here a basic EndUserScenario
@RunWith(AndroidJUnit4.class)
public class UserFlowTest {

    //Testdata
    private static final String user="crazydog335";
    private static final String pw="venture";
    private static final String searchphrase="sa";
    private static final String drivername="Sarah Scott";

    @Rule
    public ActivityTestRule<AuthenticationActivity> UserFlowTestRule = new ActivityTestRule<>(AuthenticationActivity.class, true, false);
    public ActivityTestRule<MainActivity> UserFlowTest = new ActivityTestRule<>(MainActivity.class);
    @Rule
    //permission needed due running this local for gps, so enabled it
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);


    @Test
    public void logintoApp() throws Exception {
        UserFlowTest.launchActivity(new Intent());
        Thread.sleep(2000);
        // instead of sleep something that waits for the elements to be visible to have if faster and stable
        Log.println(Log.INFO, "@Test", "Enter user credentials");
        onView(withId(R.id.edt_username))
                .perform(typeText(user));
        onView(withId(R.id.edt_password))
                .perform(typeText(pw), closeSoftKeyboard());
        onView(withId(R.id.btn_login))
                .perform(click());
        Log.println(Log.INFO, "logintoApp", "User " + user + " logged in");
    }

    @Test()
    public void search_Driver() throws Exception {
        UserFlowTest.launchActivity(null);
        Thread.sleep(2000);
        Log.println(Log.INFO, "@Test", "Test to search for a certain driver");

        onView(withId(R.id.textSearch))
                .perform(typeText(searchphrase), closeSoftKeyboard());

        Thread.sleep(1000);
        onView(withText(drivername))
                .inRoot(RootMatchers.withDecorView(not(is(UserFlowTest.getActivity().getWindow().getDecorView()))))
                .perform(scrollTo())
                .perform(click());
        Log.println(Log.INFO, "@Test", "Driver " + drivername + " found");

        onView(withId(R.id.fab))
                .perform(click());
        Log.println(Log.INFO, "@Test", "Driver " + drivername + " called");
    }

}
