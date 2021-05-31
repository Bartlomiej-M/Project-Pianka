package com.example.projekt_pianka_myjnia;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotifcationAdapter extends RecyclerView.Adapter<NotifcationAdapter.MyViewHolder> {

    Context context;

    ArrayList<notification> list;

    public NotifcationAdapter(Context context, ArrayList<notification> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        notification notification = list.get(position);
        holder.Date.setText(notification.getDate());
        holder.Subject.setText(notification.getSubject());
        holder.Description.setText(notification.getDescription());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Date, Subject, Description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Date = itemView.findViewById(R.id.item_date);
            Subject = itemView.findViewById(R.id.item_subject);
            Description = itemView.findViewById(R.id.item_description);
        }
    }

}
