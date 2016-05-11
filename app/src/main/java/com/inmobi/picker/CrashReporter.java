package com.inmobi.picker;

import android.util.Log;

/**
 * Created by dipal.patel on 11/13/15.
 */
public class CrashReporter implements Thread.UncaughtExceptionHandler {

        private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;
        private static boolean sIsRegistered = false;

        public CrashReporter(Thread.UncaughtExceptionHandler handler) {
            mDefaultExceptionHandler = handler;
        }

        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Log.v("Dipal", "Crash reported to crashReporter: " + e.getMessage());
            mDefaultExceptionHandler.uncaughtException(t,e);
        }
}
