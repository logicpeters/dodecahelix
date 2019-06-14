package org.ddx.puzzle.bowling;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BowlingScoreTest {

    @Test
    public void testScoring() {
        Bowler bowler = new Bowler();

        assertEquals(300, bowler.scoreGame("XXXXXXXXXXXX"));
        assertEquals(90, bowler.scoreGame("9-9-9-9-9-9-9-9-9-9-"));
        assertEquals(150, bowler.scoreGame("5/5/5/5/5/5/5/5/5/5/5"));
        assertEquals(167, bowler.scoreGame("X7/9-X-88/-6XXX81"));
    }

}
