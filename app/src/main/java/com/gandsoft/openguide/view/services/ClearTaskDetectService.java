package com.gandsoft.openguide.view.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.gandsoft.openguide.ISeasonConfig;
import com.gandsoft.openguide.support.NotifUtil;
import com.gandsoft.openguide.support.ServiceUtil;
import com.gandsoft.openguide.support.SessionUtil;

public class ClearTaskDetectService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //Toast.makeText(getApplicationContext(), "start service ClearTaskDetectService", Toast.LENGTH_SHORT).show();// Set your own toast  message
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //Code here
        if (SessionUtil.checkIfExist(ISeasonConfig.KEY_EVENT_ID)) {
            SessionUtil.removeKeyPreferences(ISeasonConfig.KEY_EVENT_ID);
        }
        ServiceUtil.stopSevice(ClearTaskDetectService.this, RepeatCheckDataService.class);
        NotifUtil.clearNotification(ClearTaskDetectService.this,ISeasonConfig.ID_NOTIF);
        stopSelf();
    }
}
