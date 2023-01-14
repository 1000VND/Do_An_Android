package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.model.AccountModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class ForgetPasswordActivity extends AppCompatActivity {
    TextInputEditText txtEmail;
    TextInputLayout loEmail;
    TextView pass;
    Button getpass;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
        progressBar.setVisibility(View.GONE);
        initControll();
    }

    private void initControll() {
        getpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
                } else {
                    String inputemail = txtEmail.getText().toString();
                    auth.sendPasswordResetEmail(inputemail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(ForgetPasswordActivity.this, "Reset email instructions sent to " + inputemail, Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                                        progressBar.setVisibility(View.VISIBLE);
                                        finish();
                                    } else {
                                        Toast.makeText(ForgetPasswordActivity.this, "Email " + inputemail + " does not exist", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForgetPasswordActivity.this, "Email " + inputemail + " does not exist", Toast.LENGTH_LONG).show();
                                }
                            });

                }
            }
        });
    }

    private void initView() {
        txtEmail = (TextInputEditText)findViewById(R.id.email);
        loEmail = findViewById(R.id.emaillayout);
        pass = (TextView) findViewById(R.id.showpass);
        getpass = findViewById(R.id.getpass);
        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
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
