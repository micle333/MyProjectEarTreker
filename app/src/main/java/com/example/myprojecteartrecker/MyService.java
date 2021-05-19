package com.example.myprojecteartrecker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;


import java.security.Provider;

public class MyService extends Service {
    private static final Object TAG = "service" ;
    private AudioManager audioManager;
    private int seconds;
    private boolean running;
    private Thread requestThread;
    EditText etText;
    SharedPreferences sPref;
    final String SAVED_TEXT = "saved_text";
    int  hl, ml, sl;

    public void onCreate(){
        super.onCreate();

        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        runTimer();
        requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!requestThread.isInterrupted()){
                    if (audioManager.isMusicActive() & audioManager.isWiredHeadsetOn()) {
                        running = true;
                    }
                    if (audioManager.isMusicActive() & audioManager.isBluetoothA2dpOn()) {
                        running = true;
                    }
                    else{
                        running = false;
                    }
                    try {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException ex){
                        break;
                    }
                }
            }
        });
        requestThread.start();
    }
    private void runTimer(){
        final Handler handler = new Handler();
        Intent intent = new Intent(this, MainActivity.class);
        Intent intent1 = new Intent(this, MainActivity.class);
        Intent intent2 = new Intent(this, MainActivity.class);

        handler.post(new Runnable() {
            @Override
            public void run(){
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int second = seconds%60;
                hl = hours;
                ml = minutes;
                sl = second;
                if (running){
                    seconds++;
                    Log.d((String) TAG, "onCreate: Seconds--------------------------------------------------------------------------------------------");
                }
                saveText();
                intent.putExtra("hl", hl);
                intent1.putExtra("ml", ml);
                intent2.putExtra("sl", sl);
                handler.postDelayed(this,1000);
                startActivity(intent);
                startActivity(intent1);
                startActivity(intent2);
            }
        });
    }

    private void saveText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putInt("h1",hl);
        ed.putInt("m1",ml);
        ed.putInt("s1",sl);
        ed.putString(SAVED_TEXT, etText.getText().toString());
        ed.apply();
    }

    private void loadText() {
        sPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, "");
        etText.setText(savedText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        saveText();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
