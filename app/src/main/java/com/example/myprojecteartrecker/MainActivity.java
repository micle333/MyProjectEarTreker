package com.example.myprojecteartrecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    private Button button_statistic, button_warnings,add_device;
    private ImageButton Menu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Date currentTime = Calendar.getInstance().getTime();
        startService(new Intent(this, MyService.class));

        overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);
        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        Intent intent = getIntent();
        Intent intent1 = getIntent();
        Intent intent2 = getIntent();
        int hours = intent.getIntExtra("hl",0);
        int minutes = intent1.getIntExtra("ml",0);
        int second = intent2.getIntExtra("sl",0);

        final TextView timerRight1 = findViewById(R.id.timerRight);

        String timeRight = String.format("%d:%02d:%02d",hours,minutes,second);
        Thread requestThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    timerRight1.setText(timeRight);
                    Thread.sleep(1000);
                }
                catch (InterruptedException ex){
                }
            }
        });
        requestThread.start();
        button_statistic = (Button) findViewById(R.id.button_statistic);
        button_warnings = (Button) findViewById(R.id.button_warnings);
        add_device = (Button) findViewById(R.id.add_device);
        Menu = (ImageButton) findViewById(R.id.Menu);

        button_statistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityStatist();
            }
        });
        button_warnings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityWarning();
            }
        });
        add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityDevice();
            }
        });
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMenu();
            }
        });
    }

    public void openActivityStatist() {
        Intent intent = new Intent(this, Statistic.class );
        startActivity(intent);
    }
    public void openActivityWarning() {
        Intent intent = new Intent(this, Warning.class );
        startActivity(intent);
    }
    public void openActivityDevice() {
        Intent intent = new Intent(this, Add_Device.class );
        startActivity(intent);
    }
    public void openActivityMenu() {
        Intent intent = new Intent(this, Menu.class );
        startActivity(intent);
    }
    protected void onDestroy() {
        super.onDestroy();
    }


}