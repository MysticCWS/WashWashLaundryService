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

import com.bumptech.glide.Glide;

import java.util.LinkedList;

public class UserCartAdapter extends RecyclerView.Adapter<UserCartAdapter.ViewHolder>{

    LinkedList<UserCartClass> listItem;
    private Context context;

    public UserCartAdapter(LinkedList<UserCartClass> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        UserCartClass classRef = listItem.get(position);

        holder.washerOption.setText(classRef.getWasherOption());
        holder.dryerOption.setText(classRef.getDryerOption());
        holder.foldOption.setText(classRef.getFoldOption());
        holder.totalLaundry.setText("RM "+ classRef.getTotal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(context, Payment.class);
                intent1.putExtra("cartID", classRef.getCartID());
                intent1.putExtra("washerOption",classRef.getWasherOption());
                intent1.putExtra("dryerOption",classRef.getDryerOption());
                intent1.putExtra("foldOption",classRef.getFoldOption());
                intent1.putExtra("totalAmount",classRef.getTotal());
                context.startActivity(intent1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView totalLaundry, washerOption, dryerOption, foldOption;
        ImageView imgcheckout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            totalLaundry = itemView.findViewById(R.id.totalLaundry);
            washerOption = itemView.findViewById(R.id.washerOption);
            dryerOption = itemView.findViewById(R.id.dryerOption);
            foldOption = itemView.findViewById(R.id.foldOption);
            imgcheckout = itemView.findViewById(R.id.imgcheckout);
        }
    }
}
