package org.ddxlabs.shell;

import com.googlecode.lanterna.TextColor;

import java.util.*;

public class TerminalOutput {

    private List<StyledLine> lines;

    public TerminalOutput() {
        lines = new ArrayList<StyledLine>();
    }

    public TerminalOutput(String line) {
        this();
        lines.add(new StyledLine(line));
    }

    public void addHeader(String header, boolean makeBold) {
        lines.add(0, new StyledLine(header, makeBold));
    }

    public void addLine(String line) {
        addLine(line, false);
    }

    public void addLine(String line, boolean makeBold) {
        addLine(line, TextColor.ANSI.WHITE, makeBold);
    }

    public void addLine(String line, TextColor color) {
        addLine(line, color, false);
    }

    public void addLine(String line, TextColor color, boolean makeBold) {
        addLine(line, color, TextColor.ANSI.BLACK, false);
    }

    public void addLine(String line, TextColor color, TextColor background, boolean makeBold) {
        lines.add(new StyledLine(line, color, background, makeBold));
    }

    public List<StyledLine> getLines() {
        return lines;
    }

    /**
     * Break up the output lines according to a maximum line length.
     *
     * Lines are added to this output without regard to the length supported by the terminal.  This call must be made
     * before printing to the screen, otherwise they will not wrap
     *
     * @param maxLineLength
     */
    public void breakupLines(int maxLineLength) {
        // go through lines and if they are longer than the maximum length,
        //   break them into multiple lines.
        ListIterator<StyledLine> listIt = lines.listIterator();
        while (listIt.hasNext()) {
            StyledLine line = listIt.next();
            if (line.getContent().length() > maxLineLength) {
                boolean bold = line.isBold();
                long lineId = line.getLineId();
                TextColor fg = line.getForeground();
                TextColor bg = line.getBackground();
                StringTokenizer tok = new StringTokenizer(line.getContent(), " ");
                listIt.remove();

                StringBuilder outputLine = new StringBuilder();
                int lineLen = 0;
                while (tok.hasMoreTokens()) {
                    String word = tok.nextToken();
                    if (lineLen + word.length() > maxLineLength) {
                        listIt.add(new StyledLine(outputLine.toString(),fg, bg, bold, lineId));
                        outputLine.setLength(0);
                        lineLen = 0;
                    }
                    outputLine.append(word);
                    outputLine.append(" ");
                    lineLen += word.length() + 1;
                }
                listIt.add(new StyledLine(outputLine.toString(),fg, bg, bold, lineId));
            }
        }
    }

    /**
     *  Combine lines that have previously been broken
     */
    public void combineLines() {
        ListIterator<StyledLine> listIt = lines.listIterator();

        long currentLineId = -1;
        StyledLine combinedLine = new StyledLine("");
        StringBuilder combinedContent = new StringBuilder();

        while (listIt.hasNext()) {
            StyledLine line = listIt.next();
            long lineId = line.getLineId();
            if (lineId != currentLineId) {
                // write old line (if any)
                if (currentLineId != -1) {
                    combinedLine.setContent(combinedContent.toString());
                    listIt.add(combinedLine);
                    combinedContent.setLength(0);
                }

                // new line
                combinedLine.setLineId(lineId);
                combinedLine.setBackground(line.getBackground());
                combinedLine.setForeground(line.getForeground());
                combinedLine.setBold(line.isBold());
                combinedContent.append(line.getContent());
                currentLineId = lineId;
            } else {
                combinedContent.append(" ");
                combinedContent.append(line.getContent());
            }
            listIt.remove();
        }
        // add the remaining contents of the last line
        combinedLine.setContent(combinedContent.toString());
        listIt.add(combinedLine);
    }

}
