import java.io.*;

public class ExceptionTest {
    public static void main(String[] args) {
        try {
            int a[] = new int[2];

            System.out.println("Access Element Three: " + a[3]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Exception Thrown: " + ex);
        }

        System.out.println("Out of the Block");
    }
}