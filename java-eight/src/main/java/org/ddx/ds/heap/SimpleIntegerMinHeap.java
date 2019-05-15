package org.ddx.ds.heap;

/**
 * Min heap to maintain a set of integers in sorted order.
 *
 * Iterative solution.
 */
public class SimpleIntegerMinHeap implements Heap<Integer> {

    /**
     * This array will maintain the added nodes "in order"
     */
    private HeapNode<Integer>[] heap;

    private int capacity;

    private int currentSize;

    public SimpleIntegerMinHeap(int capacity) {
        heap = new SimpleIntegerHeapNode[capacity];
        this.capacity = capacity;
        this.currentSize = 0;
    }

    public void add(HeapNode<Integer> node) throws HeapException {
        if (currentSize >= capacity) {
            throw new HeapException("Tree capacity exceeded.  Cannot add more than " + capacity + " characters");
        }

        // add the new node to the last used index in the array
        heap[currentSize] = node;

        int currentIdx = currentSize;

        // move the new node up the tree until it's larger or equal to it's parent
        HeapNode<Integer> parent;
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
    public HeapNode<Integer> remove() throws HeapException {
        if (currentSize < 1) {
            throw new HeapException("Attempting to remove from an empty heap.");
        }
        HeapNode<Integer> top = heap[0];

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
    public boolean validateHeap() throws HeapException {
        return isValid(0);
    }

    /**
     * Method to check for validity of an individual node in the tree.
     * <p>
     * NOTE: This is only used in the unit tests.  The main 'add' and 'remove'
     * operations do *not* reference this code and do not use recursion.
     *
     * @param index
     * @return
     */
    private boolean isValid(int index) throws HeapException {
        if (!isLeaf(index)) {
            // is valid if both left and right child nodes are lesser in 'frequency'
            HeapNode<Integer> leftChild = heap[2 * index + 1];
            HeapNode<Integer> rightChild = heap[2 * index + 2];

            if (leftChild.isSmallerThan(heap[index])
                    || (rightChild != null && rightChild.isSmallerThan(heap[index]))) {
                throw new HeapException("invalid node in heap." +
                        "  Left value = " + leftChild.getValue() + "." +
                        "  Right value = " + rightChild.getValue() + "." +
                        "  Current value = " + heap[index]);
            }
            // check validity of left node
            if (!isValid(2 * index + 1)) {
                return false;
            }
            // check validity of right node
            return rightChild == null || isValid(2 * index + 2);
        }
        // leaf node validation
        return true;
    }

    /**
     * @param index
     */
    private void heapify(int index) {

        // heapify down the tree until we hit a leaf node
        while (!isLeaf(index)) {
            HeapNode<Integer> parent = heap[index];
            HeapNode<Integer> leftChild = heap[2 * index + 1];
            HeapNode<Integer> rightChild = heap[2 * index + 2];

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
                    // heapify the left node
                    index = 2 * index + 1;
                } else {
                    heap[2 * index + 2] = heap[index];
                    heap[index] = rightChild;
                    // heapify the right node
                    index = 2 * index + 2;
                }
            } else {
                // children are fine so we are done.
                break;
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
