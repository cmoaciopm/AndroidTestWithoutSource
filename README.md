# AndroidTestWithoutSourceCode

This project demonstrates how to run Android instrumentation test without source code.

Without modifying anything, you can do instrument test for well-known ApiDemos.
Step by Step:
1. Build and install ApiDemos
2. Build and install the APK file of this project
3. Execute command *adb shell am instrument -w -e class net.cmoaciopm.androidtest.TestWithoutSourceCode net.cmoaciopm.androidtest/android.support.test.runner.AndroidJUnitRunner*

How to make it work to test other apps?
1. Resign APK file that you want to test using your own signature file, then install the resigned APK to Android device
2. Modify android:targetPackage in src/main/AndroidManifest.xml to the package that you want to test
3. Modify ACTIVITY_TO_BE_TESTED in src/main/java/net/cmoaciopm/androidtest/TestWithoutSourceCode.java to the acitvity you want to test
4. Build and install the APK file of this project
5. Execute command *adb shell am instrument -w -e class net.cmoaciopm.androidtest.TestWithoutSourceCode net.cmoaciopm.androidtest/android.support.test.runner.AndroidJUnitRunner*

Checkout branch test_aws.apps.keyeventdisplay to see how to run test against **KeyEvent Display**(https://play.google.com/store/apps/details?id=aws.apps.keyeventdisplay)
