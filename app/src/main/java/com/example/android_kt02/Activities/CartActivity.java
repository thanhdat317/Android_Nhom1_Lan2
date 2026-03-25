package com.example.android_kt02.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.R;
import com.example.android_kt02.common.AppConstants;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.data.dto.OrderLine;
import com.example.android_kt02.data.entity.Order;
import com.example.android_kt02.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartActivity extends AppCompatActivity {
    private ShopRepository repository;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        repository = ShopRepository.getInstance(this);
        sessionManager = new SessionManager(this);

        Button btnContinue = findViewById(R.id.btnContinueShopping);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        btnContinue.setOnClickListener(v -> startActivity(new Intent(this, ProductListActivity.class)));
        btnCheckout.setOnClickListener(v -> checkout());
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
    }

    private void loadCart() {
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(AppConstants.EXTRA_RETURN_TO_CART, true);
            startActivity(intent);
            finish();
            return;
        }

        TextView txtCartInfo = findViewById(R.id.txtCartInfo);
        ListView listCartItems = findViewById(R.id.listCartItems);
        Button btnCheckout = findViewById(R.id.btnCheckout);

        Order openOrder = repository.getOpenOrderForUser(sessionManager.getUserId());
        if (openOrder == null) {
            txtCartInfo.setText(R.string.cart_empty_message);
            listCartItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, new ArrayList<>()));
            btnCheckout.setEnabled(false);
            return;
        }

        List<OrderLine> lines = repository.getOrderLines(openOrder.getId());
        List<String> labels = new ArrayList<>();
        for (OrderLine line : lines) {
            labels.add(line.getProductName() + " x" + line.getQuantity() + " - " +
                    String.format(Locale.US, "%,.0f", line.getLineTotal()) + " VND");
        }

        txtCartInfo.setText(getString(R.string.cart_total, String.format(Locale.US, "%,.0f", openOrder.getTotalAmount())));
        listCartItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels));
        btnCheckout.setEnabled(!lines.isEmpty());
    }

    private void checkout() {
        if (!sessionManager.isLoggedIn()) {
            return;
        }

        int orderId = repository.checkoutOpenOrder(sessionManager.getUserId());
        if (orderId <= 0) {
            Toast.makeText(this, R.string.cart_empty_message, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, InvoiceActivity.class);
        intent.putExtra(AppConstants.EXTRA_ORDER_ID, orderId);
        startActivity(intent);
        finish();
    }
}

