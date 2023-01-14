package com.example.appbanhang.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.TotalMoneyCalc;
import com.example.appbanhang.Utils;
import com.example.appbanhang.activity.DetailProductActivity;
import com.example.appbanhang.model.CartModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> cartModelList;
    private FirebaseFirestore firestore;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cartpopular_item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(cartModelList.get(position).getImgUrl()).into(holder.popImg);
        holder.name.setText(cartModelList.get(position).getNamePopular());
        holder.description.setText(cartModelList.get(position).getDescription());
        holder.price.setText(decimalFormat.format(cartModelList.get(position).getPrice()) + " VND");
        holder.qty.setText(cartModelList.get(position).getQuantity().toString());

        holder.setiClickListenner(new IClickListenner() {
            int money;
            @Override
            public void onItemClick(View view, int pos, int value) {
                int index = -1;
                if (value == 1) {
                    if (cartModelList.get(pos).getQuantity() > 1) {
                        int qty = cartModelList.get(pos).getQuantity() - 1;
                        cartModelList.get(pos).setQuantity(qty);

                    } else if (cartModelList.get(pos).getQuantity() == 1){
                        for (String item : Utils.cartList) {
                            if (item.equals(holder.name.getText())) {
                                index = Utils.cartList.indexOf(item);
                            }
                        }
                       if (index != -1){
                           Utils.cartList.remove(index);
                       }
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Notification");
                        builder.setMessage("Do you want to delete this product?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                firestore.collection("cartPopular").whereEqualTo("namePopular", cartModelList.get(pos).getNamePopular())
                                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                            @Override
                                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                                WriteBatch batch = firestore.batch();
                                                List<DocumentSnapshot> snapshots = queryDocumentSnapshots.getDocuments();
                                                for(DocumentSnapshot snapshot:snapshots){
                                                    batch.delete(snapshot.getReference());
                                                }
                                                batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(context.getApplicationContext(), "Delete product successfully", Toast.LENGTH_SHORT).show();
                                                        cartModelList.remove(pos);
                                                        notifyDataSetChanged();
                                                        EventBus.getDefault().postSticky(new TotalMoneyCalc());
                                                    }
                                                });
                                            }
                                        });
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                    }
                    EventBus.getDefault().postSticky(new TotalMoneyCalc());
                } else if (value == 2) {
                    if (cartModelList.get(pos).getQuantity() < 10) {
                        int qty = cartModelList.get(pos).getQuantity() + 1;
                        cartModelList.get(pos).setQuantity(qty);
                        EventBus.getDefault().postSticky(new TotalMoneyCalc());
                    }
                }
                holder.qty.setText(cartModelList.get(pos).getQuantity().toString());
                money = cartModelList.get(pos).getQuantity() * cartModelList.get(pos).getPrice();
                holder.price.setText(decimalFormat.format(money) + " VND");
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button btn_up, btn_down;
        ImageView popImg;
        TextView name, description, price, qty;
        IClickListenner iClickListenner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            firestore = FirebaseFirestore.getInstance();
            popImg = itemView.findViewById(R.id.cart_img);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.total);
            qty = itemView.findViewById(R.id.qty);
            btn_up = itemView.findViewById(R.id.btn_up);
            btn_down = itemView.findViewById(R.id.btn_down);
            //event click
            btn_down.setOnClickListener(this);
            btn_up.setOnClickListener(this);
        }

        public void setiClickListenner(IClickListenner iClickListenner) {
            this.iClickListenner = iClickListenner;
        }

        @Override
        public void onClick(View view) {
            if (view == btn_down) {
                iClickListenner.onItemClick(view, getAdapterPosition(), 1);
            } else if (view == btn_up) {
                iClickListenner.onItemClick(view, getAdapterPosition(), 2);
            }
        }
    }


}
