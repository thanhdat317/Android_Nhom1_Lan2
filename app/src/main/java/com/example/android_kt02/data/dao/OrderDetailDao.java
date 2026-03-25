package com.example.android_kt02.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.android_kt02.data.dto.OrderLine;
import com.example.android_kt02.data.entity.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {
    @Query("SELECT * FROM order_details WHERE order_id = :orderId AND product_id = :productId LIMIT 1")
    OrderDetail getByOrderAndProduct(int orderId, int productId);

    @Insert
    long insert(OrderDetail orderDetail);

    @Update
    void update(OrderDetail orderDetail);

    @Query("SELECT COALESCE(SUM(quantity * unit_price), 0) FROM order_details WHERE order_id = :orderId")
    double calculateOrderTotal(int orderId);

    @Query("SELECT p.name AS product_name, od.quantity AS quantity, od.unit_price AS unit_price, (od.quantity * od.unit_price) AS line_total " +
            "FROM order_details od INNER JOIN products p ON p.id = od.product_id WHERE od.order_id = :orderId ORDER BY p.name")
    List<OrderLine> getLinesByOrderId(int orderId);
}

