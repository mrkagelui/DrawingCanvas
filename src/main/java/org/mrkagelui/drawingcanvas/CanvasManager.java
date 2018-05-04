package org.mrkagelui.drawingcanvas;

public class CanvasManager {
    public char[][] getCanvas() {
        return canvas;
    }

    public byte[][] getPixelTypes() {
        return pixelTypes;
    }

    public char getLine() {
        return Line;
    }

    public char getBoundaryHorizontal() {
        return BoundaryHorizontal;
    }

    public char getBoundaryVertical() {
        return BoundaryVertical;
    }

    private char[][] canvas;
    private byte[][] pixelTypes;

    public CanvasManager setCommand(String command) {
        this.command = command;
        return this;
    }

    private String command;
    private final char Line = 'x';
    private final char BoundaryHorizontal = '-';
    private final char BoundaryVertical = '|';

    public void update() {

    }

    public void draw() {

    }

    public void clear() {

    }
}
