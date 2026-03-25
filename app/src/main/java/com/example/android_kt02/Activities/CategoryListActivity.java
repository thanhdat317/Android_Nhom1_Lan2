package com.example.android_kt02.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android_kt02.R;
import com.example.android_kt02.common.AppConstants;
import com.example.android_kt02.data.ShopRepository;
import com.example.android_kt02.data.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    private final List<Category> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        ListView listView = findViewById(R.id.listCategories);

        categories.clear();
        categories.addAll(ShopRepository.getInstance(this).getCategories());

        List<String> labels = new ArrayList<>();
        for (Category category : categories) {
            labels.add(category.getName() + "\n" + category.getDescription());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, labels);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Category category = categories.get(position);
            Intent intent = new Intent(this, ProductListActivity.class);
            intent.putExtra(AppConstants.EXTRA_CATEGORY_ID, category.getId());
            intent.putExtra(AppConstants.EXTRA_CATEGORY_NAME, category.getName());
            startActivity(intent);
        });
    }
}

