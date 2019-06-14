package org.ddx.puzzle.bowling;

import java.util.Arrays;

/**
 *   Represents a single frame in a game of bowling.
 *
 *   Can have up to three rolls in a frame.
 *   Most rounds have two rolls.
 *   There is only one roll in the case of a strike.
 *   In the tenth round there is a third roll.
 *
 */
public class Frame {

    /**
     *  Represents the number of pins dropped per roll.
     */
    private int[] rolls = new int[] {0, 0, 0};

    public void setRoll(int rollIdx, int numPins) {
        rolls[rollIdx] = numPins;
    }

    public int getRoll(int rollIdx) {
        return rolls[rollIdx];
    }

    public boolean isStrike() {
        return (rolls[0] == 10);
    }

    public boolean isSpare() {
        return (!isStrike() && (rollsum() == 10));
    }

    public boolean isOpen() {
        return (!isStrike() && !isSpare());
    }

    public int rollsum() {
        return Arrays.stream(rolls).sum();
    }

    public void setStrike() {
        rolls[0] = 10;
    }

    public void setSpare() {
        int firstRoll = rolls[0];
        rolls[1] = 10 - firstRoll;
    }

    @Override
    public String toString() {
        return "Frame{" + "rolls=" + Arrays.toString(rolls) + '}';
    }
}
