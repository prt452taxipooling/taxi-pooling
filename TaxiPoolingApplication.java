package com.example.taxipooling;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;



/**
 * Created by Ali on 2016-03-14.
 */
public class TaxiPoolingApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {
    private String TAG = "TaxiPoolingApplication";

    @Override
    public void onCreate() {

        super.onCreate();

    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (activity instanceof MainActivity) {
            Log.d(TAG, "onActivitySaveInstanceState");
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
} // OurBuzzApplication
