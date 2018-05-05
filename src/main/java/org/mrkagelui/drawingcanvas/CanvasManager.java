package org.mrkagelui.drawingcanvas;

public class CanvasManager {

    private Pixel[][] pixels;

    private String command;
    private final char Line = 'x';
    private final char BoundaryHorizontal = '-';
    private final char BoundaryVertical = '|';

    public char getLineChar() {
        return Line;
    }

    public char getBoundaryHorizontal() {
        return BoundaryHorizontal;
    }

    public char getBoundaryVertical() {
        return BoundaryVertical;
    }

    public Pixel getPixelAt(int x, int y) {
        int realX = x - 1;
        int realY = y - 1;
        try {
            return pixels[realX][realY];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public CanvasManager setCommand(String command) {
        this.command = command;
        return this;
    }

    public void update() {

    }

    public void draw() {

    }

    public void clear() {

    }
}
