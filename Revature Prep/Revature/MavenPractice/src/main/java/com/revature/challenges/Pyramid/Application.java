package com.revature.challenges.Pyramid;

public class Application {
    public static void main(String[] args) {
        Pyramid pyramid = new Pyramid();

        System.out.println("Output for Size 3:");
        System.out.println(pyramid.returnPyramid(3));
        System.out.println("Output for Size 5:");
        System.out.println(pyramid.returnPyramid(5));
    }
}
