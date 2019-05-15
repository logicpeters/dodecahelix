package org.ddx.ds;

import org.junit.Assert;
import org.junit.Test;

/**
 * Simple Unit Test to see if the singly linked list Queue data structure is operating correctly.
 *
 */
public class LinkedQueueTest {

    @Test
    public void testQueueOperations() throws UnderflowError {
        Queue<String> queue = new LinkedQueue();
        Assert.assertTrue(queue.isEmpty());
        queue.add("foo");
        queue.add("bar");
        Assert.assertFalse(queue.isEmpty());
        Assert.assertEquals("foo", queue.peek());
        Assert.assertEquals(2, queue.size());
        Assert.assertEquals("foo", queue.remove());
        Assert.assertEquals(1, queue.size());
        Assert.assertEquals("bar", queue.remove());

        boolean threwStackUnderflow = false;
        try {
            queue.remove();
        } catch(UnderflowError sue) {
            threwStackUnderflow = true;
        }
        Assert.assertTrue(threwStackUnderflow);
        Assert.assertTrue(queue.isEmpty());
    }

}
