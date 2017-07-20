package org.ddx.util;

/**
 *  Simple, commonly used string methods (these are also available in apache commons-lang, but provided here without the need to bring a library).
 */
public class StringUtil {

    /**
     * Removes quotes around text and strips leading and trailing spaces
     *
     * @param responseText
     * @return
     */
    public static String trimAndStripQuotes(String responseText) {
        responseText = responseText.trim();

        while (responseText.startsWith("\"")) {
            responseText = responseText.substring(1);
        }
        while (responseText.endsWith("\"")) {
            responseText = responseText.substring(0, responseText.length() - 1);
        }

        return responseText;
    }

    /**
     * The first character of some text is made upper case.
     *
     * @param word
     * @return
     */
    public static String capitalize(String word) {
        StringBuilder capital = new StringBuilder(word.substring(0, 1).toUpperCase());
        capital.append(word.substring(1));
        return capital.toString();
    }

    /**
     * If the first character of the string is upper case - make it lowercase.
     *
     * @param word
     * @return
     */
    public static String decapitalize(String word) {
        StringBuilder decapitalized = new StringBuilder(word.substring(0, 1).toLowerCase());
        decapitalized.append(word.substring(1));
        return decapitalized.toString();
    }

    /**
     * Return true if null or blank.
     *
     * Whitespace characters do not count, so text consisting of a space character(s) woulc be considered blank.
     *
     * @param text
     * @return
     */
    public static boolean isBlank(String text) {
        return (text == null || text.trim().equals(""));
    }

    /**
     * Return true if null or empty.
     *
     * Whitespace characters are counted as content, so text consisting of only space characters would NOT be empty.
     *
     * @param text
     * @return
     */
    public static boolean isEmpty(String text) {
        return (text == null || text.equals(""));
    }

    /**
     * Verifies if two strings are both not null and equal (ignoring case)
     *
     * @param text
     * @param compareText
     * @return
     */
    public static boolean equalsIgnoreCase(String text, String compareText) {
        boolean equal = false;
        if (text != null && compareText != null && text.equalsIgnoreCase(compareText)) {
            equal = true;
        }
        if (text==null && compareText==null) {
            equal = true;
        }
        return equal;
    }

    /**
     * Removes all non-alphabetic (a-z, upper or lower case) characters from a text string
     *
     * @param title
     * @return
     */
    public static String stripNonAlphas(String title) {
        return title.replaceAll("[^a-zA-Z]", "");
    }

    /**
     * Truncates text to the specified limit (if length is greater than the limit)
     *
     * @param text
     * @param limit
     * @param appendDots - if true, appends three periods to the end of the string when truncated
     * @return
     */
    public static String truncate(String text, int limit, boolean appendDots) {
        if (text != null && text.length() > limit) {
            text = text.substring(0, limit);
            if (appendDots) {
                text += "...";
            }
        }
        return text;
    }

    /**
     * Ignores case when checking the startsWith condition, and also checks for null (returns false if either text is null).
     *
     * @param startingText - the fragment of the text to check for
     * @param fullText
     * @return
     */
    public static boolean startsWithIgnoreCase(String startingText, String fullText) {
        return (fullText != null
            && startingText != null
            && fullText.toLowerCase().startsWith(startingText.toLowerCase()));
    }
}
