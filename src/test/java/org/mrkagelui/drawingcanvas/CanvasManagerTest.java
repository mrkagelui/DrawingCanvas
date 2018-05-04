package org.mrkagelui.drawingcanvas;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasManagerTest {
    private CanvasManager cm;
    @BeforeAll
    void createCanvasManager() {
        cm = new CanvasManager();
    }

    @BeforeEach
    void clearCanvasData() {
        cm.clear();
    }

    @Test
    void testNewCanvas() {
        cm.setCommand("c 14 5");
        char[][] graphics = cm.getCanvas();
        byte[][] data = cm.getPixelTypes();
        assertEquals(14, graphics.length,
                "Graphic data should have the width specified");
        assertEquals(5, data[0].length,
                "Data array should have the height specified");

        cm.setCommand("C 9 15");
        graphics = cm.getCanvas();
        data = cm.getPixelTypes();
        assertEquals(15, graphics[0].length,
                "After recreation, graphic data should have the height specified");
        assertEquals(9, data.length,
                "After recreation, data array should have the width specified");
    }

    @Test
    void testDrawLine() {
        cm.setCommand("C 14 5").update();
        cm.setCommand("L 1 3 2 4").update();
    }

    @Test
    void testDrawRectangle() {
    }

    @Test
    void testFill() {

    }
}