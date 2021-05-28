package com.example.myprojecteartrecker;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.MyViewHolder> {
    Context context;

    ArrayList<User> list;


    public ProgramAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.single_item,parent,false);
        return  new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.Date.setText(user.getDate());
        holder.Time.setText(user.getTime());
        Log.d((String) "TAG", "ТАЙМЕРooo-----------" + user.getTime());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Date, Time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.Date);
            Time = itemView.findViewById(R.id.time);


        }
    }

}
