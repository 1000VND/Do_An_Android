package com.example.appbanhang.activity;

import static com.example.appbanhang.R.id.progressbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.example.appbanhang.Adapter.PopularAdapters;
import com.example.appbanhang.R;
import com.example.appbanhang.model.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ShoesBrandsActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    PopularAdapters homeCategoryAdapter;
    RecyclerView recyclerView;
    List<PopularModel> homeCategoryModelList;
    Toolbar toolbar;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoes_brands);

        toolbar = findViewById(R.id.toolbar);
        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        toolbar.setTitle("Products " + type.toUpperCase());
        recyclerView = findViewById(R.id.view_shoes_of_brands);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        homeCategoryModelList = new ArrayList<>();
        homeCategoryAdapter = new PopularAdapters(this,homeCategoryModelList);
        recyclerView.setAdapter(homeCategoryAdapter);

        //getting nike
        if (type != null && type.equalsIgnoreCase("nike")){
            firestore.collection("PopularPropduct").whereEqualTo("type","nike").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();

                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting puma
        if (type != null && type.equalsIgnoreCase("puma")){
            firestore.collection("PopularPropduct").whereEqualTo("type","puma").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting newbalance
        if (type != null && type.equalsIgnoreCase("newbalance")){
            firestore.collection("PopularPropduct").whereEqualTo("type","newbalance").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting vans
        if (type != null && type.equalsIgnoreCase("vans")){
            firestore.collection("PopularPropduct").whereEqualTo("type","vans").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting balenciaga
        if (type != null && type.equalsIgnoreCase("balenciaga")){
            firestore.collection("PopularPropduct").whereEqualTo("type","balenciaga").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting fila
        if (type != null && type.equalsIgnoreCase("fila")){
            firestore.collection("PopularPropduct").whereEqualTo("type","fila").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting nike
        if (type != null && type.equalsIgnoreCase("nike")){
            firestore.collection("PopularPropduct").whereEqualTo("type","nike").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }

        //getting nike
        if (type != null && type.equalsIgnoreCase("adidas")){
            firestore.collection("PopularPropduct").whereEqualTo("type","adidas").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                        PopularModel popularModel = documentSnapshot.toObject(PopularModel.class);
                        homeCategoryModelList.add(popularModel);
                        homeCategoryAdapter.notifyDataSetChanged();
                    }
                    progressbar.setVisibility(View.GONE);
                }
            });
        }
    }

}