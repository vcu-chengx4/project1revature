package com.example.revature.project1.services;

import com.example.revature.project1.controller.UserController;
import com.example.revature.project1.dao.UserDAO;
import com.example.revature.project1.exceptions.NotLoggedInException;
import com.example.revature.project1.exceptions.UserNotFoundException;
import com.example.revature.project1.model.User;
import com.example.revature.project1.utilities.CheckEmailValidity;
import com.example.revature.project1.utilities.CheckPassValidity;
import org.hibernate.annotations.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Service
public class UserServicesImpl implements UserServices{
    private static final Logger logger = LoggerFactory.getLogger(UserServicesImpl.class);
    @Autowired
    User user;
    @Autowired
    UserDAO userDAO;
    @Autowired
    CheckEmailValidity checkEmailValidity;
    @Autowired
    CheckPassValidity checkPassValidity;
    Scanner inputReader = new Scanner(System.in);
    @Autowired
    HttpServletRequest request;
    @Override
    public boolean register(User us) {
        if (checkEmailValidity.checkEmailValidity(us.getUserEmail()) && checkPassValidity.CheckPassValidity(us.getUserPassword())) {
            logger.info("register method successfully fired");
            us.setItemList();
            userDAO.save(us);
            return true;
        }
        else {
            logger.error("invalid email or password was entered on register method");
            return false;
        }
    }
    @Override
    public User login(String email, String password) {
        User loggedIn = userDAO.findByUserEmailAndUserPassword(email, password);
        if(loggedIn==null) { logger.error("UserNotFoundException thrown"); throw new UserNotFoundException(String.format("No User was found with the current credentials")); }
        HttpSession session = request.getSession();
        session.setAttribute("currentUser",loggedIn);
        return loggedIn;
    }
    @Override
    public void logout() {
        HttpSession session = request.getSession(false);
        if(session == null) {
            logger.error("NotLoggedInException thrown");
            throw new NotLoggedInException(String.format("You're not logged in!"));
        }
        logger.info("logout method successfully fired");
        session.invalidate();
    }

    @Override
    public List<User> getUsers() {
        logger.info("getUsers method successfully fired");
        return userDAO.findAll();
    }

    @Override
    public boolean isUserExists(int Id) {
        logger.info("isUserExists method successfully fired");
        return userDAO.existsById(Id);
    }

    @Override
    public User getUser(int Id) {
        User us = userDAO.findByUserId(Id);
        if(us==null) {
            logger.error("UserNotFoundException thrown");
            throw new UserNotFoundException(String.format("The user with the id: " + Id + " does not exists").trim());
        } else {
            logger.info("getUser method successfully fired");
            return us;
        }
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public boolean delete(int userId) {
        if(!userDAO.existsById(userId)) {
            return false;
        }
        userDAO.deleteById(userId);
        logger.info("delete method successfully fired");
        return true;
    }
}
