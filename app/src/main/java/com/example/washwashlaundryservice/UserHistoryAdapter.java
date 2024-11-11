package com.example.washwashlaundryservice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

public class UserHistoryAdapter extends RecyclerView.Adapter<UserHistoryAdapter.ViewHolder>{

    LinkedList<UserHistoryClass> listItem;
    private Context context;

    public UserHistoryAdapter(LinkedList<UserHistoryClass> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserHistoryClass classRef = listItem.get(position);

        holder.washerOption.setText(classRef.getWasherOption());
        holder.dryerOption.setText(classRef.getDryerOption());
        holder.foldOption.setText(classRef.getFoldOption());
        holder.otpMachine.setText("OTP : " + classRef.getOtpMachine());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, UserHistoryDetails.class);
                intent1.putExtra("historyID", classRef.getHistoryID());
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView totalLaundry, washerOption, dryerOption, foldOption, otpMachine;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            totalLaundry = itemView.findViewById(R.id.totalLaundry);
            washerOption = itemView.findViewById(R.id.washerOption);
            dryerOption = itemView.findViewById(R.id.dryerOption);
            foldOption = itemView.findViewById(R.id.foldOption);
            otpMachine = itemView.findViewById(R.id.otpMachine);

        }
    }
}
