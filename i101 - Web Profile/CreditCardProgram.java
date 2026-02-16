/*
Phil Albonetti - Intro to Software Systems, T520
Purpose: This program determines if a credit card is a valid number or not. First it takes in the user's card number
and conducts input validation checks:
- is the number is the correct length (13-16 digits).
- is the input actually a number and not other character types (letters, punctuation, etc.).
Next the program conducts a Luhn check on the credit card number.
Step 1: Double every second digit starting at the left of the number. If the doubling creates a two digit number
then add those two digits to create a new single digit number.
Step 2: Add all the single digit numbers from Step 1.
Step 3: Add all the digits from the odd places of the number together.
Step 4: Add the even digit total from Step 2 and the odd digit total from Step 3.
Step 5: Calculate if the number from Step 4 is divisible by 10 (number % 10 == 0). If yes, then the credit card number
 is valid. If not, then it is an invalid number.
2025/09/16
 */

import java.util.Scanner;       //import so user can enter information

public class CreditCardProgram {

    // METHODS USED IN MAIN METHOD

    /*
    Method determines if the user input is a number or not.
    User input is taken in as a String. This method uses a for loop to scan all the indexes to confirm each one
    is a number. Returns false if any character is not a number. Returns true otherwise.
    While researching, I found another method to make this check (regex "\\d+") but didn't use it because I think
    the assignment is calling for us to use methods we read about in the textbook. This method took a loooong time to make :(
    */
    public static boolean isNumber(String cardString) {   // returns a boolean, takes a String as a parameter
        //for loop to iterate through the String
        for (int i = 0; i < cardString.length(); i++) {
            char charCheck = cardString.charAt(i); //create a character for each index of in the String
            if (!Character.isDigit(charCheck))  //use the isDigit() method in Character to check each character
                return false;                   // return false if any character is not a number
        }
        return true;                            //return true if all characters are numbers
    }

    /*
    Method determines if the credit card number is the correct length, between 13 and 16 numbers.
    It uses a simple if else statement to return either true or false regarding the length.
     */
    public static boolean cardNumLength(String cardNumAsString) {   //return a boolean, take a String as a parameter
        int len = cardNumAsString.length();             //get the length of the string
        if (len >= 13 && len <= 16) {                   //if else statement to confirm length of credit card number
            return true;
        } else {
            return false;
        }
    }

    /*
    This method adds the digits in the even places (2nd, 4th, 6th...) from right to left. It takes the credit
    card number as a string for its parameter. And it returns the integer totalSum of all the numbers added.
     */
    public static int doubleEveryEven(String cardNumAsString) {
        int totalSum = 0;       //create int that will add the numbers and be returned
        int doubleNum = 0;      //int needed for doubling numbers

        //iterates through the string of credit card #s
        //starts at the 2nd-to-last index in the String (cardNumAsString.length() - 2) <-- The -2 is necessary here.
        //Does i = i - 2 so that it skips the odd spaced numbers and only looks at even numbers
        for (int i = cardNumAsString.length() - 2; i >= 0; i = i - 2) {
            char ch = cardNumAsString.charAt(i);        //turn each number into its own character

            int chToNum = Integer.parseInt(String.valueOf(ch)); //turn each character to single digit number
            doubleNum = chToNum * 2;                            //double the value of the single digit numbers
            if (doubleNum >= 10) {             //if doubled value is >= 10, run splitAndAdd() method
                totalSum = totalSum + splitAndAdd(doubleNum); //call splitAndAdd() to add each digit of doubleNum
            } else {
                totalSum = totalSum + doubleNum; //else add doubleNum to the totalSum
            }
        }
        return totalSum;        //return total of numbers added together
    }

