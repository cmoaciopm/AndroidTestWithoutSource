package net.cmoaciopm.androidtest;

import android.app.Activity;
import android.os.SystemClock;
import android.support.test.espresso.InjectEventSecurityException;
import android.support.test.espresso.NoActivityResumedException;
import android.support.test.espresso.PerformException;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.util.HumanReadables;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import org.hamcrest.Matcher;

import java.util.Collection;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.util.ActivityLifecycles.hasForegroundActivities;
import static android.support.test.espresso.util.ActivityLifecycles.hasTransitioningActivities;

/**
 * Created by agong on 08/09/2017.
 */

public class MyKeyEventAction implements ViewAction {
    private static final String TAG = MyKeyEventAction.class.getSimpleName();
    private static final String DESC_FORMAT = "send key %s event for keycode %d";
    private static final String FAIL_FORMAT = "Failed to inject key %s event for keycode %d";

    private int keycode;
    private int keyAction;
    private String keyActionString;

    public MyKeyEventAction(int keycode,
                            int action) {
        this.keycode = keycode;
        this.keyAction = action;
        if (keyAction == KeyEvent.ACTION_DOWN) {
            keyActionString = "down";
        } else if (keyAction == KeyEvent.ACTION_UP) {
            keyActionString = "up";
        }

        // TODO Add validation
    }

    @Override
    public Matcher<View> getConstraints() {
        return isDisplayed();
    }

    @Override
    public String getDescription() {
        return String.format(DESC_FORMAT, keyActionString, keycode);
    }

    @Override
    public void perform(UiController uiController, View view) {
        try {
            if (!sendKeyEvent(uiController)) {
                String error = String.format(FAIL_FORMAT, keyActionString, keycode);
                Log.e(TAG, error);
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new RuntimeException(error))
                        .build();
            }
        } catch (InjectEventSecurityException e) {
            String error = String.format(FAIL_FORMAT, keyActionString, keycode);
            Log.e(TAG, error);
            throw new PerformException.Builder()
                    .withActionDescription(this.getDescription())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(e)
                    .build();
        }
    }

    private boolean sendKeyEvent(UiController controller)
            throws InjectEventSecurityException {
        boolean injected = false;
        long eventTime = SystemClock.uptimeMillis();
        for (int attempts = 0; !injected && attempts < 4; attempts++) {
            injected = controller.injectKeyEvent(new KeyEvent(eventTime,
                    eventTime,
                    keyAction,
                    keycode,
                    0,
                    0));
        }
        return injected;
    }

}
