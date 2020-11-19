package org.ddx.puzzle;

import org.ddx.util.FileReaderUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class GameOfWordsTest {

    @Test
    public void testGameOfWords() throws IOException {
        char[] letters = { 'a','a','s','s','h','r','m'};
        List<String> dictionary = FileReaderUtil.readFileAsStringList("datasets/corncob_lowercase.txt");
        GameOfWords gow = new GameOfWords();
        List<String> words = gow.findWords(letters, dictionary);
        assert words.contains("mash");
        assert words.contains("harass");
        assert !words.contains("hash"); // only 1 h
        assert !words.contains("sharm"); // not in dictionary
    }

}
