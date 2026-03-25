
package com.example.android_kt02.Activities;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.MainActivity;
import com.example.android_kt02.R;
import com.example.android_kt02.common.AppConstants;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.data.entity.User;
import com.example.android_kt02.session.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private ShopRepository repository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        repository = ShopRepository.getInstance(this);
        sessionManager = new SessionManager(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnLogin = findViewById(R.id.btnDoLogin);

        edtUsername.setText("admin");
        edtPassword.setText("123456");

        btnLogin.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.login_required_message, Toast.LENGTH_SHORT).show();
            return;
        }

        User user = repository.login(username, password);
        if (user == null) {
            Toast.makeText(this, R.string.login_failed_message, Toast.LENGTH_SHORT).show();
            return;
        }

        sessionManager.login(user.getId(), user.getUsername());
        Toast.makeText(this, R.string.login_success_message, Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        boolean addAfterLogin = intent.getBooleanExtra(AppConstants.EXTRA_ADD_AFTER_LOGIN, false);
        boolean returnToCart = intent.getBooleanExtra(AppConstants.EXTRA_RETURN_TO_CART, false);
        int productId = intent.getIntExtra(AppConstants.EXTRA_PRODUCT_ID, -1);

        if (addAfterLogin && productId > 0) {
            repository.addProductToCurrentOrder(user.getId(), productId);
            startActivity(new Intent(this, CartActivity.class));
        } else if (returnToCart) {
            startActivity(new Intent(this, CartActivity.class));
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
        finish();
    }
}

