package com.ddxlabs.consola;

import com.ddxlabs.consola.view.ViewContainer;
import com.ddxlabs.consola.view.WordPromptRow;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Processes any typed letter (or space) input that is sent from the CommandInput view.
 */
public class WordPromptHandler {

    private ViewContainer app;

    /**
     *  Keeps track of the previous input to test if the entry has actually changed.
     */
    private String currentInput;

    /**
     *  Current set of words that the entry can resolve to
     */
    private Set<String> availableWords;

    private WordPromptRow wordPromptRow;

    WordPromptHandler(ViewContainer app) {
        this.app = app;
        this.availableWords = Set.of("cathy","catheter","category");
    }

    public void setAvailableWords(Collection<String> words) {
        availableWords.clear();
        availableWords.addAll(words);
    }

    public void processInput(String newInput) {
        WordPromptRow row = app.getWordPromptRow();

        // if the last character ends with a space, reset the WPW
        if (newInput.endsWith(" ")) {
            row.updateWords(Collections.emptyList());
            this.currentInput = "";
        } else if (newInput.length()<=2) {
            row.updateWords(Collections.emptyList());
        }

        // ignore if the input has not changed
        if (newInput.length()>2 && !newInput.equalsIgnoreCase(currentInput)) {
            this.currentInput = newInput;

            String formingWord = currentInput;
            int i = currentInput.lastIndexOf(" ");
            if (i>0) {
                // only interested in the last word fragment of the input
                formingWord = currentInput.substring(i+1);
                System.out.println("chopping input to " + formingWord);
            }

            // only start word prompt if you have at least 2 characters in the word
            if (formingWord.length()>2) {
                row.updateWords(getPromptWordsFromStub(formingWord));
            }

        }
    }

    private Collection<String> getPromptWordsFromStub(String formingWord) {
        // TODO - logic for prompt resolution goes here

        return availableWords.stream().filter(word -> word.startsWith(formingWord)).collect(Collectors.toUnmodifiableList());
    }

}
