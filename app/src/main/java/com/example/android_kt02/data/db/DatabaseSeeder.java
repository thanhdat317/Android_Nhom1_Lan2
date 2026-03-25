package com.example.android_kt02.data.db;

import com.example.android_kt02.data.entity.Category;
import com.example.android_kt02.data.entity.Product;
import com.example.android_kt02.data.entity.User;

import java.util.ArrayList;
import java.util.List;

public final class DatabaseSeeder {
    private DatabaseSeeder() {
    }

    public static void seedIfNeeded(AppDatabase db) {
        if (db.userDao().count() == 0) {
            List<User> users = new ArrayList<>();
            users.add(new User("admin", "123456", "Administrator"));
            users.add(new User("alice", "123456", "Alice Nguyen"));
            users.add(new User("bob", "123456", "Bob Tran"));
            db.userDao().insertAll(users);
        }

        if (db.categoryDao().count() == 0) {
            List<Category> categories = new ArrayList<>();
            categories.add(new Category("Điện thoại", "Các dòng smartphone"));
            categories.add(new Category("Laptop", "Laptop văn phòng và gaming"));
            categories.add(new Category("Phụ kiện", "Tai nghe, chuột, bàn phím"));
            db.categoryDao().insertAll(categories);
        }

        if (db.productDao().count() == 0) {
            List<Product> products = new ArrayList<>();
            products.add(new Product("Pixel 9", "Điện thoại Android flagship", 19990000, 1));
            products.add(new Product("Galaxy S25", "Màn hình đẹp, camera tốt", 21990000, 1));
            products.add(new Product("MacBook Air M4", "Laptop mỏng nhẹ", 29990000, 2));
            products.add(new Product("ThinkPad X1", "Laptop doanh nhân", 32990000, 2));
            products.add(new Product("Chuột Logitech MX Master", "Chuột không dây cao cấp", 2490000, 3));
            products.add(new Product("Tai nghe Sony WH-1000XM6", "Chống ồn chủ động", 8990000, 3));
            db.productDao().insertAll(products);
        }
    }
}

