package com.gandsoft.openguide.support;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

import com.gandsoft.openguide.R;

public class NotifUtil {
    public static void setNotification(Context context, @Nullable String title, @Nullable String text, int smallIcon, int bigIcon, boolean isNotCancelAble, PendingIntent pendingIntent, int NOTIFICATION_ID) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), bigIcon);
        setNotification(context, title, text, smallIcon, bitmap, isNotCancelAble, pendingIntent, NOTIFICATION_ID);
    }

    public static void setNotification(Context context, @Nullable String title, @Nullable String text, int smallIcon, Bitmap bigIcon, boolean isNotCancelAble, PendingIntent pendingIntent, int NOTIFICATION_ID) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setSmallIcon(smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setOngoing(isNotCancelAble)
                .setPriority(Notification.PRIORITY_HIGH)
                //.setProgress()
                ;

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean isNotificationShow(Context context, int NOTIFICATION_ID) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        StatusBarNotification[] notifications = notificationManager.getActiveNotifications();
        for (StatusBarNotification notification : notifications) {
            return notification.getId() == NOTIFICATION_ID;
        }
        return false;
    }

    public static void clearNotification(Context context, int NOTIFICATION_ID) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
    }

    public static void clearNotificationAll(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

}
