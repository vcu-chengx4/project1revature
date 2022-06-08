package com.example.revature.project1.utilities;

import org.springframework.stereotype.Component;

@Component
public class CheckEmailValidity {
    public boolean checkEmailValidity(String userEmail) {
        if(userEmail.contains("@") && (userEmail.endsWith(".com") || userEmail.endsWith(".net"))) {
            return true;
        } else {
            return false;
        }
    }
}
