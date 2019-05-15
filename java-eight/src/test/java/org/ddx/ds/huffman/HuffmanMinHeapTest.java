package org.ddx.ds.huffman;

import org.junit.Test;

import java.util.Random;


public class HuffmanMinHeapTest {

    private static final String ALPHA_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String randomString(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_STRING.length());
            builder.append(ALPHA_STRING.charAt(character));
        }
        return builder.toString();
    }

    @Test
    public void testMinHeapUsingLabFreqs() throws HuffmanEncodingException {

        int testCapacity = HuffmanTreeTest.FREQUENCIES.length;
        HuffmanMinHeap heap = new HuffmanMinHeap(testCapacity + 1);
        for (int i = 0; i < testCapacity; i++) {
            String c = HuffmanTreeTest.FREQUENCIES[i].substring(0, 1);
            int freq = Integer.valueOf(HuffmanTreeTest.FREQUENCIES[i].substring(1));
            heap.add(new HuffmanNode(c, freq));
        }

        assert heap.validateHeap();

        int testFreq = 0;
        for (int i = 0; i < testCapacity; i++) {
            int freq = heap.remove().getFrequency();
            // the freq we pulled off should be greater than the last one.
            assert freq >= testFreq;
            testFreq = freq;
            assert heap.validateHeap();
        }

        assert heap.getCurrentSize() == 0;
    }

    @Test
    public void testMinHeapUsingRandomFreqs() throws HuffmanEncodingException {

        Random rand = new Random();

        int testCapacity = 100;
        HuffmanMinHeap heap = new HuffmanMinHeap(testCapacity + 1);
        for (int i = 0; i < testCapacity; i++) {
            int testFreq = rand.nextInt(1000);
            heap.add(new HuffmanNode(randomString(5), testFreq));
        }

        assert heap.validateHeap();

        int testFreq = 0;
        for (int i = 0; i < testCapacity; i++) {
            int freq = heap.remove().getFrequency();
            assert freq >= testFreq;
            testFreq = freq;
            assert heap.validateHeap();
        }

        assert heap.getCurrentSize() == 0;
    }
}
