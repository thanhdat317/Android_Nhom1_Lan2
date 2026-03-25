package com.example.android_kt02;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.Activities.CategoryListActivity;
import com.example.android_kt02.Activities.LoginActivity;
import com.example.android_kt02.Activities.ProductListActivity;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.session.SessionManager;

public class MainActivity extends AppCompatActivity {
    private SessionManager sessionManager;
    private TextView txtLoginStatus;
    private Button btnLoginLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShopRepository.getInstance(this);
        sessionManager = new SessionManager(this);

        txtLoginStatus = findViewById(R.id.txtLoginStatus);
        btnLoginLogout = findViewById(R.id.btnLoginLogout);
        Button btnProducts = findViewById(R.id.btnProducts);
        Button btnCategories = findViewById(R.id.btnCategories);
        Button btnCurrentOrder = findViewById(R.id.btnCurrentOrder);

        btnLoginLogout.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                sessionManager.logout();
                updateSessionUi();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        btnProducts.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
        btnCategories.setOnClickListener(v -> startActivity(new Intent(this, CategoryListActivity.class)));
        btnCurrentOrder.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSessionUi();
    }

    private void updateSessionUi() {
        if (sessionManager.isLoggedIn()) {
            txtLoginStatus.setText(getString(R.string.logged_in_as, sessionManager.getUsername()));
            btnLoginLogout.setText(R.string.logout);
        } else {
            txtLoginStatus.setText(R.string.not_logged_in);
            btnLoginLogout.setText(R.string.login);
        }
    }

}