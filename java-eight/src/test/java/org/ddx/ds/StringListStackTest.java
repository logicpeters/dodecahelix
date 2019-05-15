package org.ddx.ds;

import org.junit.Assert;
import org.junit.Test;

/**
 * Simple Unit Test to see if the Stack data structure is operating correctly.
 *
 */
public class StringListStackTest {

    @Test
    public void testStackOperations() throws UnderflowError {
        Stack<String> stack = new StringListStack();
        Assert.assertTrue(stack.isEmpty());
        stack.push("foo");
        stack.push("bar");
        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals("bar", stack.peek());
        Assert.assertEquals(2, stack.size());
        Assert.assertEquals("bar", stack.pop());
        Assert.assertEquals(1, stack.size());
        Assert.assertEquals("foo", stack.pop());

        boolean threwStackUnderflow = false;
        try {
            stack.pop();
        } catch(UnderflowError sue) {
            threwStackUnderflow = true;
        }
        Assert.assertTrue(threwStackUnderflow);
        Assert.assertTrue(stack.isEmpty());
    }


}
