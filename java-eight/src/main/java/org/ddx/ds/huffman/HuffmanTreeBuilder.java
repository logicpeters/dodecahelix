package org.ddx.ds.huffman;

/**
 * Builder class for a Huffman Encoding tree.
 * <p>
 */
public class HuffmanTreeBuilder {

    private int capacity;

    private HuffmanMinHeap minHeap;

    public HuffmanTreeBuilder(int capacity) {
        this.capacity = capacity;
        this.minHeap = new HuffmanMinHeap(capacity);
    }

    /**
     * Add a character with its specified frequency.
     * <p>
     * Use a min-heap to keep this sorted with the other incoming frequencies.
     *
     * @param ch
     * @param freq
     */
    public void frequency(char ch, int freq) throws HuffmanEncodingException {
        // when adding a new char, build onto the current min-heap
        //  NOTE: use toUpperCase so that we don't need to worry about case sensitivity
        HuffmanNode node = new HuffmanNode(String.valueOf(ch).toUpperCase(), freq);
        this.minHeap.add(node);
    }


    /**
     * Builds and returns the HuffmanTree using the provided frequencies.
     *
     * @return
     */
    public HuffmanTree build() throws HuffmanEncodingException {

        // repeat until there is only 1 node left on the min heap
        while (this.minHeap.getCurrentSize() > 1) {
            // take the two items of the lowest precedence off of the tree and combine
            HuffmanNode lowestNode = this.minHeap.remove();
            HuffmanNode nextLowestNode = this.minHeap.remove();

            String combined = lowestNode.getValue() + nextLowestNode.getValue();
            String reverseCombined = nextLowestNode.getValue() + lowestNode.getValue();
            int totalFreq = lowestNode.getFrequency() + nextLowestNode.getFrequency();
            HuffmanNode newNode = new HuffmanNode(combined, totalFreq);

            // whether to flip the lowest and nextLowest
            if (nextLowestNode.isSmallerThan(lowestNode)) {
                newNode.setLeftChild(nextLowestNode);
                newNode.setRightChild(lowestNode);
                newNode.setValue(reverseCombined);
            } else {
                newNode.setLeftChild(lowestNode);
                newNode.setRightChild(nextLowestNode);
            }

            this.minHeap.add(newNode);
        }

        HuffmanTree htree = new HuffmanTree(this.minHeap.remove());
        return htree;
    }

}
