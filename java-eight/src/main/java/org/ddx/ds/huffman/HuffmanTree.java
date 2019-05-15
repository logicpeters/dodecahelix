package org.ddx.ds.huffman;

import org.ddx.ds.sorts.CharacterBubbleSort;

import java.io.PrintWriter;

/**
 * A type of max heap binary tree used for encoding and decoding text using the Huffman strategy.<p>
 * The tree is built from a list of characters and their relative frequencies of occurrence in some context.<p>
 * The characters with higher frequencies will have fewer binary digits,<p>
 * while the characters with lower frequencies will have more binary digits.<p>
 * <p>
 * When you combine them, you should end up with a compressed form of the text.<p>
 *
 */
public class HuffmanTree {

    private HuffmanNode rootNode;

    public HuffmanTree(HuffmanNode rootNode) {
        this.rootNode = rootNode;
    }

    public HuffmanNode getRootNode() {
        return rootNode;
    }

    /**
     * Encodes a cleartext string into a binary string.
     *
     * @param text
     * @return
     */
    public String encode(String text) {
        StringBuilder encoded = new StringBuilder();
        HuffmanNode currentNode;
        for (char ch : text.toCharArray()) {
            // convert to uppercase to match case of tree (case insensitive)
            ch = Character.toUpperCase(ch);

            // start by evaluating the root node
            currentNode = this.rootNode;

            // check to make sure that character is in the tree (otherwise ignore)
            if (currentNode.getValue().indexOf(ch) >= 0) {
                // keep treversing the tree until we've reached a leaf
                while (!currentNode.isLeaf()) {
                    // indexOf returns -1 is character is not found
                    if (currentNode.getRightChild().getValue().indexOf(ch) >= 0) {
                        encoded.append("1");
                        currentNode = currentNode.getRightChild();
                    } else {
                        // *must* be to the left or the tree is invalid
                        encoded.append("0");
                        currentNode = currentNode.getLeftChild();
                    }
                }
            }
        }

        return encoded.toString();
    }

    /**
     * Decodes a binary string back into a cleartext form.
     * <p>
     * This cleartext form will *only* support the characters that were provided in the frequency table.
     *
     * @param binaryCode
     * @return
     */
    public String decode(String binaryCode) throws HuffmanEncodingException {
        StringBuilder decoded = new StringBuilder();

        char[] binaryDigits = binaryCode.toCharArray();

        // the position of the digit in the binary text that is being evaluated
        int idx = 0;

        HuffmanNode currentNode;
        while (idx < binaryDigits.length) {
            currentNode = this.rootNode;
            while (!currentNode.isLeaf()) {
                if (idx >= binaryDigits.length) {
                    throw new HuffmanEncodingException("provided binary text cannot be decoded");
                }
                char binaryDigit = binaryDigits[idx];
                if (binaryDigit == '0') {
                    currentNode = currentNode.getLeftChild();
                } else {
                    currentNode = currentNode.getRightChild();
                }
                idx++;
            }
            // this is a leaf, so should be a single char
            decoded.append(currentNode.getValue());
        }

        return decoded.toString();
    }

    public void printPreOrderTraversal(PrintWriter pw) {
        this.printPreOrderTraversal(pw, this.getRootNode());
    }

    /**
     * Prints out an in-order traversal of this tree using the supplied PrintWriter
     * <p>
     * This is a recursive function.
     *
     * @param pw   - object to append the output
     * @param node -
     */
    private void printPreOrderTraversal(PrintWriter pw, HuffmanNode node) {
        pw.println(CharacterBubbleSort.sortCharsInString(node.getValue()) + " : " + node.getFrequency());
        if (node.getLeftChild() != null) printPreOrderTraversal(pw, node.getLeftChild());
        if (node.getRightChild() != null) printPreOrderTraversal(pw, node.getRightChild());
    }

}
