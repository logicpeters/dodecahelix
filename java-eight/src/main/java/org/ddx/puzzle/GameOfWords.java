package org.ddx.puzzle;

import java.util.*;

/**
 *  Given a list of letters, find all possible words in dictionary from these letters.
 */
public class GameOfWords {

    /**
     * Use a letter map to make sure dictionary word doesn't contain *more* character instances than the input.
     *
     * @param letterMap
     * @param letters
     */
    private static void resetLetterMap(Map<Character, Integer> letterMap, char[] letters) {
        letterMap.clear();
        for (char letter: letters) {
            if (letterMap.containsKey(letter)) {
                letterMap.put(letter,letterMap.get(letter) + 1);
            } else {
                letterMap.put(letter, 1);
            }
        }
    }

    public List<String> findWords(char[] letters, List<String> dictionary) {
        List<String> words = new ArrayList<>();

        // mapping input letters to the number of instances in the array
        Map<Character, Integer> letterMap = new HashMap<>();
        for (String word : dictionary) {
            resetLetterMap(letterMap, letters);

            // don't bother checking words longer than the word length
            if (word.length() <= letters.length) {
                // if any letters found that aren't in the input, then skip this word
                boolean skipWord = false;
                for (char c: word.toCharArray()) {
                    if (!letterMap.containsKey(c)) {
                        // dict word letter is not in list of letters, drop it
                        skipWord = true;
                        break;
                    } else {
                        // decrement the number of available letters
                        int numLetters = letterMap.get(c);
                        if (numLetters == 1) {
                            // if we see this character again, there are too many instances of it - skip
                            letterMap.remove(c);
                        } else {
                            // one less character in the list
                            letterMap.put(c, numLetters - 1);
                        }
                    }
                }

                if (!skipWord) {
                    words.add(word);
                }
            }
        }

        return words;
    }

}
