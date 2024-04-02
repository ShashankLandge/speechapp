package com.example.speechapp;

import java.util.Random;

public class PhraseGenerator {
    private static String[] phrases = {
            "The quick brown fox jumps over the lazy dog, demonstrating agility and speed, while the dog lazily watches, unfazed by the fox's antics.",
            "She sells seashells by the seashore, her voice soft as waves, as she showcases her treasures amidst the sandy expanse.",
            "Peter Piper picked a peck of pickled peppers, carefully selecting each one, his fingers deftly maneuvering among the vines and leaves.",
            "Unique New York, a city of diversity and energy, where towering skyscrapers dominate the skyline and bustling streets buzz with activity.",
            "Six slippery snails slid slowly seaward, leaving shimmering trails on the moist sand, as they ventured towards the tranquil waters of the ocean.",
            "How much wood would a woodchuck chuck if a woodchuck could chuck wood, pondering the age-old question of chucking wood with whimsical curiosity.",
            "Red leather, yellow leather, a tongue-twister challenging the mouth's agility, as lips and tongue dance through the repetitive sounds with playful effort.",
            "Sally sells sea shells by the sea shore, her melodic voice echoing the rhythm of the waves, as she proudly displays her seaside treasures.",
            "Betty Botter bought some butter but she said the butter’s bitter, wrestling with words, striving for clarity despite the challenging consonants.",
            "Fuzzy Wuzzy was a bear. Fuzzy Wuzzy had no hair. Fuzzy Wuzzy wasn’t very fuzzy, was he? A whimsical rhyme reflecting on a bear's appearance.",
    };

    private static Random random = new Random();

    public static String getRandomPhrase() {
        if (phrases.length == 0) {
            throw new IllegalStateException("Array of phrases is empty");
        }
        int randomIndex = random.nextInt(phrases.length);
        return phrases[randomIndex];
    }

    public static void main(String[] args) {
        System.out.println("Random Phrase: " + getRandomPhrase());
    }
}

