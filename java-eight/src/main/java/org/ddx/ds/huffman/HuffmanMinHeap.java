package org.ddx.ds.huffman;

/**
 * Temporary min heap to store the frequencies (as HuffmanNode elements)
 * in order when building a Huffman Encoding tree.
 *
 */
public class HuffmanMinHeap {

    /**
     *   This will maintain the added nodes "in order"
     */
    private HuffmanNode[] heap;

    private int capacity;

    private int currentSize;

    public HuffmanMinHeap(int capacity) {
        heap = new HuffmanNode[capacity];
        this.capacity = capacity;
        this.currentSize = 0;
    }

    public void add(HuffmanNode node) throws HuffmanEncodingException {
        if (currentSize >= capacity) {
            throw new HuffmanEncodingException("Tree capacity exceeded.  Cannot add more than " + capacity + " characters");
        }

        // add the new node to the last used index in the array
        heap[currentSize] = node;

        int currentIdx = currentSize;
        ;
        // move the new node up the tree until it's larger or equal to it's parent
        HuffmanNode parent;
        while (currentIdx > 0) {
            int parentIdx = (currentIdx - 1) / 2;
            parent = heap[parentIdx];

            // if the parent is a smaller or equal freq, we are valid.
            //  if (parent.getFrequency() <= node.getFrequency()) {
            if (parent.isSmallerThan(node)) {
                break;
            }

            // swap
            heap[currentIdx] = parent;
            heap[parentIdx] = node;
            currentIdx = parentIdx;
        }

        // now the size should be correct
        currentSize++;
    }

    /**
     * Removes the top node and then heapifies the remaining structure.
     *
     * @return
     */
    public HuffmanNode remove() throws HuffmanEncodingException {
        if (currentSize < 1) {
            throw new HuffmanEncodingException("Attempting to remove from an empty heap.");
        }
        HuffmanNode top = heap[0];

        // now, swap root with the bottom-most node
        currentSize--;
        heap[0] = heap[currentSize];
        heap[currentSize] = null;

        // heapify the tree from this current index
        heapify(0);

        return top;
    }

    /**
     * Validates this structure making sure it follows the min heap rules.
     *
     * @return
     */
    public boolean validateHeap() throws HuffmanEncodingException {
        return isValid(0);
    }

    /**
     * Recursive method to check for validity of an individual node in the tree.
     *
     * @param index
     * @return
     */
    private boolean isValid(int index) throws HuffmanEncodingException {
        if (!isLeaf(index)) {
            int currentValue = heap[index].getFrequency();

            // is valid if both left and right child nodes are lesser in 'frequency'
            HuffmanNode leftChild = heap[2 * index + 1];
            int leftValue = leftChild.getFrequency();
            HuffmanNode rightChild = heap[2 * index + 2];

            // the the event of a null right child, give this a negative value
            int rightValue = -1;
            if (rightChild != null) {
                rightValue = rightChild.getFrequency();
            }

            if (leftValue < currentValue || (rightValue > 0 && rightValue < currentValue)) {
                throw new HuffmanEncodingException("invalid node in heap." +
                        "  Left value = " + leftValue + "." +
                        "  Right value = " + rightValue + "." +
                        "  Current value = " + currentValue);
            }
            // check validity of left node
            if (!isValid(2 * index + 1)) {
                return false;
            }
            // check validity of right node
            return rightValue <= 0 || isValid(2 * index + 2);
        }
        // leaf node validation
        return true;
    }

    /**
     * Recursive method to go through the tree and
     * make sure that the nodes follow the Min Heap rules.
     * (i.e; no parent can be larger than its children)
     *
     * @param index
     */
    private void heapify(int index) {
        // stopping point of recursion is if we hit a leaf node
        if (!isLeaf(index)) {
            HuffmanNode parent = heap[index];
            HuffmanNode leftChild = heap[2 * index + 1];
            HuffmanNode rightChild = heap[2 * index + 2];

            // if one child is smaller, we needs to swap it
            if (leftChild.isSmallerThan(parent) || (rightChild != null && rightChild.isSmallerThan(parent))) {
                // figure out if we swap the left or right node
                boolean swapLeft = true;
                if (rightChild != null) {
                    if (rightChild.isSmallerThan(leftChild)) {
                        swapLeft = false;
                    }
                }

                if (swapLeft) {
                    heap[2 * index + 1] = heap[index];
                    heap[index] = leftChild;
                    heapify(2 * index + 1);
                } else {
                    heap[2 * index + 2] = heap[index];
                    heap[index] = rightChild;
                    heapify(2 * index + 2);
                }
            }
        }
    }

    public int getCurrentSize() {
        return currentSize;
    }

    private boolean isLeaf(int index) {
        boolean leaf = false;
        if (((2 * index + 1) >= capacity) || (heap[2 * index + 1] == null)) {
            leaf = true;
        }
        return leaf;
    }
}
