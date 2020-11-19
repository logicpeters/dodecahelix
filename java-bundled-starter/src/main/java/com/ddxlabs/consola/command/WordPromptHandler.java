package com.ddxlabs.consola.command;

import com.ddxlabs.consola.prefs.Constants;
import com.ddxlabs.consola.view.ViewContainer;
import com.ddxlabs.consola.view.WordPromptRow;

import java.util.*;
import java.util.stream.Collectors;

/**
 *  Responsible for type-ahead word prompting for the command input.
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

    public WordPromptHandler(ViewContainer app) {
        this.app = app;
        this.availableWords = new HashSet<String>(Arrays.asList(Constants.DEFAULT_COMMAND_WORDS));
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
            // clear the prompt if the fragment is less than 3 characters
            row.updateWords(Collections.emptyList());
        }

        // ignore if the input has not changed
        if (newInput.length()>2 && !newInput.equalsIgnoreCase(currentInput)) {
            this.currentInput = newInput;

            String formingWord = getFormingWord();

            // only provide word prompt if you have at least 2 characters in the word
            if (formingWord.length()>2) {
                row.updateWords(getPromptWordsFromStub(formingWord));
            }

        }
    }

    /**
     *  Retrieves the last word that is just forming in the current command.
     *
     * @return
     */
    private String getFormingWord() {
        String formingWord = currentInput;

        // only resolve for the currently forming word
        int i = currentInput.lastIndexOf(" ");
        if (i>0) {
            // only interested in the last word fragment of the input
            formingWord = currentInput.substring(i+1);
        }

        return formingWord;
    }

    private Collection<String> getPromptWordsFromStub(String formingWord) {
        // TODO - logic for prompt resolution goes here

        return availableWords.stream().filter(word -> word.startsWith(formingWord)).collect(Collectors.toSet());
    }

    /**
     *  If there is only one possible prompt available, return it, otherwise return empty.
     *
     *  Used in autofill.
     *
     * @return
     */
    Optional<String> onlyWord() {
        Collection<String> prompts = getPromptWordsFromStub(getFormingWord());
        if (prompts.size()==1) {
            return Optional.of(prompts.iterator().next());
        }
        return Optional.empty();
    }

}
