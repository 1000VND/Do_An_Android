package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.dao.AccountDAO;
import com.example.appbanhang.fragment.HomeFragment;
import com.example.appbanhang.model.AccountModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText txtEmail, txtPass;
    TextInputLayout loEmail, loPass;
    Button loginbtn, signupbtn;
    AccountDAO accountDAO;
    AccountModel accountModel;
    Intent intent;
    TextView txtForgotpaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initControll();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    locginAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loEmail.setError(null);
            }
        });

        txtPass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER){
                    locginAction();
                }
                return false;
            }
        });

        txtPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loPass.setError(null);
            }
        });

        txtForgotpaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }

    private void locginAction() {
        if (!isConnected()) {
            Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
        } else {
            if (txtEmail.getText().toString().isEmpty() && txtPass.getText().toString().isEmpty()) {
                loEmail.requestFocus();
                loEmail.setError("Have not entered Email");
                loPass.requestFocus();
                loPass.setError("Have not entered Password");
            } else if (txtEmail.getText().toString().isEmpty() && !txtPass.getText().toString().isEmpty()) {
                loEmail.requestFocus();
                loEmail.setError("Have not entered Email");
                loPass.setError(null);
            } else if (txtPass.getText().toString().isEmpty() && !txtEmail.getText().toString().isEmpty()) {
                loPass.requestFocus();
                loPass.setError("Have not entered Password");
                loEmail.setError(null);
            } else {
                String email = txtEmail.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();
                accountDAO = new AccountDAO();
                accountModel = accountDAO.CheckPass(email, pass);
                if (accountModel != null) {
                    Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                    txtEmail.setText("");
                    txtPass.setText("");
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                    finishAffinity();
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    txtPass.setText("");
                    txtEmail.setText("");
                }
                txtEmail.clearFocus();
                txtPass.clearFocus();
                loEmail.setError(null);
                loPass.setError(null);
            }
        }
    }

    private void initView() {
        txtEmail = findViewById(R.id.email);
        txtPass = findViewById(R.id.password);
        loEmail = findViewById(R.id.emaillayout);
        loPass = findViewById(R.id.passwordlayout);
        loginbtn = findViewById(R.id.loginbtn);
        signupbtn = findViewById(R.id.signupbtn);
        txtForgotpaw = findViewById(R.id.forgotpassword);
    }

    private void initControll() {
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