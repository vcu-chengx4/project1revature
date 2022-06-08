package com.example.revature.project1.utilities;

import org.springframework.stereotype.Component;

@Component
public class CheckNegativeValues {
    public boolean checkNegativeValue(int value) {
        if (value<=0) {
            return true;
        } else {
            return false;
        }
    }
    public boolean checkNegativeValue(double value) {
        if (value<=0) {
            return true;
        } else {
            return false;
        }
    }
}
