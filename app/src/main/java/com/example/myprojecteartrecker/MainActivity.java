package com.example.myprojecteartrecker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends Activity {
    private Button button_statistic, button_warnings,add_device;
    private ImageButton Menu;
    TextView timerRight1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Intent servo = new Intent(this, MyService.class);
        this.startService(servo);

        overridePendingTransition(R.anim.slide_up_in,R.anim.slide_up_out);

        runTimer();

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
    private void runTimer(){
        final Handler handler = new Handler();
        timerRight1 = findViewById(R.id.timerRight);


        SharedPreferences Pref = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE);
        handler.post(new Runnable() {
            @Override
            public void run() {

                timerRight1.setText(Pref.getString("SECON", ""));

                handler.postDelayed(this, 1000);
            }
        });
    }


}