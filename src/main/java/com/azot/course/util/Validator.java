package com.azot.course.util;

public class Validator {

    public static boolean isValidUsername(String username) {
        return username != null && !username.trim().isEmpty() && !username.contains(" ");
    }

    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$");
    }

    public static boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(passwordPattern);
    }
}