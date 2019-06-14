package com.ddxlabs.consola.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SolidCaret extends DefaultCaret {

    private Color[] colors = new Color[] {Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY, Color.GRAY};
    private int currentColor = 0;

    public SolidCaret() {
        this(false, 0,true, 400);
    }

    public SolidCaret(boolean blink, int blinkRate, boolean colorChange, int changeRate) {
        super();

        if (colorChange) {
            int delay = changeRate; //milliseconds
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    SolidCaret.this.repaint();
                }
            };
            new Timer(delay, taskPerformer).start();
        }

        if (blink) {
            this.setBlinkRate(blinkRate);
        }
    }

    protected synchronized void damage(Rectangle r) {
        if (r == null)
            return;

        // give values to x,y,width,height (inherited from java.awt.Rectangle)
        x = r.x;
        y = r.y;
        height = r.height;

        // A value for width was probably set by paint(), which we leave alone.
        // But the first call to damage() precedes the first call to paint(), so
        // in this case we must be prepared to set a valid width, or else
        // paint() will receive a bogus clip area and caret will not get drawn properly.
        if (width <= 0)
            width = getComponent().getWidth();

        repaint(); // calls getComponent().repaint(x, y, width, height)
    }

    public void paint(Graphics g) {
        JTextComponent comp = getComponent();
        if (comp == null)
            return;

        Rectangle r = null;
        try {
            r = comp.modelToView(getDot());
            if (r == null) {
                return;
            }
        } catch (BadLocationException e) {
            return;
        }

        if ((x != r.x) || (y != r.y)) {
            // paint() has been called directly, without a previous call to
            // damage(), so do some cleanup. (This happens, for example, when
            // the text component is resized.)
            repaint(); // erase previous location of caret
            x = r.x; // Update dimensions (width gets set later in this method)
            y = r.y;
            height = r.height;
        }

        currentColor++;
        if (currentColor>=colors.length) {
            currentColor = 0;
        }
        g.setColor(colors[currentColor]);
        //g.setColor(comp.getCaretColor());

        g.setXORMode(comp.getBackground()); // do this to draw in XOR mode

        if (isVisible())
            //g.fillRect(r.x, r.y, width, r.height);
            g.fillRect(r.x, r.y, 10, r.height);
    }

}
