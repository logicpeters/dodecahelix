package org.ddxlabs.shell;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TerminalTextUtils;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created on 5/15/2019.
 */
public class QueShell {

    private TextGraphics gtext;
    private Screen screen;

    // maintain a record of the output so that we can reprint when
    private Deque<StyledLine> outputRecord;

    // how many characters we can fit on a single line
    private int maxColumns;

    // how many rows we can fit inside the screen
    private int maxRows;

    // current column where the cursor is printing
    private int indentation;

    // the row that we print to.
    private int outputRow;

    // the row to collect input from
    private int inputRow;

    // number of columns to leave blank on the left before printing
    private int LEFT_MARGIN = 1;

    private TerminalOutput lastOutput;

    public QueShell() {
        this.outputRecord = new ArrayDeque<StyledLine>();
    }

    public void init(final Screen screen, Terminal backingTerminal) throws IOException {
        this.gtext = screen.newTextGraphics();

        this.screen = screen;
        this.screen.startScreen();
        this.screen.clear();
        this.screen.refresh();

        this.updateTerminalSize();

        backingTerminal.addResizeListener(new TerminalResizeListener() {
            public void onResized(Terminal terminal, TerminalSize terminalSize) {
                screen.doResizeIfNecessary();
                if ((screen.getTerminalSize().getColumns() != QueShell.this.maxColumns) ||
                        (screen.getTerminalSize().getRows() != QueShell.this.maxRows)) {
                    try {
                        doScreenResize();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        outputRow = this.maxRows - 3;
        inputRow = this.maxRows - 2;

        gtext.setForegroundColor(TextColor.ANSI.WHITE);
        gtext.setBackgroundColor(TextColor.ANSI.BLACK);
        this.indentation = 1;  // number of columns to indent text

        gtext.putString(2, 1, "Lanterna Shell - Type 'exit' to exit", SGR.BOLD);
        gtext.setForegroundColor(TextColor.ANSI.DEFAULT);
        gtext.setBackgroundColor(TextColor.ANSI.DEFAULT);

        TerminalOutput output = new TerminalOutput("Now is the time for Lorem Espum blah balh. " +
                "jiOSJD jidojsa jdiosajdoiaj sodij cajsdio jasoid caoisjd coiasjd ociasjdco iji asjcodi " +
                "asoidj oasijdco asjdoc iasjoid");
        output.addHeader("Start", true);
        publishOutput(output, true);

        String command = collectInput();
        while (!command.equalsIgnoreCase("exit")) {
            TerminalOutput cmdOutput = processCommand(command);
            publishOutput(cmdOutput, true);
            command = collectInput();
        }
    }

    private void updateTerminalSize() {
        this.maxColumns = screen.getTerminalSize().getColumns();
        this.maxRows = screen.getTerminalSize().getRows();
    }

    private void doScreenResize() throws IOException {
        // clear the screen and reprint the last stuff that had been sent
        this.screen.clear();
        this.screen.refresh();
        this.updateTerminalSize();

        outputRow = this.maxRows - 3;
        inputRow = this.maxRows - 2;

        // scroll everything up
        publishOutput(lastOutput, true);
    }

    /**
     * Send output to the screen.
     * <p>
     * For each line of output, the current screen will be scrolled up.
     * The new line will be placed on the 2nd to bottom row.
     * Lines which scroll off the screen are placed on a history stack.
     *
     * @param output
     * @throws IOException
     */
    private void publishOutput(TerminalOutput output, boolean appendSpace) throws IOException {
        this.lastOutput = output;
        for (StyledLine line : output.getLines()) {
            this.outputRecord.add(line);
        }

        // check for a resize
        TerminalSize newSize = screen.doResizeIfNecessary();
        if (newSize != null) {
            this.updateTerminalSize();
        }

        output.breakupLines(this.maxColumns - 5);

        for (StyledLine newLine : output.getLines()) {
            // scroll everything up 1 line
            screen.scrollLines(0, outputRow, 1);

            gtext.setForegroundColor(newLine.getForeground());
            gtext.setBackgroundColor(newLine.getBackground());
            if (newLine.isBold()) {
                gtext.putString(indentation, outputRow, newLine.getContent(), SGR.BOLD);
            } else {
                gtext.putString(indentation, outputRow, newLine.getContent());
            }
        }

        // add a space before next output
        if (appendSpace) {
            screen.scrollLines(0, outputRow, 1);
        }

        this.screen.refresh();
    }

    private TerminalOutput processCommand(String command) {
        TerminalOutput output = new TerminalOutput();
        output.addLine("Processed command for " + command, TextColor.ANSI.CYAN);
        return output;
    }

    private String collectInput() throws IOException {

        indentation = LEFT_MARGIN;
        gtext.putString(indentation, inputRow, "> ");
        int cursorIdx = indentation + 2;
        screen.setCursorPosition(screen.getCursorPosition().withColumn(cursorIdx).withRow(inputRow));
        screen.refresh();

        KeyStroke keyStroke = screen.readInput();
        gtext.setForegroundColor(TextColor.ANSI.GREEN);
        StringBuilder command = new StringBuilder();

        while (keyStroke.getKeyType() != KeyType.Enter) {
            // TODO - listen for screen resize commands here

            if (keyStroke.getKeyType() == KeyType.Backspace) {
                cursorIdx--;
                command.deleteCharAt(command.length() - 1);
                gtext.putString(cursorIdx, inputRow, String.valueOf(" "));
                screen.setCursorPosition(screen.getCursorPosition().withColumn(cursorIdx).withRow(inputRow));
                screen.refresh();
            } else {
                char entry = keyStroke.getCharacter();
                if (!TerminalTextUtils.isControlCharacter(entry)) {
                    command.append(entry);
                    gtext.putString(cursorIdx, inputRow, String.valueOf(entry));
                    cursorIdx++;
                    screen.setCursorPosition(screen.getCursorPosition().withColumn(cursorIdx).withRow(inputRow));
                    screen.refresh();
                } else {
                    System.out.println("control character pressed: " + entry);
                }
            }
            keyStroke = screen.readInput();
        }

        while (cursorIdx >= (indentation + 2)) {
            gtext.putString(cursorIdx, inputRow, " ");
            cursorIdx--;
        }
        screen.refresh();
        return command.toString();
    }

}
