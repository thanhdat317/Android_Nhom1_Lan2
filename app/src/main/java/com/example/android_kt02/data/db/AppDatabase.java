package com.example.android_kt02.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android_kt02.data.dao.CategoryDao;
import com.example.android_kt02.data.dao.OrderDao;
import com.example.android_kt02.data.dao.OrderDetailDao;
import com.example.android_kt02.data.dao.ProductDao;
import com.example.android_kt02.data.dao.UserDao;
import com.example.android_kt02.data.entity.Category;
import com.example.android_kt02.data.entity.Order;
import com.example.android_kt02.data.entity.OrderDetail;
import com.example.android_kt02.data.entity.Product;
import com.example.android_kt02.data.entity.User;

@Database(entities = {User.class, Category.class, Product.class, Order.class, OrderDetail.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();

    public abstract OrderDao orderDao();

    public abstract OrderDetailDao orderDetailDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "shop.db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

