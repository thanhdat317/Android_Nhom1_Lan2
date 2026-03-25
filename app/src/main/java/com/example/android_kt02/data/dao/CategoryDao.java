package com.example.android_kt02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_kt02.data.entity.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name")
    List<Category> getAll();

    @Insert
    void insertAll(List<Category> categories);

    @Query("SELECT COUNT(*) FROM categories")
    int count();
}

