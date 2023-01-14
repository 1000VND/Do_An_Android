package com.example.appbanhang.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.appbanhang.Adapter.HomeCategoryAdapter;
import com.example.appbanhang.Adapter.PopularAdapters;
import com.example.appbanhang.R;
import com.example.appbanhang.model.HomeCategoryModel;
import com.example.appbanhang.model.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    ImageSlider imageSlider;
    View view;
    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView poRecyclerView, homeCatRec;
    FirebaseFirestore db;

    //popular items
    PopularAdapters popularAdapters;
    ArrayList<PopularModel> popularModelList;

    //Home Category
    List<HomeCategoryModel> categoryModelList;
    HomeCategoryAdapter homeCategoryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://bizweb.dktcdn.net/100/427/221/products/325dd85d-75d2-4408-a01b-4675c4b982d3.jpg?v=1626667729313", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.wired.co.uk/photos/63727049ab57b5ecdfc2fb42/16:9/w_2560%2Cc_limit/Nike-Swoosh-News-Gear.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://c.files.bbci.co.uk/44CF/production/_117751671_satan-shoes1.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://media.gq.com/photos/62fd35143f91baeb482811d2/master/w_1600%2Cc_limit/GQ0922_Nike_59.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://images.stockx.com/images/Nike-Dunk-Low-Retro-QS-Flash-White-Argon-Blue-Flash-Product.jpg?fit=fill&bg=FFFFFF&w=1200&h=857&fm=webp&auto=compress&dpr=2&trim=color&updated_at=1658842672&q=751", ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);


        //Popular product
        poRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        poRecyclerView.setAdapter(popularAdapters);

        db.collection("PopularPropduct")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "Error" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //Home category
        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeCategoryAdapter = new HomeCategoryAdapter(getActivity(),categoryModelList);
        homeCatRec.setAdapter(homeCategoryAdapter);

        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategoryModel homeCategoryModel = document.toObject(HomeCategoryModel.class);
                                categoryModelList.add(homeCategoryModel);
                                homeCategoryAdapter.notifyDataSetChanged();
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
        popularModelList = new ArrayList<>();
        categoryModelList = new ArrayList<>();
        imageSlider = view.findViewById(R.id.imageSlider);
        poRecyclerView = view.findViewById(R.id.allProduct);
        homeCatRec = view.findViewById(R.id.categories);
        scrollView = view.findViewById(R.id.scrollview);
        progressBar = view.findViewById(R.id.progressbar);
    }
}