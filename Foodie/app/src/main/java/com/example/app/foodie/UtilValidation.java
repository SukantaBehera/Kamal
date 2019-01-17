package com.example.app.foodie;

public class UtilValidation {
    public static boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }
}
