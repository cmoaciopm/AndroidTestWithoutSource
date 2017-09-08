package net.cmoaciopm.androidtest;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.runner.AndroidJUnit4;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static net.cmoaciopm.androidtest.MyKeyEventActions.keyDown;
import static net.cmoaciopm.androidtest.MyKeyEventActions.keyUp;
import static org.hamcrest.CoreMatchers.containsString;


@RunWith(AndroidJUnit4.class)
public class TestWithoutSourceCode {

    private static final String ACTIVITY_TO_BE_TESTED = "aws.apps.keyeventdisplay.MainActivity";

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
        onView(withText("Clear")).check(matches(isDisplayed()));
        ViewInteraction fldEventTextView = onView(withId(getId("fldEvent")));
        fldEventTextView.check(matches(isDisplayed()));
        Espresso.pressBack();
        fldEventTextView.check(matches(withText(containsString("code=" + KeyEvent.KEYCODE_BACK))));

        fldEventTextView.perform(keyDown(KeyEvent.KEYCODE_CTRL_LEFT));
        fldEventTextView.check(matches(withText(containsString("action=0 code=" + KeyEvent.KEYCODE_CTRL_LEFT))));
        fldEventTextView.perform(keyDown(KeyEvent.KEYCODE_C));
        fldEventTextView.check(matches(withText(containsString("action=0 code=" + KeyEvent.KEYCODE_C))));
        fldEventTextView.perform(keyUp(KeyEvent.KEYCODE_C));
        fldEventTextView.check(matches(withText(containsString("action=1 code=" + KeyEvent.KEYCODE_C))));
        fldEventTextView.perform(keyUp(KeyEvent.KEYCODE_CTRL_LEFT));
        fldEventTextView.check(matches(withText(containsString("action=1 code=" + KeyEvent.KEYCODE_CTRL_LEFT))));
    }

    public static int getId(String id) {
        Context targetContext = InstrumentationRegistry.getTargetContext();
        String packageName = targetContext.getPackageName();
        return targetContext.getResources().getIdentifier(id, "id", packageName);
    }
}

