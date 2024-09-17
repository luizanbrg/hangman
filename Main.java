import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static String getRandomWord(List<String> wordList) {
        Random random = new Random();
        return wordList.get(random.nextInt(wordList.size())).toLowerCase();
    }

    public static void hangman(String word, char[] hiddenWord) {
        int penalty = 0;
        boolean hasWon = false;
        List<String> guessedLetters = new ArrayList<>();
        char[] wordLetterList = word.toCharArray();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome! Each try costs 1 point, regardless if you get it right or not. "
                + "Trying a whole word (or more than one letter) costs you 5 points if you get it wrong. Good luck!");

        while (penalty < 16 && !Arrays.equals(hiddenWord, wordLetterList)) {
            System.out.print("Enter a letter or a word: ");
            String input = scanner.nextLine().toLowerCase();

            if (input.length() == 0) {
                System.out.println("You have to enter a letter.");
                continue;
            } else if (!input.matches("[a-zA-Z]+")) {
                System.out.println("Only alphabetic characters are allowed.");
                continue;
            }

            if (guessedLetters.contains(input)) {
                System.out.println("You've already guessed that letter.");
                continue;
            }

            guessedLetters.add(input);

            if (input.length() > 1) {
                if (input.equals(word)) {
                    hiddenWord = word.toCharArray();
                    System.out.println("You've won!");
                    hasWon = true;
                    break;
                } else {
                    System.out.println("Incorrect guess for the whole word.");
                    penalty += 5;
                    System.out.println("You still have " + (16 - penalty) + " chances.");
                    continue;
                }
            }

            boolean found = false;
            for (int i = 0; i < word.length(); i++) {
                if (word.charAt(i) == input.charAt(0)) {
                    hiddenWord[i] = input.charAt(0);
                    found = true;
                }
            }

            if (found) {
                System.out.println(Arrays.toString(hiddenWord));
            } else {
                System.out.println("Wrong guess.");
                penalty++;
            }

            if (Arrays.equals(hiddenWord, wordLetterList)) {
                System.out.println("Congrats, you've won!");
                hasWon = true;
                break;
            }

            if (!hasWon) {
                System.out.println("You still have " + (16 - penalty) + " chances.");
            }
        }

        if (!hasWon) {
            System.out.println("Noob you lost. The word was '" + word + "'.");
        }
    }

    public static void main(String[] args) {
        List<String> wordList = Arrays.asList("apple", "banana", "snowboard", "student", "birthday", "elephant", "brazil", "france", "java");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String word = getRandomWord(wordList);
            char[] hiddenWord = new char[word.length()];
            Arrays.fill(hiddenWord, '_');

            hangman(word, hiddenWord);

            System.out.print("Do you want to play again? :) (yes/no): ");
            String replay = scanner.nextLine().toLowerCase();
            if (!replay.equals("yes")) {
                System.out.println("Thanks for playing! Goodbye!");
                break;
            }
        }

        scanner.close();
    }
}
