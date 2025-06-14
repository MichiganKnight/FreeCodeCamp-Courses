import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Random random = new Random();
    private static int randomNumber;

    public static void main(String[] args) {
        System.out.println("=== Number Guessing Game ===");
        do {
            playGame();
        } while (playAgain());
    }

    private static void generateRandomNumber() {
        randomNumber = random.nextInt(101);
    }

    private static void playGame() {
        Scanner scanner = new Scanner(System.in);
        int guesses = 0;
        generateRandomNumber();

        while (true) {
            try {
                System.out.print("Enter a Number Between 0 and 100: ");
                String guessedString = scanner.nextLine();

                int guessedNumber = Integer.parseInt(guessedString);

                if (guessedNumber == randomNumber) {
                    guesses++;
                    System.out.println("You Won!\nThe Number Was: " + randomNumber + "\nGuesses: " + guesses);
                    break;
                } else if (guessedNumber < randomNumber) {
                    guesses++;
                    System.out.println("Too Low!");
                } else {
                    guesses++;
                    System.out.println("Too High!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input!");
            }
        }
    }

    private static boolean playAgain() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Play Again? (Y/N): ");
        String response = scanner.nextLine().trim().toLowerCase();

        return response.equals("y") || response.equals("yes");
    }
}
