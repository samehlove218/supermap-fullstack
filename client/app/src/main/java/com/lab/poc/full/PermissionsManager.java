package com.lab.poc.full;

import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

public class PermissionsManager {
    // Demo helper to request/check dangerous permissions in an Activity.
    public static boolean hasPermission(Activity act, String permission) {
        return ContextCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_GRANTED;
    }
    public static void requestPermissions(Activity act, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(act, permissions, requestCode);
    }
}
