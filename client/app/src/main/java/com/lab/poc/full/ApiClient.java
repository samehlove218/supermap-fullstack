package com.lab.poc.full;

import android.content.Context;
import android.util.Log;

public class ApiClient {
    // Demo client: prints logs instead of real network ops.
    public static void sendHeartbeat(Context ctx) {
        Log.i("ApiClientDemo","Sending heartbeat to lab server (simulated).");
        // In a real setup: use HttpUrlConnection / OkHttp to POST to server.
    }
    public static void sendFakeCommandResult(String deviceId, String result) {
        Log.i("ApiClientDemo","Reporting fake result for "+deviceId+": "+result);
    }
}
