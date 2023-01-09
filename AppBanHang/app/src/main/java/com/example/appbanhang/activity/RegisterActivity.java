package com.example.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.dao.AccountDAO;
import com.example.appbanhang.model.AccountModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    TextInputEditText username, email, mobile, pass, repass;
    TextInputLayout lusername, lemail, lmobile, lpass, lrepass;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initControl();
    }

    private void initView() {
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.repass);

        button = findViewById(R.id.registerbtn);

        lusername = findViewById(R.id.usernamelayout);
        lemail = findViewById(R.id.emaillayout);
        lmobile = findViewById(R.id.mobilelayout);
        lpass = findViewById(R.id.passwordlayout);
        lrepass = findViewById(R.id.repasslayout);
    }

    private void initControl() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!isConnected()) {
                        Toast.makeText(getApplicationContext(), "Internet not availble. Check your internet", Toast.LENGTH_SHORT).show();
                    } else {
                        dangKi();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void dangKi() throws Exception {
        String str_username = username.getText().toString().trim();
        String str_email = email.getText().toString().trim();
        String str_mobile = mobile.getText().toString().trim();
        String str_pass = pass.getText().toString().trim();
        String str_repass = repass.getText().toString().trim();
        if (TextUtils.isEmpty(str_username)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Username", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_mobile)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Mobile", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_pass)) {
            Toast.makeText(RegisterActivity.this, "Have not entered Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_repass)) {
            Toast.makeText(RegisterActivity.this, "Have not entered RePassword", Toast.LENGTH_SHORT).show();
        } else {
            if (str_pass.equals(str_repass)) {
                AccountDAO dao = new AccountDAO();
                AccountModel exitsEmail = dao.CheckEmail(email.getText().toString().trim());
                if (exitsEmail != null) {
                    Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    email.setText("");
                } else {
                    AccountModel data = accountModel();
                    if (dao.AddAccount(data)) {
                        Toast.makeText(getApplicationContext(), "Create account susscess", Toast.LENGTH_SHORT).show();
                        username.setText("");
                        email.setText("");
                        mobile.setText("");
                        pass.setText("");
                        repass.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Create account failed", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "Confirmation password is incorrect", Toast.LENGTH_SHORT).show();
                repass.clearComposingText();
            }
        }
    }

    private AccountModel accountModel() {
        AccountModel ac = new AccountModel();
        ac.setUserName(username.getText().toString());
        ac.setEmail(email.getText().toString());
        ac.setPhone(mobile.getText().toString());
        ac.setPassWord(pass.getText().toString());
        return ac;
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