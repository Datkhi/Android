package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.ShowAllActivity;
import com.example.ecommerceapp.models.CategoryModel;
import com.example.ecommerceapp.models.MyCartModel;

import java.util.List;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.ViewHolder> {

    private Context context;
    private List<MyCartModel> list;
    int totalAmount = 0;
    public MyCardAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCardAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardAdapter.ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getProductName());
        holder.price.setText(list.get(position).getProductPrice()+"$");
        holder.date.setText(list.get(position).getCurrentDate());
        holder.time.setText(list.get(position).getCurrentTime());
        holder.totalQuantity.setText(list.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(list.get(position).getTotalPrice()));

        //Total amount pass to Cart Activity
        totalAmount += list.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, date, time, totalQuantity, totalPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.product_price);
            date = itemView.findViewById(R.id.current_date);
            time = itemView.findViewById(R.id.current_time);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
            totalPrice = itemView.findViewById(R.id.total_price);
        }
    }
}
