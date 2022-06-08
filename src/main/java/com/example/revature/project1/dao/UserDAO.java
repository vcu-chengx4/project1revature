package com.example.revature.project1.dao;

import com.example.revature.project1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface UserDAO extends JpaRepository<User,Integer> {
    User findByUserEmailAndUserPassword(String userEmail, String userPassword);
    User findByUserEmail(String userEmail);
    boolean existsByUserEmailAndUserPassword(String userEmail, String userPassword);
    User findByUserId(int userId);
    @Query("SELECT u FROM User u")
    List<User> getAllUsers();
}
