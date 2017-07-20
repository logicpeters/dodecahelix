package org.ddx.util;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created on 6/22/2017.
 */
public class StringUtilTest {

    @Test
    public void testCapitalization() {
        assertEquals("He flew to Chicago", StringUtil.capitalize("he flew to Chicago"));
        assertEquals("city of Chicago", StringUtil.decapitalize("City of Chicago"));
    }

    @Test
    public void testIsBlankOrEmpty() {
        assertTrue(StringUtil.isBlank(null));
        assertTrue(StringUtil.isEmpty(null));
        assertTrue(StringUtil.isBlank(""));
        assertTrue(StringUtil.isEmpty(""));
        assertTrue(StringUtil.isBlank("  "));
        assertFalse(StringUtil.isEmpty("  "));
    }

    @Test
    public void testTrimAndStripQuotes() {
        assertEquals("hello", StringUtil.trimAndStripQuotes("\"hello\""));
        assertEquals("hello", StringUtil.trimAndStripQuotes("  \"hello\"  "));
    }

    @Test
    public void testEqualsIgnoreCase() {
        assertTrue(StringUtil.equalsIgnoreCase("SomeThing", "something"));
        assertFalse(StringUtil.equalsIgnoreCase(null, "something"));
        assertFalse(StringUtil.equalsIgnoreCase("something", null));
        assertTrue(StringUtil.equalsIgnoreCase(null, null));
    }

    @Test
    public void testStripNonAlphas() {
        assertEquals("abc", StringUtil.stripNonAlphas("abc123"));
        assertEquals("Thenumberissowhat", StringUtil.stripNonAlphas("The number is 12, so what?"));
    }

    @Test
    public void testTruncate() {
        assertEquals("Every good boy...", StringUtil.truncate("Every good boy does fine", 14, true));
        assertEquals("Every ", StringUtil.truncate("Every good boy does fine", 6, false));
    }

    @Test
    public void testStartsWithIgnoreCase() {
        assertTrue(StringUtil.startsWithIgnoreCase("my naMe", "My name is Jack."));
        assertFalse(StringUtil.startsWithIgnoreCase(null, "My name is Jack."));
        assertFalse(StringUtil.startsWithIgnoreCase("My name", null));
    }
}
