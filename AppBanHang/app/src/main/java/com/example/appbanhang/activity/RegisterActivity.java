package com.example.appbanhang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.model.AccountModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText userName, Email, Mobile, Pass, rePass;
    Button button;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        progressBar.setVisibility(View.GONE);
        initControl();
    }

    private void initView() {
        progressBar = findViewById(R.id.progressbar);
        userName = findViewById(R.id.username);
        Email = findViewById(R.id.email);
        Mobile = findViewById(R.id.mobile);
        Pass = findViewById(R.id.password);
        rePass = findViewById(R.id.repass);
        button = findViewById(R.id.registerbtn);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void initControl() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!isConnected()) {
                        Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
                    } else {
                        createUser();
                        progressBar.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void createUser() throws Exception {
        String str_username = userName.getText().toString();
        String str_email = Email.getText().toString();
        String str_mobile = Mobile.getText().toString();
        String str_pass = Pass.getText().toString();
        String str_repass = rePass.getText().toString();
        if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(str_mobile)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Mobile", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(str_pass)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(str_repass)) {
            Toast.makeText(RegisterActivity.this, "Have not entered RePassword", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!str_pass.equals(str_repass)) {
            Toast.makeText(getApplicationContext(), "Confirmation password is incorrect", Toast.LENGTH_SHORT).show();
            rePass.clearComposingText();
            return;
        }

        auth.createUserWithEmailAndPassword(str_email, str_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            AccountModel accountModel = new AccountModel(str_username, str_email, str_mobile, str_pass);
                            String id = task.getResult().getUser().getUid();
                            firebaseDatabase.getReference().child("Users").child(id).setValue(accountModel);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Create account susscessfull", Toast.LENGTH_SHORT).show();
                            userName.setText("");
                            Email.setText("");
                            Mobile.setText("");
                            Pass.setText("");
                            rePass.setText("");
                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(RegisterActivity.this, "Error:" + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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