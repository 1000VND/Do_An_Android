package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.appbanhang.Adapter.OrderAdapter;
import com.example.appbanhang.R;
import com.example.appbanhang.model.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    RecyclerView recycleview;
    Toolbar toolbar;
    List<OrderModel> orderModelList;
    OrderAdapter orderAdapter;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Order history
        recycleview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        orderAdapter = new OrderAdapter(getApplicationContext(),orderModelList);
        recycleview.setAdapter(orderAdapter);

        db.collection("orderHistory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                OrderModel homeCategoryModel = document.toObject(OrderModel.class);
                                orderModelList.add(homeCategoryModel);
                                orderAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView() {
        db = FirebaseFirestore.getInstance();
        orderModelList = new ArrayList<>();
        recycleview = findViewById(R.id.recycleview);
        toolbar = findViewById(R.id.toolbar);
    }


}