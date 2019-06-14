package org.ddx.puzzle.bowling;

public class Bowler {

    /**
     *  Convert a string representing all of the bowling rolls into an array of 10 bowling frames.
     *
     * @param framesLine - a string of chars that represent the rolls of a single player in a game of bowling.
     * @return a 10 element array of bowling Frames
     */
    private Frame[] convertToFrames(String framesLine) {
        Frame[] frames = new Frame[10];

        int frameIdx = 0;       // index of current frame
        int frameRollIdx = 0;   // index of individual roll in current frame

        for (char roll : framesLine.toCharArray()) {
            Frame currentFrame = frames[frameIdx];
            if (currentFrame==null) {
                currentFrame = new Frame();
                frames[frameIdx] = currentFrame;
                frameRollIdx = 0;
            }

            if (frameIdx<9) {
                switch (roll) {
                    case 'X': currentFrame.setStrike(); frameIdx++; break;
                    case '/': currentFrame.setSpare(); frameIdx++; break;
                    case '-': currentFrame.setRoll(frameRollIdx, 0); frameRollIdx++; break;
                    default: currentFrame.setRoll(frameRollIdx, Character.getNumericValue(roll)); frameRollIdx++;
                }
                if (frameRollIdx==2) { frameIdx++; };
            } else {
                // 10th frame -- special case
                switch (roll) {
                    case 'X': currentFrame.setRoll(frameRollIdx, 10); frameRollIdx++; break;
                    case '/': currentFrame.setRoll(frameRollIdx, 10 - currentFrame.getRoll(frameRollIdx-1)); frameRollIdx++; break;
                    case '-': currentFrame.setRoll(frameRollIdx, 0); frameRollIdx++; break;
                    default: currentFrame.setRoll(frameRollIdx, Character.getNumericValue(roll)); frameRollIdx++;
                }
            }
        }

        return frames;
    }

    /**
     * Tallies the final score for a player from the set of frames that make up the game..
     *
     * @param frames an array of 10 frames
     * @return the tallied score of the game.
     */
    private int tally(Frame[] frames) {

        int score = 0;
        for (int frameIdx=0; frameIdx<9; frameIdx++) {
            Frame frame = frames[frameIdx];

            if (frame.isOpen()) {
                score += frame.rollsum();
            } else if (frame.isSpare())  {
                score += 10 + frames[frameIdx+1].getRoll(0);
            } else if (frame.isStrike()) {
                score += tallyStrike(frames, frameIdx);
            }
        }

        // score the 10th frame -- just a sum of all rolls.
        score += frames[9].rollsum();

        return score;
    }

    /**
     * Tallies the score of a strike frame.
     *  (this does not work for the 10th frame)
     *
     * @param frames -- all of the frames
     * @param frameIdx -- index of the frame to be tallied (i.e; from 0-9).
     * @return the final score for this single frame.
     */
    private int tallyStrike(Frame[] frames, int frameIdx) {
        int score = 10;
        Frame followFrame = frames[frameIdx+1];
        if (followFrame.isOpen() || followFrame.isSpare()) {
            score += followFrame.rollsum();
        } else {
            // strike followed by another strike
            if (frameIdx<8) {
                score += 10 + frames[frameIdx+2].getRoll(0);
            } else {
                // 9th frame is a special case: add the first two 10th frame rolls together
                score += frames[9].getRoll(0) + frames[9].getRoll(1);
            }
        }

        return score;
    }

    /**
     * Calculates the score of the bowler based on their rolls in the game..
     *
     * @param rolls
     * @return
     */
    public int scoreGame(String rolls) {
        Frame[] frames = convertToFrames(rolls);
        return tally(frames);
    }

}
