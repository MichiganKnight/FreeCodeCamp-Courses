public class Test {
    public static void main(String[] args) {
        char grade = 'C';

        switch (grade) {
            case 'A':
                System.out.println("Excellent!");
                break;
            case 'B':
            case 'C':
                System.out.println("Well Done!");
                break;
            case 'D':
                System.out.println("You Passed");
                break;
            case 'F':
                System.out.println("You Failed");
                break;
            default:
                System.out.println("Invalid Grade");
        }

        System.out.println("Your Grade: " + grade);
    }
}