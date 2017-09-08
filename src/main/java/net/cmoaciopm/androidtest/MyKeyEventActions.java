package net.cmoaciopm.androidtest;

import android.support.test.espresso.ViewAction;
import android.view.KeyEvent;

public class MyKeyEventActions {

    public static ViewAction keyDown(int keycode) {
        return new MyKeyEventAction(keycode, KeyEvent.ACTION_DOWN);
    }

    public static ViewAction keyUp(int keycode) {
        return new MyKeyEventAction(keycode, KeyEvent.ACTION_UP);
    }
}
