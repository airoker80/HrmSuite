package com.harati.hrmsuite.FCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.harati.hrmsuite.LoginPackage.Activity.LoginActivity;
import com.harati.hrmsuite.MainPackage.Acitivity.MainActivity;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;


public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    Intent in;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        showNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
    }

    private void showNotification(String message, String title) {
        if (title != null && getLoginStatus()) {
            in = new Intent(getApplicationContext(), MainActivity.class);
            in.putExtra("title", title);
        } else {
            in = new Intent(getApplicationContext(), LoginActivity.class);
        }
//        Intent i = new Intent(this, MainActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setContentTitle("HRMSuite- "+ title)
                .setContentText(message)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        manager.notify((int) System.currentTimeMillis(), builder.build());
    }
    public boolean getLoginStatus() {
        UserSessionManager userSessionManager= new UserSessionManager(getApplicationContext());
        return userSessionManager.isUserLoggedIn();
    }

}
