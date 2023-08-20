package com.awesomeproject;
// replace your-apps-package-name with your app’s package name

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsageStatsModule extends ReactContextBaseJavaModule {
    private final ReactApplicationContext reactContext;
    private UsageStatsManager usageStatsManager;

    public UsageStatsModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.usageStatsManager = (UsageStatsManager) reactContext.getSystemService(Context.USAGE_STATS_SERVICE);
    }


    @NonNull
    @Override
    public String getName() {
        return "UsageStatsModule";
    }
    @ReactMethod
    public void getUsageStats(Promise promise) {

        WritableMap jsObject = new WritableNativeMap();
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        long endTime = System.currentTimeMillis();
        long startTime = endTime - (24 * 60 * 60 * 1000);
        Log.d("CalendarModule", "Create event called with name: " + startTime
                + " and location: " +endTime);
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, startTime, endTime);
//        Log.d("CalendarModule", "Create event called with name: " + usageStatsList.getClass().getName()
//                + " and location: " );
//         app_list.pushArray((ReadableArray) usageStatsList);
//         cb.invoke(usageStatsList);
        if (usageStatsList != null && !usageStatsList.isEmpty()) {
            Map<String, Long> statsMap = new HashMap<>();
            for (UsageStats usageStats : usageStatsList) {
                statsMap.put(usageStats.getPackageName(), usageStats.getTotalTimeInForeground());
                jsObject.putString(usageStats.getPackageName(), String.valueOf((usageStats.getTotalTimeInForeground()/1000)));

            }
            promise.resolve(jsObject);

        } else {
            promise.reject("NO_USAGE_STATS", "No usage stats available.");
        }
    }


}