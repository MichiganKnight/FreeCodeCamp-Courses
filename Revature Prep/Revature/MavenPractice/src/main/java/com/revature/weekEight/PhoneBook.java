package com.revature.weekEight;

import java.util.HashMap;
import java.util.Optional;

public class PhoneBook {
    private static final HashMap<String, String> PHONE_NUMBERS = new HashMap<>() {
        {
            put("Ted Striker", "5551212");
            put("Roger Murdock", "3879812");
            put("Elaine Dickinson", "8675309");
            put("Jos de Vos", "016/161616");
        }
    };

    private HashMap<String, String> phoneBookEntries = PHONE_NUMBERS;

    PhoneBook() {}

    public HashMap<String, String> getPhoneBookEntries() {
        return phoneBookEntries;
    }

    public Optional<String> findPhoneNumberByName(String name) {
        return null;
    }

    public Optional<String> findNameByPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public String toString() {
        System.out.println("Phone Book Entries: ");

        return "Phone Book: { " + phoneBookEntries + " }";
    }
}
