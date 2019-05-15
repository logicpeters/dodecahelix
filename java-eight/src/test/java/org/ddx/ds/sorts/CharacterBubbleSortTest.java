package org.ddx.ds.sorts;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class CharacterBubbleSortTest {

    @Test
    public void testCharacterSort() {
        Assert.assertEquals("ABCD", CharacterBubbleSort.sortCharsInString("DBCA"));
        Assert.assertEquals("123GO", CharacterBubbleSort.sortCharsInString("3G1O2"));
        // TODO - more unit tests
    }
}
