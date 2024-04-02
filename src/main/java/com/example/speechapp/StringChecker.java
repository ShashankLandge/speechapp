package com.example.speechapp;

public class StringChecker {
    public static float accuracyScore(String correctStr, String checkStr) {
        // Split the correct string into words
        String[] correctWords = correctStr.split("\\s+");

        // Count the number of words found in the check string
        int foundWords = 0;
        for (String word : correctWords) {
            if (checkStr.contains(word)) {
                foundWords++;
            }
        }

        // Calculate accuracy score
        float accuracy = (float) foundWords / correctWords.length;

        return accuracy;
    }
}
