package com.revature.weekThree.solidPrinciple;

public class DIP {
    public static void main(String[] args) {
        System.out.println("=== Dependency Inversion Principle (DIP) ===");
    }

    static class NotificationService {
        Sendable sender = new EmailSender();

        /*sendNotification() {
            sender.send();
        }*/
    }

    interface Sendable {
        //send();
    }

    static class EmailSender implements Sendable {
        /*send() {

        }*/
    }

    static class TextSender  implements Sendable {
        /*send() {

        }*/
    }
}
