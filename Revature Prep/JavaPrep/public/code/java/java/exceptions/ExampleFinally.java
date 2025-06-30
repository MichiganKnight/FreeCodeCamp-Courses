public class ExceptionTest {
    public static void main(String[] args) {
        int a[] = new int[2];

        try {
            System.out.println("Access Element Three: " + a[3]);
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Exception Thrown: " + ex);
        } finally {
            a[0] = 6;

            System.out.println("First Element Value: " + a[0]);
            System.out.println("Finally Executed");
        }
    }
}