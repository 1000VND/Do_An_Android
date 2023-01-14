package com.example.appbanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class OrderFragment extends Fragment {
    RecyclerView recycleview;
    Toolbar toolbar;
    List<OrderModel> orderModelList;
    OrderAdapter orderAdapter;
    FirebaseFirestore db;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_order, container, false);
        initView();
        //Order history
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderAdapter = new OrderAdapter(getActivity(), orderModelList);
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
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return view;
    }

    private void initView() {
        db = FirebaseFirestore.getInstance();
        orderModelList = new ArrayList<>();
        recycleview = view.findViewById(R.id.recycleview);
        toolbar = view.findViewById(R.id.toolbar);
    }
}