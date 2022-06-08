package com.example.revature.project1.dao;

import com.example.revature.project1.model.Cart;
import com.example.revature.project1.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderDAO extends JpaRepository<Order,Integer> {
    @Query(value="insert into project1.order (order_id, cart_id, user_id, count_of_unique_items, price) VALUES (?1, ?2, ?3, ?4, ?5)", nativeQuery = true)
    public void checkOut(int orderId, int cartId, int userId, int countOfUniqueItems,double price);
    public Order findById(int orderId);
}
