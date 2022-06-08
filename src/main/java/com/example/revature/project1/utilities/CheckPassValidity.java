package com.example.revature.project1.utilities;

import org.springframework.stereotype.Component;

@Component
public class CheckPassValidity {
    public boolean CheckPassValidity (String userPassword) {
        char[] numbers = {'0','1','2','3','4','5','6','7','8','9'};
        for(int i =0; i<userPassword.length();i++) {
            char passChar = userPassword.charAt(i);
            for(int j =0; j<numbers.length; j++) {
                if(numbers[j] == passChar && userPassword.length()>5) {
                    return true;
                }
            }
        }
        return false;
    }
}
