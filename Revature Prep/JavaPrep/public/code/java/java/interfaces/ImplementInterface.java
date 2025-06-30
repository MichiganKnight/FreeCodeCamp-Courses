public class MammalInt implements Animal {
    public void eat() {
        System.out.println("Mammals Eats");
    }

    public void travel() {
        System.out.println("Mammals Travels");
    }

    public int noOfLegs() {
        return 0;
    }

    public static void main(String[] args) {
        MammalInt m = new MammalInt();
        m.eat();
        m.travel();
    }
}