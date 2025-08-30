package com.lab.poc.full;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
    private static final String TAG = "MainServiceDemo";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "MainService started (demo).");
        // Example: send heartbeat to local lab server (simulated)
        ApiClient.sendHeartbeat(this);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
