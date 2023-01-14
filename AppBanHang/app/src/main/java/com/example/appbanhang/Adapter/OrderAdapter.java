package com.example.appbanhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.OrderModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    private Context context;
    private List<OrderModel> orderModelList;

    public OrderAdapter(Context context, List<OrderModel> orderModelList) {
        this.context = context;
        this.orderModelList = orderModelList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.name.setText("Name: " +orderModelList.get(position).getFirstname()+ " " + orderModelList.get(position).getLastname());
        holder.paydata.setText("Order date: " + orderModelList.get(position).getCurrentDate() + " " + orderModelList.get(position).getCurrentTime());
        holder.summary.setText("Summary: " + orderModelList.get(position).getSummary());
    }

    @Override
    public int getItemCount() {
            return orderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, paydata, summary;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            paydata = itemView.findViewById(R.id.paydata);
            summary = itemView.findViewById(R.id.summary);
        }
    }
}
