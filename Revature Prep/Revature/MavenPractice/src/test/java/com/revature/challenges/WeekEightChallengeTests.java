package com.revature.challenges;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class WeekEightChallengeTests {
    WeekEightChallenges challenges = new WeekEightChallenges();

    @Test
    public void charCompareTest1() {
        char[] input1 = {'c', 'a', 't'};
        char[] input2 = {'d', 'o', 'g'};
        int expected = -1;
        int actual = challenges.compare(input1, input2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void charCompareTest2() {
        char[] input1 = {'m', 'o', 'u', 's', 'e'};
        char[] input2 = {'d', 'o', 'g'};
        int expected = 1;
        int actual = challenges.compare(input1, input2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void charCompareTest3() {
        char[] input1 = {'m', 'o', 'u', 's', 'e'};
        char[] input2 = {'m', 'o', 'u', 's', 'e'};
        int expected = 0;
        int actual = challenges.compare(input1, input2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void charCompareTest4() {
        char[] input1 = {'d', 'o', 't'};
        char[] input2 = {'d', 'o', 'g'};
        int expected = 1;
        int actual = challenges.compare(input1, input2);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void abvTest1() {
        int[] nums = {1, -3, 5, -6};
        int[] target = {1, 3, 5, 6};
        Assertions.assertArrayEquals(target, challenges.getArrayAbs(nums));
    }

    @Test
    public void fibTest1() {
        int input = 1;
        int expected = 1;
        Assertions.assertEquals(expected, challenges.fib(input));
    }

    @Test
    public void fibTest2() {
        int input = 3;
        int expected = 2;
        Assertions.assertEquals(expected, challenges.fib(input));
    }

    @Test
    public void fibTest3() {
        int input = 6;
        int expected = 8;
        Assertions.assertEquals(expected, challenges.fib(input));
    }

    @Test
    public void sumTest1() {
        List<Integer> nums = new ArrayList<>();
        nums.add(0);
        nums.add(1);
        nums.add(2);
        nums.add(3);
        nums.add(4);
        int target = 7;
        Assertions.assertEquals(target, challenges.bigSum(nums));
    }

    @Test
    public void sumTest2() {
        List<Integer> nums = new ArrayList<>();
        nums.add(0);
        nums.add(0);
        nums.add(0);
        nums.add(0);
        nums.add(0);
        int target = 0;
        Assertions.assertEquals(target, challenges.bigSum(nums));
    }

    @Test
    public void sumTest3() {
        List<Integer> nums = new ArrayList<>();
        nums.add(0);
        nums.add(4);
        nums.add(2);
        nums.add(5);
        nums.add(0);
        int target = 9;
        Assertions.assertEquals(target, challenges.bigSum(nums));
    }

    @Test
    public void sumTest4() {
        List<Integer> nums = new ArrayList<>();
        nums.add(5);
        nums.add(4);
        nums.add(2);
        nums.add(5);
        nums.add(0);
        int target = 10;
        Assertions.assertEquals(target, challenges.bigSum(nums));
    }

    @Test
    public void isoTest1() {
        String input = "cat";
        boolean expected = true;
        boolean actual = challenges.isIsogram(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void isoTest2() {
        String input = "isogram";
        boolean expected = true;
        boolean actual = challenges.isIsogram(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void isoTes3() {
        String input = "uncopyrightable";
        boolean expected = true;
        boolean actual = challenges.isIsogram(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void isoTest4() {
        String input = "egg";
        boolean expected = false;
        boolean actual = challenges.isIsogram(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void isoTest5() {
        String input = "java";
        boolean expected = false;
        boolean actual = challenges.isIsogram(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void commonChars1() {
        String str = "5003196532" + "8932857241" + "1886684186" + "2095882212" + "7736052319" + "0513214100" + "9837113941" + "5535441504" + "6350518702" + "5831217238";

        Assertions.assertEquals('1', challenges.recurringChar(str));
    }

    @Test
    public void commonChars2() {
        String str = "3585922463" + "4564881449" + "9744344742" + "8950706750" + "4316600461" + "9728760400" + "7599995873" + "0552858863" + "6107666584" + "8137602552";

        Assertions.assertEquals('4', challenges.recurringChar(str));
    }

    @Test
    public void commonChars3() {
        String str = "4846380793" + "4193747220n" + "9075367490" + "1180082954" + "2190728751" + "7748973893" + "1981988622n" + "5637648056" + "8515013697" + "4592237105";

        Assertions.assertEquals('7', challenges.recurringChar(str));
    }

    @Test
    public void commonChars4() {
        String str = "2276666678274" + "46085766663499" + "60053968666671" + "08912666667263" + "9188951666383" + "0463711695" + "8940121428" + "6030148532" + "1579113887" + "7221199733";

        Assertions.assertEquals('6', challenges.recurringChar(str));
    }

    @Test
    public void commonChars5() {
        String str = "5555003196532" + "8932855557241" + "18866841555586" + "20955588552212" + "7736052555319" + "0513214105550" + "983711355941" + "5535441504" + "6350518702" + "5831217238";

        Assertions.assertEquals('5', challenges.recurringChar(str));
    }

    @Test
    public void commonChars6() {
        String str = "nnpppppplldfdfmnnnnnnnnn";

        Assertions.assertEquals('n', challenges.recurringChar(str));
    }
}
