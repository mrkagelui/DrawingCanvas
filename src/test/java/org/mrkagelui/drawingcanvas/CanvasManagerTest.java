package org.mrkagelui.drawingcanvas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasManagerTest {
    private CanvasManager cm;

    @BeforeEach
    void clearCanvasData() {
        cm = new CanvasManager();
        cm.setCommand("c 14 5").update();
        cm.clear();
    }

    @Test
    void testNewCanvas() {
        assertNotNull(cm.getPixelAt(14, 5),
                "Graphic data should be created at max width and height");

        cm.setCommand("C 9 15").update();
        assertNotNull(cm.getPixelAt(9, 15),
                "After recreation, graphic should be created at new max");
        assertNull(cm.getPixelAt(14, 5),
                "After recreation, graphic not have old data");
    }

    @Test
    void testDrawLine() {
        cm.setCommand("L 1 3 2 4").update();
        assertNotEquals(cm.getLineChar(), cm.getPixelAt(1, 3).getPixelChar(),
                "Line should not be drawn if not straight");

        cm.setCommand("L 1 3 1 6").update();
        assertNotEquals(cm.getLineChar(), cm.getPixelAt(1, 4).getPixelChar(),
                "Line should not be drawn if index out of bound");

        cm.setCommand("L 2 4 10 4").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(5, 4).getPixelChar(),
                "Line should be drawn from left to right");
        cm.setCommand("L 7 1 7 5").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(7, 4).getPixelChar(),
                "Should be able to show line downward, must not negate any intersection");
        cm.setCommand("L 8 2 2 2").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(7, 2).getPixelChar(),
                "Should be able to show line from right to left");
        cm.setCommand("L 3 5 3 3").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(3, 4).getPixelChar(),
                "Line should be drawn upward");
        cm.setCommand("L 6 1 6 1").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(6, 1).getPixelChar(),
                "Dot should be supported");
    }

    @Test
    void testDrawRectangle() {
    }

    @Test
    void testFill() {

    }
}