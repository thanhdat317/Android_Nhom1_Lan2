package com.example.android_kt02.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.R;
import com.example.android_kt02.common.AppConstants;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.data.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductListActivity extends AppCompatActivity {
    private final List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        TextView txtTitle = findViewById(R.id.txtProductListTitle);
        ListView listProducts = findViewById(R.id.listProducts);

        int categoryId = getIntent().getIntExtra(AppConstants.EXTRA_CATEGORY_ID, -1);
        String categoryName = getIntent().getStringExtra(AppConstants.EXTRA_CATEGORY_NAME);

        if (categoryId > 0 && categoryName != null) {
            txtTitle.setText(getString(R.string.product_list_by_category, categoryName));
        }

        products.clear();
        products.addAll(ShopRepository.getInstance(this).getProducts(categoryId > 0 ? categoryId : null));

        List<String> labels = new ArrayList<>();
        for (Product product : products) {
            labels.add(product.getName() + " - " + String.format(Locale.US, "%,.0f", product.getPrice()) + " VND");
        }

        listProducts.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels));
        listProducts.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, ProductDetailActivity.class);
            intent.putExtra(AppConstants.EXTRA_PRODUCT_ID, products.get(position).getId());
            startActivity(intent);
        });
    }
}

