package com.example.fatin.foodbasket.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.example.fatin.foodbasket.MainActivity;
import com.example.fatin.foodbasket.R;
import com.google.firebase.messaging.FirebaseMessagingService;

public class Notification extends FirebaseMessagingService {
    private static final int NOTIFY_ID =9;

public static void notifyUsers(Context context){
    Intent intent = new Intent(context, MainActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    PendingIntent pendingIntent =PendingIntent
            .getActivity(context,0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
    Uri uri_sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
    NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    NotificationCompat.Builder nofitBuilder =
            new NotificationCompat.Builder(context);
    nofitBuilder.setSmallIcon(R.drawable.notification_icon);
    nofitBuilder.setContentTitle("Food Basket");
    nofitBuilder.setContentText("New post");
    nofitBuilder.setAutoCancel(true);
    nofitBuilder.setSound(uri_sound);
    nofitBuilder.setContentIntent(pendingIntent);
    notificationManager.notify(NOTIFY_ID,nofitBuilder.build());
    //871450039178-kmangk9j2p15b0jn4ood6bj2mamltva6.apps.googleusercontent.com
}

}
