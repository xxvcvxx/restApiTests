package org.example;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String pesel = "93732854784";
        int correctLength = 11;
        int[] peselTab = new int[correctLength];
        int[] multiply = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3, 0};
        int controlSum = 0;

        if (pesel.length() == 11) {
            int[] multiplyc = new int[correctLength];

            for (int i = 0; i < correctLength; i++) {
                peselTab[i] = Character.getNumericValue((pesel.charAt(i)));
                multiplyc[i] = peselTab[i] * multiply[i];
                if (multiplyc[i] >= 10) {
                    controlSum += multiplyc[i] % 10;
                } else controlSum += multiplyc[i];
            }
            if (controlSum > 10) controlSum = 10 - controlSum % 10;
            System.out.println("Control Number: " + peselTab[correctLength - 1]);
            System.out.println("Calculated control number :" + controlSum);
        } else System.out.println("Invalid PESEL length!");
    }
}
