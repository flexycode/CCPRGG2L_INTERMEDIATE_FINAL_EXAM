package StringCountAndReverse;

import java.util.Scanner;

public class StringCountAndReverse {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Any String: ");
        String input = scanner.nextLine();

        int numberCount = 0;
        int specialCharCount = 0;

        // Count the numbers and special characters in the input string 
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                numberCount++; // Increment the number count if the character is a digit :)
            } else if (!Character.isLetterOrDigit(c)) {
                specialCharCount++; // Increment the special character count if the character is not a letter or digit :)
            }
        }

        StringBuilder reversedString = new StringBuilder();
        
        // Reverse the input string
        for (int i = input.length() - 1; i >= 0; i--) {
            reversedString.append(input.charAt(i)); // Append each character in reverse order to the StringBuilder :)
        }

        // Print the counts and the reversed string
        System.out.println("Numbers in String: " + numberCount);
        System.out.println("Special Character in String: " + specialCharCount);
        System.out.println("The Reverse string is: " + reversedString);

        scanner.close();
    }
}