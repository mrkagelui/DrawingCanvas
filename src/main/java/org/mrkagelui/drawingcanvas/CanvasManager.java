package org.mrkagelui.drawingcanvas;

import java.io.PrintStream;

public class CanvasManager {

    private Pixel[][] pixels;

    private String command;
    private String[] commandSplits;
    private final char Line = 'x';
    private final char BoundaryHorizontal = '-';
    private final char BoundaryVertical = '|';
    private int x, y, secondX, secondY;

    public char getLineChar() {
        return Line;
    }

    public Pixel getPixelAt(int x, int y) {
        int realX = x - 1;
        int realY = y - 1;
        try {
            return pixels[realY][realX];
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public CanvasManager setCommand(String command) {
        this.command = command;
        sanityCheck();
        return this;
    }

    public void update() {
        if (null == command) return;
        char commandChar = Character.toUpperCase(commandSplits[0].charAt(0));
        switch (commandChar) {
            case 'C':
                newCanvas();
                break;
            case 'L':
                drawLine(x, y, secondX, secondY);
                break;
            case 'R':
                drawRectangle(x, y, secondX, secondY);
                break;
            case 'B':
                fill(x, y, commandSplits[3].charAt(0));
                break;
            default:
                break;
        }

        cleanUpCommand();
    }

    private void newCanvas() {
        pixels = new Pixel[y][x];
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j] = new Pixel(Line);
            }
        }
    }

    private void drawLine(int x, int y, int secondX, int secondY) {
        // straight lines only
        if (x != secondX && y != secondY) {
            return;
        }

        if (x == secondX) {
            int smaller = (y < secondY) ? y : secondY;
            int larger = (y < secondY) ? secondY : y;
            for (int i = smaller; i <= larger; i++) {
                pixels[i][x].drawLine();
            }
        }
        else if (y == secondY) {
            int smaller = (x < secondX) ? x : secondX;
            int larger = (x < secondX) ? secondX : x;
            for (int i = smaller; i <= larger; i++) {
                pixels[y][i].drawLine();
            }
        }
    }

    private void drawRectangle(int x, int y, int secondX, int secondY) {
        if (x > secondX || y > secondY) {
            return;
        }

        drawLine(x, y, secondX, y);
        drawLine(secondX, y, secondX, secondY);
        drawLine(secondX, secondY, x, secondY);
        drawLine(x, secondY, x, y);
    }

    private void fill(int x, int y, char color) {

    }

    public void clear() {
        if (null == pixels) return;
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                pixels[i][j].clear();
            }
        }
    }

    public void show(PrintStream p) {
        if (null == pixels) return;
        printHorizontalBoundary(p);
        for (int i = 0; i < pixels.length; i++) {
            printOneLine(i, p);
        }
        printHorizontalBoundary(p);
    }

    private void printHorizontalBoundary(PrintStream p) {
        if (null == pixels) return;
        for (int i = 0; i < pixels[0].length + 2; i++) {
            p.print(BoundaryHorizontal);
        }
        p.println();
    }

    private void printOneLine(int y, PrintStream p) {
        printVerticalBoundary(p);
        for (int j = 0; j < pixels[y].length; j++) {
            printPixel(y, j, p);
        }
        printVerticalBoundary(p);
        p.println();
    }

    private void printVerticalBoundary(PrintStream p) {
        p.print(BoundaryVertical);
    }

    private void printPixel(int i, int j, PrintStream p) {
        p.print(pixels[i][j]);
    }

    private void sanityCheck() {
        if (null == command) return;
        commandSplits = command.split("\\s");
        boolean valid = true;

        if (0 == commandSplits.length || commandSplits[0].length() < 1) {
            valid = false;
        }
        else {
            char commandChar = Character.toUpperCase(commandSplits[0].charAt(0));
            switch (commandChar) {
                case 'C':
                    if (commandSplits.length < 3 || !commandSplits[1].matches("\\d+")
                            || !commandSplits[2].matches("\\d+"))
                        valid = false;
                    else {
                        x = Integer.parseInt(commandSplits[1]);
                        y = Integer.parseInt(commandSplits[2]);
                    }
                    break;
                case 'L':
                case 'R':
                    if (null == pixels || commandSplits.length < 5
                            || !commandSplits[1].matches("\\d+")
                            || !commandSplits[2].matches("\\d+")
                            || !commandSplits[3].matches("\\d+")
                            || !commandSplits[4].matches("\\d+"))
                        valid = false;
                    else {
                        x = Integer.parseInt(commandSplits[1]) - 1;
                        y = Integer.parseInt(commandSplits[2]) - 1;
                        secondX = Integer.parseInt(commandSplits[3]) - 1;
                        secondY = Integer.parseInt(commandSplits[4]) - 1;
                        if (!areValidCoordinates(x, y) || !areValidCoordinates(secondX, secondY)) {
                            valid = false;
                        }
                    }
                    break;
                case 'B':
                    if (null == pixels || commandSplits.length < 4
                            || !commandSplits[1].matches("\\d+")
                            || !commandSplits[2].matches("\\d+")
                            || commandSplits[3].length() != 1)
                        valid = false;
                    else {
                        x = Integer.parseInt(commandSplits[1]) - 1;
                        y = Integer.parseInt(commandSplits[2]) - 1;
                        if (!areValidCoordinates(x, y)) {
                            valid = false;
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        if (!valid) {
            cleanUpCommand();
        }
    }

    private void cleanUpCommand() {
        command = null;
        commandSplits = null;
        x = 0;
        y = 0;
        secondX = 0;
        secondY = 0;
    }

    private boolean areValidCoordinates(int x, int y) {
        if (null == pixels) return false;
        if (0 > x || x >= pixels[0].length || 0 > y || y >= pixels.length) return false;
        return true;
    }
}
