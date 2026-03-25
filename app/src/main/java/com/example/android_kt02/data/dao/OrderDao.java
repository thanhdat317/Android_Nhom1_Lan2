package com.example.android_kt02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_kt02.data.entity.Order;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders WHERE user_id = :userId AND status = 'OPEN' LIMIT 1")
    Order getOpenOrderByUser(int userId);

    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    Order getById(int orderId);

    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Query("UPDATE orders SET total_amount = :totalAmount WHERE id = :orderId")
    void updateTotal(int orderId, double totalAmount);

    @Query("UPDATE orders SET status = 'PAID' WHERE id = :orderId")
    void markPaid(int orderId);
}

/*

@Dao
public interface OrderDao {
    @Query("SELECT * FROM orders WHERE user_id = :userId AND status = 'OPEN' LIMIT 1")
    Order getOpenOrderByUser(int userId);

    @Query("SELECT * FROM orders WHERE id = :orderId LIMIT 1")
    Order getById(int orderId);

    @Insert
    long insert(Order order);

    @Update
    void update(Order order);

    @Query("UPDATE orders SET total_amount = :totalAmount WHERE id = :orderId")
    void updateTotal(int orderId, double totalAmount);

    @Query("UPDATE orders SET status = 'PAID' WHERE id = :orderId")
    void markPaid(int orderId);
}
*/



