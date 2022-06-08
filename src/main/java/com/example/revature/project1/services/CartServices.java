package com.example.revature.project1.services;

import com.example.revature.project1.model.Cart;
import com.example.revature.project1.model.Item;
import com.example.revature.project1.model.User;

import java.util.List;

public interface CartServices {
    boolean addItemToCart(Cart cart);
    void addItemToOrders(int cartId);
    List<Item> getItems();
}
