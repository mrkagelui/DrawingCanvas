package org.mrkagelui.drawingcanvas;

public class Pixel {
    public boolean isAtLine() {
        return isAtLine;
    }

    private boolean isAtLine;

    private char pixelChar;
    private final char LineChar;

    public Pixel() {
        isAtLine = false;
        pixelChar = ' ';
        LineChar = 'x';
    }

    public Pixel(char lineChar) {
        isAtLine = false;
        pixelChar = ' ';
        LineChar = lineChar;
    }

    public char getPixelChar() {
        return pixelChar;
    }

    public void clear() {
        isAtLine = false;
        pixelChar = ' ';
    }

    public void drawColor(char pixelChar) {
        this.pixelChar = pixelChar;
    }

    public void drawLine() {
        isAtLine = true;
        pixelChar = LineChar;
    }

    @Override
    public String toString() {
        return "" + pixelChar;
    }
}
