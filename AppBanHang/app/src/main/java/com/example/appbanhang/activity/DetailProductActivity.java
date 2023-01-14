package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appbanhang.R;
import com.example.appbanhang.Utils;
import com.example.appbanhang.model.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailProductActivity extends AppCompatActivity {

    PopularModel popularModel;
    Toolbar toolbar;
    ImageView imageView;
    TextView description, name, price, detail, discount;
    Button addtocart, favorite;
    FirebaseFirestore firestore;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        initView();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        popularModel = (PopularModel) getIntent().getSerializableExtra("detail");
        Glide.with(getApplicationContext()).load(popularModel.getImgUrl()).into(imageView);
        description.setText(popularModel.getDescriptionPopular());
        name.setText(popularModel.getNamePopular());
        price.setText(popularModel.getDiscount() != 0 ? (popularModel.getPrice() - ((popularModel.getPrice() * popularModel.getDiscount()) / 100)) + " VND" : decimalFormat.format(popularModel.getPrice()) + " VND");
        discount.setText((popularModel.getDiscount() == 0 ?
                "" :
                "Discount: " + Integer.toString(popularModel.getDiscount()) + "% Off"
        ));
        detail.setText(popularModel.getDetail());

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
                } else {
                    addToCart();
                }
            }
        });
    }

    private void addToCart() {
        boolean isSave = true;
        if (Utils.cartList.size() == 0) {
            Utils.cartList.add(name.getText().toString());
        } else {
            for (String item : Utils.cartList) {
                if (!item.equals(name.getText())) {
                    Utils.cartList.add(name.getText().toString());
                } else {
                    isSave = false;
                }
            }
        }

        String saveCurrentDate, saveCurrentTime;
        Calendar calendarDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calendarDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarDate.getTime());

        final Map<String, Object> cartMap = new HashMap<>();

        cartMap.put("namePopular", name.getText().toString());
        cartMap.put("price", popularModel.getPrice());
        cartMap.put("description", description.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);
        cartMap.put("imgUrl", popularModel.getImgUrl().toString());
        cartMap.put("quantity", 1);

        if (isSave) {
            firestore.collection("cartPopular")
                    .add(cartMap)
                    .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(DetailProductActivity.this, "Add to cart success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }else {
            Toast.makeText(DetailProductActivity.this, "Add to cart fail", Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        imageView = findViewById(R.id.detail_img);
        description = findViewById(R.id.description);
        name = findViewById(R.id.nameproduct);
        price = findViewById(R.id.price);
        detail = findViewById(R.id.detail);
        addtocart = findViewById(R.id.addcart);
        favorite = findViewById(R.id.addfavorite);
        discount = findViewById(R.id.discount);
        addtocart = findViewById(R.id.addcart);
        favorite = findViewById(R.id.addfavorite);
        firestore = FirebaseFirestore.getInstance();
    }

    boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.isConnected())
                return true;
            else
                return false;
        } else
            return false;
    }

}
