package com.lab.poc.full;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.util.Log;

public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = "AccessibilityDemo";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // Demo: log events for study. Do NOT perform automated clicks.
        Log.i(TAG, "Received accessibility event: " + event.toString());
    }

    @Override
    public void onInterrupt() {
        Log.i(TAG, "Accessibility service interrupted (demo).");
    }
}
