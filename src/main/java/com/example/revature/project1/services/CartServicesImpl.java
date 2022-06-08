package com.example.revature.project1.services;

import com.example.revature.project1.controller.UserController;
import com.example.revature.project1.dao.CartDAO;
import com.example.revature.project1.dao.ItemDAO;
import com.example.revature.project1.dao.UserDAO;
import com.example.revature.project1.exceptions.NotLoggedInException;
import com.example.revature.project1.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartServicesImpl implements CartServices{
    private static final Logger logger = LoggerFactory.getLogger(CartServicesImpl.class);
    @Autowired
    User user;
    @Autowired
    Item item;
    @Autowired
    Cart cart;
    @Autowired
    Order order;
    @Autowired
    UserServices userServices;
    @Autowired
    CartDAO cartDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CartIdentification cartPK;
    @Autowired
    HttpServletRequest request;
    @Override
    public boolean addItemToCart(Cart cart) {
        HttpSession session = request.getSession();
        if(session==null){
            logger.error("NotLoggedInException thrown");
            throw new NotLoggedInException(String.format("You must log in to do that!"));
        } else {
            user.setItemList(null);
            userDAO.save(user);
            cart.setUserId(userServices.getUser(User.loggedAs));
            cartDAO.save(cart);
            logger.info("addItemToCart method successfully fired");
            return true;
        }
    }
    @Override
    public void addItemToOrders(int cartId) {

    }
    @Override
    public List<Item> getItems() {
        return null;
    }
}
