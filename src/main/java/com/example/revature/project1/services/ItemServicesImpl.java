package com.example.revature.project1.services;

import com.example.revature.project1.dao.ItemDAO;
import com.example.revature.project1.model.Item;
import com.example.revature.project1.utilities.CheckNegativeValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServicesImpl implements ItemServices {
    @Autowired
    Item item;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    CheckNegativeValues checkNegativeValues;
    @Override
    public boolean addItem(Item item) {
        if(checkNegativeValues.checkNegativeValue(item.getQoh()) || checkNegativeValues.checkNegativeValue(item.getPrice())) {
            return false;
        } else {
            itemDAO.save(item);
            return true;
        }
    }

    @Override
    public boolean deleteItem(int itemId) {
        itemDAO.deleteById(itemId);
        return true;
    }

    @Override
    public boolean updateItem(Item item) {
        if(checkNegativeValues.checkNegativeValue(item.getQoh()) || checkNegativeValues.checkNegativeValue(item.getPrice())) {
            return false;
        } else {
            itemDAO.save(item);
            return true;
        }
    }

    @Override
    public Item getItem(int itemId) {
        return itemDAO.findById(itemId);
    }

    @Override
    public boolean isItemExists(int itemId) {
        return itemDAO.existsById(itemId);
    }


    @Override
    public List<Item> getItems() {
        return itemDAO.findAll();
    }

    @Override
    public List<Item> getItem(String itemName) {
        return itemDAO.findByItemName(itemName);
    }

    @Override
    public List<Item> filterItemByPrice(int minimumPrice, int maximumPrice) {
        return itemDAO.findByPriceBetween(minimumPrice, maximumPrice);
    }
    @Override
    public List<Item> getItemByPrice(int price) {
        return itemDAO.findByPrice(price) ;
    }
}
