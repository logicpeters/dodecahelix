package org.ddx.ds.huffman;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class HuffmanTreeTest {

    /**
     *   Sample frequencies
     */
    protected static final String[] FREQUENCIES = new String[]{
            "A19",
            "B16",
            "C17",
            "D11",
            "E42",
            "F12",
            "G14",
            "H17",
            "I16",
            "J5",
            "K10",
            "L20",
            "M19",
            "N24",
            "O18",
            "P13",
            "Q1",
            "R25",
            "S35",
            "T25",
            "U15",
            "V5",
            "W21",
            "X2",
            "Y8",
            "Z3"
    };


    @Test
    public void testBuildTree() throws HuffmanEncodingException {
        HuffmanTreeBuilder treeBuilder = new HuffmanTreeBuilder(27);
        for (int i = 0; i< FREQUENCIES.length; i++) {
            char c = FREQUENCIES[i].charAt(0);
            int freq = Integer.valueOf(FREQUENCIES[i].substring(1));
            treeBuilder.frequency(c, freq);
        }
        HuffmanTree tree = treeBuilder.build();

        String cleartext = "The quick brown fox jumped over the lazy dog.";
        String expected = cleartext.replaceAll("[^A-Za-z0-9]", "");
        String encodedBinary = tree.encode(cleartext);
        System.out.print(encodedBinary + " = ");
        String decoded = tree.decode(encodedBinary);
        System.out.println(decoded);
        Assert.assertEquals(expected.toLowerCase(), decoded.toLowerCase());

        // This is the provided example
        cleartext = "Hello World";
        encodedBinary = tree.encode(cleartext);
        expected = "1101101000010001111100011111101000000101100";
        Assert.assertEquals(expected, encodedBinary);
    }

}

