package com.ddxlabs.consola;

import com.ddxlabs.consola.view.WordPromptRow;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *  Processes any typed letter (or space) input that is sent from the CommandInput view.
 */
public class CommandKeyEntryHandler {

    private Application app;

    private String currentInput;

    private Set<String> availableWords;

    public CommandKeyEntryHandler(Application app) {
        this.app = app;

        this.availableWords = Set.of("cathy","catheter","category");
    }

    public void setAvailableWords(Collection<String> words) {
        availableWords.clear();
        availableWords.addAll(words);
    }

    public void processInput(String newInput) {
        // ignore if the input has not changed
        if (newInput.length()>2 && !newInput.equalsIgnoreCase(currentInput)) {
            this.currentInput = newInput;

            WordPromptRow row = app.getWordPromptRow();

            // if the last character ends with a space, reset the WPW
            if (currentInput.endsWith(" ")) {
                row.updateWords(Collections.emptyList());
            }

            String formingWord = currentInput;
            int i = currentInput.lastIndexOf(" ");
            if (i>0) {
                formingWord = currentInput.substring(i);
            }

            // only start word prompt if you have at least 2 characters in the word
            if (formingWord.length()>2) {
                row.updateWords(getPromptWordsFromStub(formingWord));
            }

        }
    }

    private Collection<String> getPromptWordsFromStub(String formingWord) {
        return availableWords.stream().filter(word -> word.startsWith(formingWord)).collect(Collectors.toUnmodifiableList());
    }

}