    /*
    This method adds the digits in the odds places (1st, 3rd, 5th...) from right to left. It takes the credit
    card number as a string for its parameter. And it returns the integer totalSum of all the numbers added.
     */
    public static int addEveryOdd(String cardNumAsString) {
        int totalSum = 0;           //for adding integers and returning the total

        //iterates through the string of credit card #
        //starts at the last index in the String
        //Does i = i - 2 so that it skips the even spaced numbers and only looks at odd numbers
        for (int i = cardNumAsString.length() - 1; i >= 0; i = i - 2) {
            char ch = cardNumAsString.charAt(i);            //turn each number into its own character
            int stringToNum = Integer.parseInt(String.valueOf(ch)); //turn each character to single digit number
            totalSum = totalSum + stringToNum;              //add the numbers together
        }
        return totalSum;  //return total sum of numbers added
    }

    /*
    This method splits the digits of the sum of Step 4 and then adds those digits together.
    I originally wrote the code assuming that the number passed in as the parameter would be two digits. I changed
    from that code into a for loop so that the parameter could be a number of any length, and I could still split it
    and add its digits together to return the total.
    */
    public static int splitAndAdd(int num) {
        String numAsString = String.valueOf(num);       //turn the number into a String
        int total = 0;                                  //integer that will return the total sum

        for (int i = 0; i < numAsString.length(); i++) {    //for loop to iterate through the digits of the parameter
            char ch = numAsString.charAt(i);    //split the string into individual characters as the for loop iterates
            int chToNum = Integer.parseInt(String.valueOf(ch)); //use parseInt(String) to turn character into number
            total = total + chToNum; //add the numbers                    //valueOf(char) converts ch into String
        }
        return total;               //return total value of digits added together
    }

    //MAIN METHOD TO RUN PROGRAM
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);     //needed for user to input card #
        String cardNumAsString = "";                //value of card number stored here

        //variables needed to add digits of card #
        int everyEvenDoubled = 0;
        int everyOddAdded = 0;

        //Instructions for user to input credit card number.
        System.out.print("Please enter your credit card number as a long integer: ");
        cardNumAsString = input.nextLine();
        System.out.println("You entered " + cardNumAsString);

        //STEP 0: input validation
        //If isNumber() is false, tell user card number is invalid and exit the program.
        //I added this method because cardNumLength() wasn't enough. I was able to input 13-16 letters (not numbers) and the program
        //would crash with an error. I didn't want the program to crash so I added this check.
        if (!isNumber(cardNumAsString)) {
            System.out.println("Invalid card number");
            System.exit(0);
        }
        //If cardNumLength is false (less than 13 or greater than 16), then tell user card number is invalid and exit the program.
        if (!cardNumLength(cardNumAsString)) {
            System.out.println("Invalid card number");
            System.exit(0);
        } else {
            System.out.println("Card number is correct length.");
        }

        //STEP 1: Double every second digit from right to left.
        //If doubling of a digit results in a two-digit number, add up the two digits to get a single-digit number.
        //STEP 2: Add all single-digit numbers from Step 1
        everyEvenDoubled = doubleEveryEven(cardNumAsString);  //method here performs Steps 1 & 2
        System.out.println("Double every even = " + everyEvenDoubled);

        //STEP 3: Add all digits in the odd places from right to left in the card number.
        everyOddAdded = addEveryOdd(cardNumAsString);  //method performs Step 3
        System.out.println("Add every odd = " + everyOddAdded);

        //STEP 4: Sum the results from Step 2 and Step 3.
        System.out.println("The two numbers combined = " + (everyEvenDoubled + everyOddAdded));
        //STEP 5: If the result from Step 4 is divisible by 10, the card number is valid; otherwise, it is invalid.
        if ((everyEvenDoubled + everyOddAdded) % 10 == 0) {
            System.out.println((everyEvenDoubled + everyOddAdded) + " is divisible by 10.");
            System.out.println("Card number is valid.");
        } else {
            System.out.println((everyEvenDoubled + everyOddAdded) + " is not divisible by 10.");
            System.out.println("Invalid card number.");
        }

        }
    }




