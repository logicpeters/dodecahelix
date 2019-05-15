package org.ddx.ds.huffman;

/**
 * Represents a character (or set of characters) and their associated frequency of usage in some context.
 * <p>
 * For usage with a Huffman Encoding tree strategy.
 */
public class HuffmanNode {

    /**
     * String value of the node.
     * Can be a single or multiple character.
     */
    private String value;

    /**
     * Relative frequency of the character,
     * or sum of the set of characters.
     */
    private int frequency;

    private HuffmanNode leftChild;

    private HuffmanNode rightChild;

    public HuffmanNode(String value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    /**
     * Resolve whether this node is 'smaller' than another node.
     * <p>
     * Determines precedence rules in the HuffmanTree and MinHeap.
     *
     * @param that
     * @return
     */
    public boolean isSmallerThan(HuffmanNode that) {
        boolean smaller = false;

        // first, compare by frequency
        if (this.getFrequency() == that.getFrequency()) {
            // next, files of length 1 are "higher" than length X
            int thisLen = this.value.length();
            int thatLen = that.getValue().length();
            if (thisLen == 1 && thatLen > 1) {
                smaller = true;
            } else if (thatLen == 1 && thisLen > 1) {
                smaller = false;
            } else {
                // finally, use string comparison (where i.e; A < B)
                smaller = (this.value.compareTo(that.getValue()) < 0);
            }
        } else {
            smaller = (this.getFrequency() < that.getFrequency());
        }

        return smaller;
    }

    public boolean isLeaf() {
        return (rightChild == null && leftChild == null);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public HuffmanNode getLeftChild() {
        return leftChild;
    }

    public void setLeftChild(HuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    public HuffmanNode getRightChild() {
        return rightChild;
    }

    public void setRightChild(HuffmanNode rightChild) {
        this.rightChild = rightChild;
    }
}
