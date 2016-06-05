package com.example.emil.taskmanager.utils;

public class Validator {

    /**
     * Checks if the given string matches the email regular expression.
     * @param input The input to be validated
     * @return True if the input matches the email regular expression.
     */
    public static boolean isEmail(String input) {
        return input.trim().matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
    }

    /**
     * Checks if the given string matches the password regular expression.
     * @param input The input to be validated
     * @return True if the input matches the password regular expression.
     */
    public static boolean isPassword(String input) {
        return input.trim().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }

    /**
     * Checks if the given string is longer than the given amount of characters.
     * @param input The input to be validated.
     * @param chars The number of chars.
     * @return True if the input is above the amount of chars.
     */
    public static boolean isAbove(String input, int chars) {
        return input.trim().length() > chars;
    }

    /**
     * Checks if the given string is shorter than the given amount of characters.
     * @param input The input to be validated.
     * @param chars The number of chars.
     * @return True if the input is below the amount of chars.
     */
    public static boolean isBelow(String input, int chars) {
        return input.trim().length() < chars;
    }

    /**
     * Checks if the given string is present.
     * @param input The input to be validated.
     * @return True if the input is present.
     */
    public static boolean isPresent(String input) {
        return !input.trim().equalsIgnoreCase("");
    }

}
