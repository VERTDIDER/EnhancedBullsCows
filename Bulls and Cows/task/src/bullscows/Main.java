package bullscows;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please, enter the secret code's length:");

        String codeString = scanner.nextLine();
        if (codeString.matches("\\d\\d?")) {
            int codeLength = Integer.parseInt(codeString);
            if (codeLength > 0 && codeLength <= 36) {

                System.out.println("Input the number of possible symbols in the code:");
                int symbols = scanner.nextInt();

                if (symbols >= codeLength && symbols <= 36) {
                    RandomString rs = new RandomString(codeLength, symbols);
                    String random = rs.nextString();
                    String range;
                    if (symbols <= 10)  range = String.format("(0-%d)", symbols - 1);
                    else range = String.format("(0-9, a-%s)", RandomString.alphanum.charAt(symbols - 1));

                    System.out.printf("The secret is prepared: %s %s.\n",
                            random.replaceAll("\\w", "*"),
                            range );
                    System.out.println("Okay, let's start a game!");
                    int turn = 1;

                    while (true) {
                        System.out.println("Turn " + turn);
                        turn++;
                        String guess = scanner.next();
                        int[] bullsCows = countBullsCows(guess, random);
                        outputBullsCows(bullsCows);
                        if (bullsCows[0] == codeLength) {
                            System.out.println("Congratulations! You guessed the secret code.");
                            break;
                        }
                    }
                } else if (symbols < codeLength) {
                    System.out.printf("Error: it's not possible to generate a code with a length of %d with %d unique symbols.", codeLength, symbols);
                } else {
                    System.out.print("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                }
            } else {
                System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", codeLength);
            }
        } else {
            System.out.printf("Error: \"%s\" isn't a valid number.", codeString);
        }
    }
    public static int[] countBullsCows(String guess, String code) {
        int[] bullsCows = new int[2];
        Arrays.fill(bullsCows, 0);
        for (int i = 0; i < code.toCharArray().length; i++) {
            if (code.charAt(i) == guess.charAt(i)) {
                bullsCows[0]++;
                continue;
            }
            for (char c : code.toCharArray()) {
                if (guess.charAt(i) == c) {
                    bullsCows[1]++;
                    break;
                }
            }
        }
        return bullsCows;
    }
    public static void outputBullsCows(int[] bullsCows) {
        System.out.println("Grade : " + (bullsCows[0] <= 1 ? bullsCows[0] + " bull " : bullsCows[0] + " bulls ") +
               "and " + (bullsCows[1] <= 1 ? bullsCows[1] + " cow " : bullsCows[1] + " cows "));
    }
}