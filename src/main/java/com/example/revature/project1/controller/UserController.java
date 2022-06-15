package com.example.revature.project1.controller;

import com.example.revature.project1.dao.CartDAO;
import com.example.revature.project1.dao.ItemDAO;
import com.example.revature.project1.dao.OrderDAO;
import com.example.revature.project1.dao.UserDAO;
import com.example.revature.project1.exceptions.NotLoggedInException;
import com.example.revature.project1.exceptions.UserNotFoundException;
import com.example.revature.project1.model.*;
import com.example.revature.project1.services.CartServices;
import com.example.revature.project1.services.ItemServices;
import com.example.revature.project1.services.UserServices;
import io.micrometer.core.annotation.Timed;
import jdk.nashorn.internal.ir.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    User user;
    @Autowired
    Item item;
    @Autowired
    Cart cart;
    @Autowired
    Order order;
    @Autowired
    CartIdentification cartPK;
    @Autowired
    UserDAO userDAO;
    @Autowired
    ItemDAO itemDAO;
    @Autowired
    CartDAO cartDAO;
    @Autowired
    OrderDAO orderDAO;
    @Autowired
    UserServices userServices;
    @Autowired
    ItemServices itemServices;
    @Autowired
    CartServices cartServices;
    @Autowired
    HttpServletRequest request;
    boolean result;
    ResponseEntity responseEntity = null;

    @GetMapping("/home") //localhost:8085/user/home
    public String home(){
        return "Welcome wonderful user!";
    }

    @GetMapping //localhost:8085/user
    @Timed(value = "show_users.time", description = "Time it takes to load all users")
    public ResponseEntity<List<User>> getUsers() {
        ResponseEntity responseEntity = null;
        List<User> users = new ArrayList<User>();
        users = userServices.getUsers();
        logger.info("displaying all user details");
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }
    @GetMapping("/{uId}")
    public ResponseEntity<User> getUserOfId(@PathVariable("uId")int userId) {
        ResponseEntity responseEntity = null;
        User UserofId = userServices.getUser(userId);
        logger.info("displaying user id: "+userServices.getUser(userId).getUserId()+" details");
        return new ResponseEntity<User>(UserofId, HttpStatus.OK);
    }
    @PostMapping("/register") //localhost:8085/user/register
    @Timed(value = "show_register.time", description = "Time it takes to register a user")
    public ResponseEntity<String> register(@RequestBody User user){
        ResponseEntity responseEntity = null;
        if(userServices.isUserExists(user.getUserId())) {
            logger.warn("User: "+ user.getUserId()+ " already exists");
            responseEntity = new ResponseEntity<String>("Cannot register because this user with the id: "+user.getUserId()+" already exists", HttpStatus.CONFLICT); //202
        } else {
            result = userServices.register(user);
            if(result) {
                logger.info("registered new user: "+user.getUserId());
                responseEntity = new ResponseEntity<String>( "Successfully registered: " + user.getUserId(),HttpStatus.OK);
            } else {
                logger.error("invalid email/password was entered during registration");
                responseEntity = new ResponseEntity<String>( "Cannot register, "  + " because either an invalid email or password was entered.\n" +
                        "\"⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⠋⠉⣉⣉⠙⠿⠋⣠⢴⣊⣙⢿⣿⣿\n" +
                        "\"⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠟⠋⠁⠀⢀⠔⡩⠔⠒⠛⠧⣾⠊⢁⣀⣀⣀⡙⣿\n" +
                        "\"⣿⣿⣿⣿⣿⣿⣿⠟⠛⠁⠀⠀⠀⠀⠀⡡⠊⠀⠀⣀⣠⣤⣌⣾⣿⠏⠀⡈⢿⡜\n" +
                        "\"⣿⣿⣿⠿⠛⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠡⣤⣶⠏⢁⠈⢻⡏⠙⠛⠀⣀⣁⣤⢢\n" +
                        "\"⣿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰⣄⡀⠣⣌⡙⠀⣘⡁⠜⠈⠑⢮⡭⠴⠚⠉⠀\n" +
                        "\"⠁⠀⢀⠔⠁⣀⣤⣤⣤⣤⣤⣄⣀⠀⠉⠉⠉⠉⠉⠁⠀⠀⠀⠀⠀⠁⠀⢀⣠⢠\n" +
                        "\"⡀⠀⢸⠀⢼⣿⣿⣶⣭⣭⣭⣟⣛⣛⡿⠷⠶⠶⢶⣶⣤⣤⣤⣶⣶⣾⡿⠿⣫⣾\n" +
                        "\"⠇⠀⠀⠀⠈⠉⠉⠉⠉⠉⠙⠛⠛⠻⠿⠿⠿⠷⣶⣶⣶⣶⣶⣶⣶⣶⡾⢗⣿⣿\n" +
                        "\"⣦⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣿⣶⣾⣿⣿⣿\n" +
                        "\"⣿⣿⣿⣷⣶⣤⣄⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⢀⣀⣤⣝⡻⣿⣿⣿⣿⣿⣿⣿⣿\n" +
                        "\"⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣦⡹⣿⣿⣿⣿⣿⣿\n",HttpStatus.CONFLICT);
            }
        }
        return responseEntity;
    }
    @GetMapping("/login/{email}/{password}") //localhost:8085/user/login
    public ResponseEntity<String> login(@PathVariable("email")String email,@PathVariable("password")String password) {
        ResponseEntity responseEntity = null;
        if (userDAO.existsByUserEmailAndUserPassword(email, password) && User.loggedAs!=userServices.login(email,password).getUserId()){
            userServices.login(email,password);
            User.setLoggedAs(userServices.login(email,password).getUserId());
            logger.info(userServices.login(email,password).getUserId()+" successfully logged in");
            responseEntity = new ResponseEntity<String>("Your login was successful "+userServices.login(email,password).getUserId(),HttpStatus.OK);
        } else if (userDAO.existsByUserEmailAndUserPassword(email, password) && User.loggedAs==userServices.login(email,password).getUserId()) {
            logger.warn("User already logged in");
            responseEntity = new ResponseEntity<String>("Login was unsuccessful, you're already logged in!",HttpStatus.CONFLICT);
        } else {
            logger.error("invalid email or password was entered during login");
            responseEntity = new ResponseEntity<String>("Login was unsuccessful, either entered email or password is invalid",HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseEntity responseEntity = null;
        if(User.loggedAs!=0) {
            logger.info(User.loggedAs+" logged out");
            responseEntity = new ResponseEntity<String>("Logging you out user Id: "+User.loggedAs,HttpStatus.OK);
            userServices.logout();
            User.loggedAs=0;
        } else {
            logger.warn("user logging out despite already being logged out");
            responseEntity = new ResponseEntity<String>("You're already logged out!",HttpStatus.CONFLICT);
        }
        return responseEntity;
    }
    @PostMapping("/addToCart/")
    @Timed(value = "adding_to_cart.time", description = "Time it takes to load an item to a cart")
    public ResponseEntity<String> addtocart(@RequestBody Cart cart) {
        ResponseEntity responseEntity = null;
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("currentUser")==null) {
            logger.error("NotLoggedInException thrown");
            throw new NotLoggedInException("You must be logged in to do that!");
        } else {
            cartServices.addItemToCart(cart);
            itemServices.getItem(cart.getCartId().getItem_id()).setQoh(itemServices.getItem(cart.getCartId().getItem_id()).getQoh() - cart.getCartQoh());
            itemDAO.save(itemServices.getItem(cart.getCartId().getItem_id()));
            logger.info("items successfully added to user: "+ userServices.getUser(User.loggedAs).getUserEmail()+"'s cart");
            responseEntity = new ResponseEntity<String>("Congratulations user " + userServices.getUser(User.loggedAs).getUserEmail() + " your items are successfully stored in your cart",HttpStatus.OK);
            return responseEntity;
        }
    }
    @GetMapping("/checkout")
    public ResponseEntity<String> ordered() {
        ResponseEntity responseEntity = null;
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("currentUser")==null) {
            logger.error("NotLoggedInException thrown");
            throw new NotLoggedInException("You must be logged in to do that!");
        } else {
            order.setCartId(cartDAO.findByCartIdUserId(User.loggedAs).get(0).getCartId().getCartId());
            order.setUserId(User.loggedAs);
            order.setCountOfUniqueItems(0);
            order.setPrice(0);
            for (Cart c : cartDAO.findByCartIdUserId(User.loggedAs)) {
                if (c.getCartId().getItem_id() != 0) {
                    order.setCountOfUniqueItems(order.getCountOfUniqueItems() + 1);
                    order.setPrice(order.getPrice() + itemServices.getItem(c.getCartId().getItem_id()).getPrice());
                }
                order.getItemList().add((itemServices.getItem(c.getCartId().getItem_id())));
            }
            orderDAO.save(order);
            cartDAO.deleteAll(cartDAO.findByCartIdUserId(User.loggedAs));
            logger.info(userServices.getUser(User.loggedAs).getUserEmail()+" successfully ordered");
            responseEntity = new ResponseEntity<String>("Congratulations user " + userServices.getUser(User.loggedAs).getUserEmail() + " your items have been successfully ordered",HttpStatus.OK);
            return responseEntity;
        }
    }

    @GetMapping("/cart")
    @Timed(value = "show_cart.time", description = "Time it takes to load a user's cart")
    public ResponseEntity<List<Item>> carted() {
        ResponseEntity responseEntity = null;
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("currentUser")==null) {
            logger.error("NotLoggedInException thrown");
            throw new NotLoggedInException("You must be logged in do that!");
        } else {
            List<Item> checkoutArray = new ArrayList<>();
            for (Cart c : cartDAO.findByCartIdUserId(User.loggedAs)) {
                itemServices.getItem(c.getCartId().getItem_id()).setQoh(c.getCartQoh());
                itemServices.getItem(c.getCartId().getItem_id()).setPrice(c.getCartQoh() * itemServices.getItem(c.getCartId().getItem_id()).getPrice());
                checkoutArray.add(itemServices.getItem(c.getCartId().getItem_id()));
            }
            logger.info("Displaying "+User.loggedAs+" cart info");
            return new ResponseEntity<List<Item>>(checkoutArray, HttpStatus.OK);
        }
    }
    @DeleteMapping("{uId}")
    public ResponseEntity<String> delete(@PathVariable("uId") int userId) {
        if(userServices.delete(userId)) {
            logger.error("UserNotFoundException thrown");
            throw new UserNotFoundException("No user with the id of "+userId+" was found.");
        } else {
            logger.info("successfully deleted user: "+userId);
            responseEntity = new ResponseEntity<String>("deleting user with the Id: "+userId,HttpStatus.OK);
        } return responseEntity;
    }
    @GetMapping("/allCart")
    public ResponseEntity<List<Cart>> allCarted() {
        ResponseEntity responseEntity = null;
        List<Cart> carts = new ArrayList<Cart>();
        carts = cartDAO.findAll();
        logger.info("displaying all carts");
        return new ResponseEntity<List<Cart>>(carts, HttpStatus.OK);
    }
}
