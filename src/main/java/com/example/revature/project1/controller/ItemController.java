package com.example.revature.project1.controller;

import com.example.revature.project1.dao.ItemDAO;
import com.example.revature.project1.model.Item;
import com.example.revature.project1.services.CartServicesImpl;
import com.example.revature.project1.services.ItemServices;
import com.example.revature.project1.utilities.CheckNegativeValues;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("item")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    Item item;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    ItemServices itemServices;
    @Autowired
    CheckNegativeValues checkNegativeValues;
    boolean result;
    ResponseEntity responseEntity = null;
    @GetMapping("/home")
    public ResponseEntity<String> home() {
        logger.info("homepage accessed");
        return new ResponseEntity<String>("Welcome to the Items Home", HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<Item>> getItems() {
        ResponseEntity responseEntity = null;
        List<Item> listOfItems = new ArrayList<Item>();
        listOfItems = itemServices.getItems();
        logger.info("displaying all items");
        return new ResponseEntity<List<Item>>(listOfItems, HttpStatus.OK);
    }
    @GetMapping("{itemId}")
    public ResponseEntity<Item> getItemById(@PathVariable("itemId") int itemId ) {
        System.out.println("Finding details on item no. "+itemId);
        ResponseEntity responseEntity = null;
        Item item1 = new Item();
        if (itemServices.isItemExists(itemId)) {
            item1 = itemServices.getItem(itemId);
            logger.info("Displaying details on item: "+item1.getItemId());
            responseEntity = new ResponseEntity<Item>(item1, HttpStatus.OK);
        } else {
            logger.error("no item with id "+itemId);
            responseEntity = new ResponseEntity<Item>(item1, HttpStatus.NO_CONTENT);
        } return responseEntity;
    }
    @GetMapping("/searchByName/{itemName}")
    public ResponseEntity<List<Item>> getItemByName(@PathVariable("itemName") String itemName ) {
        System.out.println("Finding details on item named "+ itemName);
        ResponseEntity responseEntity = null;
        Item item = new Item();
        if (itemDAO.findByItemName(itemName).size()==0) {
            logger.error("no item with the name "+itemName);
            responseEntity = new ResponseEntity<String>("No Items found", HttpStatus.NO_CONTENT);
        } else {
            List<Item> listOfItems = new ArrayList<Item>();
            listOfItems = itemServices.getItem(itemName);
            logger.info("Displaying details of "+itemName);
            responseEntity = new ResponseEntity<List<Item>>(listOfItems, HttpStatus.OK);
        } return responseEntity;
    }
    @PostMapping
    public ResponseEntity<String> saveItem(@RequestBody Item item) {
        ResponseEntity responseEntity = null;
        if(!itemServices.isItemExists(item.getItemId())) {
            result = itemServices.addItem(item);
            if(result) {
                logger.info("Item details successfully saved");
                responseEntity = new ResponseEntity<String>( "Successfully saved item: " + item.getItemId(),HttpStatus.OK);
            } else {
                logger.error("Either price or qoh was 0 or negative on the item");
                responseEntity = new ResponseEntity<String>( "Cannot save because either price or qoh is negative or 0 on id: " + item.getItemId(),HttpStatus.OK);
            }
        } else {
            logger.error("Item "+item.getItemId()+" already exists");
            responseEntity = new ResponseEntity<String>("Cannot save because this item with the id: "+item.getItemId()+" already exists", HttpStatus.CONFLICT); //202
        }
        return responseEntity;
    }
    @PutMapping
    public ResponseEntity<String> updateItem(@RequestBody Item item) {
        ResponseEntity responseEntity = null;
        if(itemServices.isItemExists(item.getItemId())) {
            result = itemServices.updateItem(item);
            if(result) {
                logger.info("Item details successfully saved");
                responseEntity = new ResponseEntity<String>( "Successfully updated item: " + item.getItemId(),HttpStatus.OK);
            } else {
                logger.error("Either price or qoh was 0 or negative on the item");
                responseEntity = new ResponseEntity<String>( "Cannot save because either price or qoh is negative or 0 on id: " + item.getItemId(),HttpStatus.OK);
            }
        } else {
            logger.error("An error has occurred");
            responseEntity = new ResponseEntity<String>("Cannot save because something went wrong", HttpStatus.CONFLICT); //202
        }
        return responseEntity;
    }
}
