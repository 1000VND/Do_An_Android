package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.Utils;
import com.example.appbanhang.model.CartModel;
import com.example.appbanhang.model.PopularModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextInputEditText firstname, lastname, email, phone, address;
    Button btnmuahang;
    TextView summary;
    String transummary;
    FirebaseFirestore firestore;
    List<String> cardIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        transummary = getIntent().getStringExtra("summary");
        summary.setText(transummary);
        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstname.getText().toString().isEmpty()
                        && lastname.getText().toString().isEmpty()
                        && address.getText().toString().isEmpty()
                        && email.getText().toString().isEmpty()
                        && phone.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Not enough information has been entered", Toast.LENGTH_LONG).show();
                } else {
                    addToOrderHistory();
                }
            }
        });

        firestore.collection("cartPopular")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String cardId = document.getId();
                                cardIdList.add(cardId);
                            }
                        }
                    }
                });
    }

    private void addToOrderHistory() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calendarDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = currentDate.format(calendarDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarDate.getTime());

        final Map<String, Object> cartMap = new HashMap<>();

        cartMap.put("summary", summary.getText().toString());
        cartMap.put("firstname", firstname.getText().toString());
        cartMap.put("lastname", lastname.getText().toString());
        cartMap.put("address", address.getText().toString());
        cartMap.put("email", email.getText().toString());
        cartMap.put("phone", phone.getText().toString());
        cartMap.put("currentDate", saveCurrentDate);
        cartMap.put("currentTime", saveCurrentTime);

        firestore.collection("orderHistory")
                .add(cartMap)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(PayActivity.this, "Add to order success", Toast.LENGTH_SHORT).show();
                        deleteDocument(cardIdList);
                        Utils.cartList = new ArrayList<>();
                        finish();
                    }
                });
    }

    private void deleteDocument(List<String> cardIdList) {
        for (String item : cardIdList) {
            firestore.collection("cartPopular").document(item).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getApplicationContext(), "Cart has been emptied", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        address = findViewById(R.id.address);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        btnmuahang = findViewById(R.id.btnmuahang);
        firestore = FirebaseFirestore.getInstance();
        summary = findViewById(R.id.summary);
        cardIdList = new ArrayList<>();

    }
}