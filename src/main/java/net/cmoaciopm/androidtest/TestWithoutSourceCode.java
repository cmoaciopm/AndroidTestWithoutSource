package net.cmoaciopm.androidtest;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class TestWithoutSourceCode {

    private static final String ACTIVITY_TO_BE_TESTED = "com.example.android.apis.ApiDemos";

    @Before
    public void setup() {
        Class activityClass = null;
        try {
            activityClass = Class.forName(ACTIVITY_TO_BE_TESTED);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.setInTouchMode(true);

        final String targetPackage = instrumentation.getTargetContext().getPackageName();
        Intent startIntent = new Intent(Intent.ACTION_MAIN);
        startIntent.setClassName(targetPackage, activityClass.getName());
        startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        instrumentation.startActivitySync(startIntent);
        instrumentation.waitForIdleSync();
    }

    @Test
    public void test() {
        onView(withText("API Demos")).check(matches(isDisplayed()));
    }
}

