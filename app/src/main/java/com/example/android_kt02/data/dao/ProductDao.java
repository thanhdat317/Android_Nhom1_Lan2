package com.example.android_kt02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_kt02.data.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE category_id = :categoryId ORDER BY name")
    List<Product> getByCategoryId(int categoryId);

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    Product getById(int id);

    @Insert
    void insertAll(List<Product> products);

    @Query("SELECT COUNT(*) FROM products")
    int count();
}


/*
package com.example.android_kt02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_kt02.data.entity.Product;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM products ORDER BY name")
    List<Product> getAll();

    @Query("SELECT * FROM products WHERE category_id = :categoryId ORDER BY name")
    List<Product> getByCategoryId(int categoryId);

    @Query("SELECT * FROM products WHERE id = :id LIMIT 1")
    Product getById(int id);

    @Insert
    void insertAll(List<Product> products);

    @Query("SELECT COUNT(*) FROM products")
    int count();
}

*/

