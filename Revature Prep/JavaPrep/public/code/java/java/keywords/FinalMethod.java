public class Bike {
    final void run() {
        System.out.println("Running...");
    }
}

public class Honda extends Bike {
    void run() {
        System.out.println("Running... at 100 MPH"); // Compile Time Error
    }

    public static void main(String[] args) {
        Honda honda = new Honda();
        honda.run();
    }
}