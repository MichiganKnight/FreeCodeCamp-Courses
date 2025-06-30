public final class Bike {
}

class Honda extends Bike { // Compile Time Error
    void run() {
        System.out.println("Running...");
    }

    public static void main(String[] args) {
        Honda honda = new Honda();
        honda.run();
    }
}