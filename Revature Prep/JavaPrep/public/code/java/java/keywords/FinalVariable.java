public class Bike {
    final int speedLimit = 90;

    void run() {
        speedLimit = 400; // Compile Time Error
    }

    Bike obj = new Bike();
    obj.run();
}