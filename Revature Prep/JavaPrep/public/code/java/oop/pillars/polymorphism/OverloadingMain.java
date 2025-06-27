public class OverloadingMain {
    public static void main(String[] args) {
        System.out.println("Main With String[]");
    }

    public static void main(String args) {
        System.out.println("Main With String");
    }

    public static void main() {
        System.out.println("Main Without Args");
    }
}