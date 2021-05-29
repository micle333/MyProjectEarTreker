package com.example.myprojecteartrecker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Provider;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class MyService extends Service {
    private static final Object TAG = "service" ;
    private Thread requestThread;
    int sec;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference root = mDatabase.getReference().child("User");

    public void onCreate(){
        super.onCreate();

        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        Intent servo = new Intent(this, MyService.class);
        this.startService(servo);

        Date currentTime  = Calendar.getInstance().getTime();
        String formattrdData = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
        String[] splitDate = formattrdData.split(",");

        requestThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Date currentTime  = Calendar.getInstance().getTime();
                String formattrdData = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);
                String[] splitDate = formattrdData.split(",");
                SharedPreferences shPref = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shPref.edit();
                sec = shPref.getInt("SECi", 0);
                int hours = sec/3600;
                int minutes = (sec%3600)/60;
                int second = sec%60;
                String Date = splitDate[1].trim();

                while (!requestThread.isInterrupted()){
                    hours = sec/3600;
                    minutes = (sec%3600)/60;
                    second = sec%60;
                    String text = String.format("%d:%02d:%02d",hours,minutes,second);

                    if (sec == shPref.getInt("warning", 14400)){
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(MyService.this)
                                        .setSmallIcon(R.drawable.hearing)
                                        .setContentTitle("Напоминание")
                                        .setContentText("Пора покормить кота")
                                        .setAutoCancel(true);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(MyService.this);
                        notificationManager.notify(101, builder.build());

                    }
                    if(!splitDate[1].trim().equals(Date)){
                            text = "00:00:00";
                            sec = 0;
                            editor.putString("SECON", text);
                            editor.putInt("SECi", sec);
                            editor.apply();

                            Date = splitDate[1].trim();
                            HashMap<String,String> userMap = new HashMap<>();
                            userMap.put("Date", Date);
                            userMap.put("Time", text);
                            root.push().setValue(userMap);
                        }

                    if (audioManager.isMusicActive() && audioManager.isWiredHeadsetOn()) {
                        sec++;
                        editor.putString("SECON", text);
                        editor.putInt("SECi", sec);
                        editor.apply();
                        Date = splitDate[1].trim();


                    }
                    if (audioManager.isMusicActive() && audioManager.isBluetoothA2dpOn()) {
                        sec++;
                        editor.putString("SECON", text);
                        editor.putInt("SECi", sec);
                        editor.apply();
                        Log.d((String) "TAG",  "СЕРВИСИИИИССС----------" + sec);
                    }
                    else{
                    }
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException ex){
                    }
                }


            }
        });
        requestThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
