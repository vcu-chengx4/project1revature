package com.example.revature.project1.dao;

import com.example.revature.project1.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ItemDAO extends JpaRepository<Item,Integer> {
    @Query("SELECT i FROM Item i")
    public List<Item> getAllItems();
    public Item findById(int itemId);
    public List<Item> findByItemName(String itemName);
    public List<Item> findByPrice(int price);
    public List<Item> findByQoh(int qoh);
    public List<Item> findByPriceBetween(int minimumPrice, int maximumPrice);
    public List<Item> findByQohLessThan(int qoh);
}
