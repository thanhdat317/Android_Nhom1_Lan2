package com.example.android_kt02.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.R;
import com.example.android_kt02.common.AppConstants;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.data.entity.Product;
import com.example.android_kt02.session.SessionManager;

import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {
    private Product product;
    private SessionManager sessionManager;
    private ShopRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        sessionManager = new SessionManager(this);
        repository = ShopRepository.getInstance(this);

        int productId = getIntent().getIntExtra(AppConstants.EXTRA_PRODUCT_ID, -1);
        product = repository.getProductById(productId);

        TextView txtName = findViewById(R.id.txtDetailName);
        TextView txtPrice = findViewById(R.id.txtDetailPrice);
        TextView txtDescription = findViewById(R.id.txtDetailDescription);
        Button btnAdd = findViewById(R.id.btnAddToOrder);

        if (product == null) {
            Toast.makeText(this, R.string.product_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        txtName.setText(product.getName());
        txtPrice.setText(getString(R.string.product_price, String.format(Locale.US, "%,.0f", product.getPrice())));
        txtDescription.setText(product.getDescription());

        btnAdd.setOnClickListener(v -> addToOrder());
    }

    private void addToOrder() {
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(AppConstants.EXTRA_ADD_AFTER_LOGIN, true);
            intent.putExtra(AppConstants.EXTRA_PRODUCT_ID, product.getId());
            startActivity(intent);
            return;
        }

        repository.addProductToCurrentOrder(sessionManager.getUserId(), product.getId());
        Toast.makeText(this, R.string.added_to_order_message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, CartActivity.class));
    }
}

