package com.revature.challenges;

import java.util.HashSet;
import java.util.Set;

public class ContainsDuplicates {
    /**
     * Check if an Array contains Duplicates
     * @param arr Array to Check
     * @return Boolean
     */
    public boolean containsDuplicates(int[] arr) {
        boolean duplicates = false;

        Set<Integer> integers = new HashSet<>();
        for (int i : arr) {
            if (!integers.add(i)) {
                duplicates = true;
            }
        }

        return duplicates;
    }
}
