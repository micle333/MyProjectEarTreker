package com.example.myprojecteartrecker;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MusicThread extends Thread{
    private int seconds;
    public boolean running = false;
    private Messenger messenger;
    public SharedPreferences shPref;


    public MusicThread(Messenger messenger, int seconds) {
        this.messenger = messenger;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        
        int hours = seconds/3600;
        int minutes = (seconds%3600)/60;
        int second = seconds%60;

        String text1 = String.format("%d:%02d:%02d",hours,minutes,second);
        Message msg1 = Message.obtain();
        msg1.what = 1;
        Bundle bundle = new Bundle(1);
        bundle.putString("time", text1);
        msg1.setData(bundle);
        try {   this.messenger.send(msg1); }
        catch (RemoteException e) { e.printStackTrace();    }



        while (true){
            if (running){
                seconds++;
                hours = seconds/3600;
                minutes = (seconds%3600)/60;
                second = seconds%60;
                String text = String.format("%d:%02d:%02d",hours,minutes,second);
                Message msg = Message.obtain();
                msg.what = 1;
                Bundle bundle1 = new Bundle(1);
                bundle1.putString("time", text);
                bundle1.putInt("seconds", seconds);
                msg.setData(bundle1);
                try {   this.messenger.send(msg); }
                catch (RemoteException e) { e.printStackTrace();    }
            }


            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException ex){
                break;
            }
        }
    }
}
