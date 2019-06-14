package org.ddxlabs.shell;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created on 5/17/2019.
 */
public class QueApp {

    private static final String FONT_FILE = "bitstream.ttf";
    private static final int DEFAULT_FONT_SIZE = 18;

    private static Font createFontFromFile(String fontFilename, int size) throws IOException, FontFormatException {
        Font font;
        try (InputStream inputStream = QueShell.class
                .getClassLoader()
                .getResourceAsStream(fontFilename)) {
            font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        }
        return font.deriveFont((float)size);
    }

    private static Font getDefaultMonoFont(int size) {
        return new Font("Monospaced", Font.PLAIN, size);
    }

    private static Terminal createTerminal() throws IOException {
        int fontSize = DEFAULT_FONT_SIZE;
        String fontFileName = FONT_FILE;

        Font font = null;
        try {
            font = createFontFromFile(fontFileName, fontSize);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            font = getDefaultMonoFont(fontSize);
        }
        SwingTerminalFontConfiguration fontConfig = new SwingTerminalFontConfiguration(false, AWTTerminalFontConfiguration.BoldMode.EVERYTHING, font);
        DefaultTerminalFactory tFact = new DefaultTerminalFactory();
        tFact.setTerminalEmulatorFontConfiguration(fontConfig);
        return tFact.createTerminal();
    }

    public static void main(String[] args) {
        QueShell shell = new QueShell();
        Screen screen = null;
        try {
            Terminal terminal = createTerminal();
            screen = new TerminalScreen(terminal);
            shell.init(screen, terminal);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                    screen.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
