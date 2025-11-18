package com.revature.weekThree;

public class SRP {
    public static void main(String[] args) {
        System.out.print("=== Single Responsibility Principle (SRP) ===");
    }

    static class UserAuthenticator {
        public boolean authenticateUser(String username, String password) {
            return false;
        }
    }

    static class UserDAO {
        public void createUser(String username, String password) {

        }
    }
}
