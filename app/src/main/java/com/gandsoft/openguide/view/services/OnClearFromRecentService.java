package com.gandsoft.openguide.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.support.SessionUtil;

public class OnClearFromRecentService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("Lihat", "onStartCommand OnClearFromRecentService : " + "Service Started");

        Toast.makeText(getApplicationContext(), "start service OnClearFromRecentService", Toast.LENGTH_SHORT).show();// Set your own toast  message

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("Lihat", "onDestroy OnClearFromRecentService : " + "Service Destroyed");
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.d("Lihat", "onTaskRemoved OnClearFromRecentService : " + "task removed");
        //Code here
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            SessionUtil.removeKeyPreferences(ISeasonConfig.KEY_EVENT_ID);
        }
        stopSelf();
    }
}
