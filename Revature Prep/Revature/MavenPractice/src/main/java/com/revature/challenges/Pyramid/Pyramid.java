package com.revature.challenges.Pyramid;

public class Pyramid {
    public String returnPyramid(int n) {
        String pyramid = "";

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                pyramid += "*";
            }

            if (i < n) {
                pyramid += "\n";
            }
        }

        return pyramid;
    }
}
