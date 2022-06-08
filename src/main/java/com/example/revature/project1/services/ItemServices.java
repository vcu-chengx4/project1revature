package com.example.revature.project1.services;

import com.example.revature.project1.model.Item;

import java.util.List;

public interface ItemServices {
    boolean addItem(Item item);
    boolean deleteItem(int itemId);
    boolean updateItem(Item item);
    Item getItem(int itemId);
    boolean isItemExists(int itemId);
    List<Item> getItems();
    List<Item> getItem(String itemName);
    List<Item> filterItemByPrice(int minimumPrice, int maximumPrice);
    List<Item> getItemByPrice(int price);

}
