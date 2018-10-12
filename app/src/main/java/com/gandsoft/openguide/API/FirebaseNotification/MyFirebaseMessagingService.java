/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gandsoft.openguide.API.FirebaseNotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.gandsoft.openguide.R;
import com.gandsoft.openguide.activity.ChangeEventActivity;
import com.gandsoft.openguide.activity.main.BaseHomeActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getFrom: " + remoteMessage.getFrom());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getMessageId: " + remoteMessage.getMessageId());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getMessageType: " + remoteMessage.getMessageType());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getSentTime: " + remoteMessage.getSentTime());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getNotification().getBody: " + remoteMessage.getNotification().getBody());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getNotification().getColor: " + remoteMessage.getNotification().getColor());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getNotification().getClickAction: " + remoteMessage.getNotification().getClickAction());
        Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService remoteMessage.getNotification().getTitle: " + remoteMessage.getNotification().getTitle());

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> remoteMessageData = remoteMessage.getData();
            List<String> list1 = new ArrayList<String>(remoteMessageData.keySet());
            Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService list1 : " + list1);
            List<String> list21 = new ArrayList<String>(remoteMessageData.values());
            Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService list21 : " + list21);
            JSONObject mapToObject = new JSONObject(remoteMessageData);
            Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService mapToObject : " + mapToObject);

            for (String key : remoteMessageData.keySet()) {
                Log.d("Lihat", "onMessageReceived MyFirebaseMessagingService key : " + key);
            }
        }
        makeNotification(remoteMessage.getNotification().getBody());
    }

    private void makeNotification(String messageBody) {
        Intent intent = new Intent(this, BaseHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("Openguides Notification")
                .setSmallIcon(R.drawable.ic_og_logo)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setLights(Color.GREEN, 3000, 3000)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
}
