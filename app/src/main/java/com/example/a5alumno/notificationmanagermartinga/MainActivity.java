package com.example.a5alumno.notificationmanagermartinga;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.R.attr.id;


public class MainActivity extends AppCompatActivity {

    private NotificationCompat.Builder builder;
    private NotificationCompat.Builder secondBuilder;
    private NotificationManager mNotificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNotification = (Button) findViewById(R.id.buttonToNotify);
        Button btnCustomNotification = (Button) findViewById(R.id.buttonToCustomNotify);
        final Button btnAddNotification = (Button) findViewById(R.id.buttonAddNotification);

        btnCustomNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNotification();
                btnAddNotification.setEnabled(true);
            }
        });

        btnAddNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNotification();
            }
        });
        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createCustomNotification();
            }
        });
    }


    Integer numMessages = 0;

    private void createNotification() {

        builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My notification")
                        .setContentText("Hello Me! You are awesome! ^^")
                        .setAutoCancel(true);
        numMessages = 0;

        // Creates an Intent for the Activity
        Intent notifyIntent =
                new Intent(this, ResultActivity.class);
        // Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Creamos el PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // Añadimos el PendingIntent al notificationBuilder
        builder.setContentIntent(pendingIntent);
        // Se la mandamos al  NotificationManager system service.
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Creamos una Notification anonima del constructor, y se la pasamos al NotificationManager
        mNotificationManager.notify(id, builder.build());


    }

    private void addNotification() {

        Log.i("BAAAAH", numMessages.toString());
        ++numMessages;
        builder.setContentText("You are even more awesome!! " + numMessages.toString())
                .setNumber(numMessages);
        // Como el ID se mantiene el mismo, la notificacion existente es "updateada"
        mNotificationManager.notify(
                0,
                builder.build());
    }

    private void createCustomNotification() {

        secondBuilder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My second notification")
                        .setContentText("You are awesome! ^^")
                        .setAutoCancel(true);

        Intent dismissIntent = new Intent(this, ResultActivity.class);
        PendingIntent piDismiss = PendingIntent.getActivity(
                this,
                0,
                dismissIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        Intent snoozeIntent = new Intent(this, ResultActivity.class);
        //snoozeIntent.setAction(CommonConstants.ACTION_DISMISS);
        PendingIntent piSnooze = PendingIntent.getService(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder secondBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("ATENCION!!!!")
                        .setContentText("HAS GANADO UN IPHONE 7!!!!")
                        .setDefaults(Notification.DEFAULT_ALL) // requires VIBRATE permission
        /*
         * Sets the big view "big text" style and supplies the
         * text (the user's reminder message) that will be displayed
         * in the detail area of the expanded notification.
         * These calls are ignored by the support library for
         * pre-4.1 devices.
         */
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("HAS SIDO SELECCIONADO PARA GANAR UN IPHONE 8!"))
                        .addAction(R.drawable.ic_launcher,
                                "Soy estupido y lo quiero", piDismiss)
                        .addAction(R.drawable.ic_notifications_black_24dp,
                                "Soy de Android :(", piSnooze);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // Creamos una Notification anonima del constructor, y se la pasamos al NotificationManager
        mNotificationManager.notify(id, secondBuilder.build());


    }
}
