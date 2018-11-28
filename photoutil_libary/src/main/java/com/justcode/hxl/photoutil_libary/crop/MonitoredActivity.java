package com.justcode.hxl.photoutil_libary.crop;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

public class MonitoredActivity extends Activity {

    private final ArrayList<LifeCycleListener> listeners = new ArrayList<LifeCycleListener>();

    public static interface LifeCycleListener {
        public void onActivityCreated(MonitoredActivity activity);
        public void onActivityDestroyed(MonitoredActivity activity);
        public void onActivityStarted(MonitoredActivity activity);
        public void onActivityStopped(MonitoredActivity activity);
    }

    public static class LifeCycleAdapter implements MonitoredActivity.LifeCycleListener {
        public void onActivityCreated(MonitoredActivity activity) {}
        public void onActivityDestroyed(MonitoredActivity activity) {}
        public void onActivityStarted(MonitoredActivity activity) {}
        public void onActivityStopped(MonitoredActivity activity) {}
    }

    public void addLifeCycleListener(MonitoredActivity.LifeCycleListener listener) {
        if (listeners.contains(listener)) return;
        listeners.add(listener);
    }

    public void removeLifeCycleListener(MonitoredActivity.LifeCycleListener listener) {
        listeners.remove(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (MonitoredActivity.LifeCycleListener listener : listeners) {
            listener.onActivityCreated(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (MonitoredActivity.LifeCycleListener listener : listeners) {
            listener.onActivityDestroyed(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (MonitoredActivity.LifeCycleListener listener : listeners) {
            listener.onActivityStarted(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        for (MonitoredActivity.LifeCycleListener listener : listeners) {
            listener.onActivityStopped(this);
        }
    }

}