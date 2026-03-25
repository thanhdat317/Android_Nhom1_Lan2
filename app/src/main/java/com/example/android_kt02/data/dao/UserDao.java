package com.example.android_kt02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android_kt02.data.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    User getById(int id);

    @Insert
    void insertAll(List<User> users);

    @Query("SELECT COUNT(*) FROM users")
    int count();
}

