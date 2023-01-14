package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.Adapter.CartAdapter;
import com.example.appbanhang.Adapter.PopularAdapters;
import com.example.appbanhang.R;
import com.example.appbanhang.TotalMoneyCalc;
import com.example.appbanhang.model.CartModel;
import com.example.appbanhang.model.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    Toolbar toolbar;
    FirebaseFirestore firestore;
    CartAdapter cartAdapter;
    RecyclerView recyclerView;
    List<CartModel> cartModelList;
    ScrollView scrollView;
    Button buy;
    Intent intent;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    TextView totalprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        totalprice.setText("0 VND");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!totalprice.getText().equals("0 VND")){
                    intent = new Intent(getApplicationContext(), PayActivity.class);
                    intent.putExtra("summary", totalprice.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Empty shopping cart", Toast.LENGTH_SHORT).show();
                }

            }
        });

        firestore = FirebaseFirestore.getInstance();

        //Cart product
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        cartAdapter = new CartAdapter(getApplicationContext(),cartModelList);
        recyclerView.setAdapter(cartAdapter);

        firestore.collection("cartPopular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CartModel cartModel = document.toObject(CartModel.class);
                                cartModelList.add(cartModel);
                                cartAdapter.notifyDataSetChanged();
                                summary();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void summary() {
        long total = 0;
        for (int i = 0; i < cartModelList.size();i++){
            total = total + (cartModelList.get(i).getPrice() * cartModelList.get(i).getQuantity());
        }
        totalprice.setText(decimalFormat.format(total) + " VND");
    }

    private void initView() {
        firestore = FirebaseFirestore.getInstance();
        cartModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycleview);
        scrollView = findViewById(R.id.scrollview);
        toolbar = findViewById(R.id.toolbar);
        buy = findViewById(R.id.btnmuahang);
        totalprice = findViewById(R.id.totalprice);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void totalMoneyCalcEvent(TotalMoneyCalc totalMoneyCalc) {
        if(totalMoneyCalc != null) {
            summary();
        }
    }
}