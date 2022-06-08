package com.example.revature.project1.dao;

import com.example.revature.project1.model.Cart;
import com.example.revature.project1.model.CartIdentification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository

public interface CartDAO extends JpaRepository<Cart, CartIdentification> {

    void deleteByCartIdCartId(int cartId);
    List<Cart> findByCartIdUserId(int userId);
    @Query(value="insert into project1.cart (cart_id, item_id, user_id, item_qoh) VALUES (?1, ?2, ?3, ?4)", nativeQuery = true)
    void insertToCart(int cartId, int itemId, int userId, int qoh);
}
