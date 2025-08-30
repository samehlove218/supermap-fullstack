
package com.lab.poc.supermap;

import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {

    private static final String TAG = "ApiClient";
    // This URL will be replaced with the public Render URL before final build
    private static final String BASE_URL = "http://10.0.2.2:3000"; // 10.0.2.2 is localhost for Android emulator

    private static final OkHttpClient client = new OkHttpClient();
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    public static void registerDevice(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        String model = Build.MODEL;

        try {
            JSONObject json = new JSONObject();
            json.put("deviceId", deviceId);
            json.put("model", model);

            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(BASE_URL + "/api/register")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Device registration failed", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Device registered successfully: " + response.body().string());
                    } else {
                        Log.w(TAG, "Device registration server error: " + response.body().string());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error creating registration JSON", e);
        }
    }

    public static void sendData(String deviceId, String type, String content) {
        try {
            JSONObject json = new JSONObject();
            json.put("deviceId", deviceId);
            json.put("type", type);
            json.put("content", content);

            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(BASE_URL + "/api/data")
                    .post(body)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, "Failed to send data of type '" + type + "'", e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Log.i(TAG, "Data of type '" + type + "' sent successfully.");
                    } else {
                        Log.w(TAG, "Server error sending data: " + response.body().string());
                    }
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error creating data JSON", e);
        }
    }
}
