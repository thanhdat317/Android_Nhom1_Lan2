package com.example.android_kt02.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.MainActivity;
import com.example.android_kt02.R;
import com.example.android_kt02.common.AppConstants;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.data.dto.OrderLine;
import com.example.android_kt02.data.entity.Order;
import com.example.android_kt02.session.SessionManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InvoiceActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);

        SessionManager sessionManager = new SessionManager(this);
        if (!sessionManager.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        int orderId = getIntent().getIntExtra(AppConstants.EXTRA_ORDER_ID, -1);
        ShopRepository repository = ShopRepository.getInstance(this);
        Order order = repository.getOrderById(orderId);

        if (order == null || order.getUserId() != sessionManager.getUserId() || !"PAID".equals(order.getStatus())) {
            Toast.makeText(this, R.string.order_not_found, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        TextView txtInvoiceHeader = findViewById(R.id.txtInvoiceHeader);
        ListView listInvoiceItems = findViewById(R.id.listInvoiceItems);
        TextView txtInvoiceTotal = findViewById(R.id.txtInvoiceTotal);
        Button btnBackHome = findViewById(R.id.btnBackHome);

        String time = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                .format(new Date(order.getCreatedAt()));
        txtInvoiceHeader.setText(getString(R.string.invoice_header, order.getId(), order.getStatus(), time));

        List<OrderLine> lines = repository.getOrderLines(order.getId());
        List<String> labels = new ArrayList<>();
        for (OrderLine line : lines) {
            labels.add(line.getProductName() + " x" + line.getQuantity() + " - " +
                    String.format(Locale.US, "%,.0f", line.getLineTotal()) + " VND");
        }

        listInvoiceItems.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels));
        txtInvoiceTotal.setText(getString(R.string.cart_total, String.format(Locale.US, "%,.0f", order.getTotalAmount())));

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}

