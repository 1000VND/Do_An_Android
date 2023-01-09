package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.dao.AccountDAO;
import com.example.appbanhang.model.AccountModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class ForgetPasswordActivity extends AppCompatActivity {
    TextInputEditText txtEmail;
    TextInputLayout loEmail;
    TextView pass;
    Button getpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
        initControll();
    }

    private void initControll() {
        getpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isConnected()) {
                    Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isEmpty(txtEmail.getText().toString())) {
                        loEmail.requestFocus();
                        loEmail.setError("Have not entered Email");
                    } else {
                        AccountDAO dao = new AccountDAO();
                        AccountModel data = dao.CheckEmail(txtEmail.getText().toString().trim());
                        if (data != null) {
                            pass.setText("Your password: " + data.getPassWord());
                        }
                    }
                }
            }
        });
    }

    private void initView() {
        txtEmail = findViewById(R.id.email);
        loEmail = findViewById(R.id.emaillayout);
        pass = (TextView) findViewById(R.id.showpass);
        getpass = findViewById(R.id.getpass);
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