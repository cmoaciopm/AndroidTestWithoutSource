# AndroidTestWithoutSourceCode

This project demonstrates how to run Android instrumentation test without source code.

Without modifying anything, you can do instrument test for "KeyEvent Display"(Play store link : https://play.google.com/store/apps/details?id=aws.apps.keyeventdisplay)

Step by Step:
1. Resign apk file of **KeyEvent Display** with your own keystore using **apksinger**(introduced began from Android build-tools 25.0.0)
2. Build and install the APK file of this project. For example, using command **gradle installDebug**.
3. Execute command *adb shell am instrument -w -e class net.cmoaciopm.androidtest.TestWithoutSourceCode net.cmoaciopm.androidtest/android.support.test.runner.AndroidJUnitRunner* to run the test
