package com.example.myprojecteartrecker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Statistic extends  Activity{

    RecyclerView recyclerView;
    RecyclerView.Adapter programAdapter;
    RecyclerView.LayoutManager layoutmanager;
    String[] date = {"MAY 18 95 min","MAY 17 30 min","MAY 16 45 min","MAY 15 5 min","MAY 14 15 min","MAY 13 20 min"};
    int[] programmImages = {R.drawable.red25, R.drawable.green50, R.drawable.green100, R.drawable.red50, R.drawable.green100, R.drawable.red100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        recyclerView = findViewById(R.id.rvProgram);
        recyclerView.setHasFixedSize(true);
        layoutmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutmanager);
        programAdapter = new ProgramAdapter(this, date, programmImages);
        recyclerView.setAdapter(programAdapter);
    }

}