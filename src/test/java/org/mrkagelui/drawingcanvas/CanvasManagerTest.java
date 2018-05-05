package org.mrkagelui.drawingcanvas;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CanvasManagerTest {
    private CanvasManager cm;

    @BeforeEach
    void clearCanvasData() {
        cm = new CanvasManager();
        cm.setCommand("c 14 9").update();
        cm.clear();
    }

    @Test
    void testNewCanvas() {
        assertNotNull(cm.getPixelAt(14, 9),
                "Graphic data should be created at max width and height");

        cm.setCommand("C 9 15").update();
        assertNotNull(cm.getPixelAt(9, 15),
                "After recreation, graphic should be created at new max");
        assertNull(cm.getPixelAt(14, 9),
                "After recreation, graphic should not have old data");
    }

    @Test
    void testDrawLine() {
        cm.setCommand("L 1 3 2 4").update();
        assertNotEquals(cm.getLineChar(), cm.getPixelAt(1, 3).getPixelChar(),
                "Line should not be drawn if not straight");

        cm.setCommand("L 1 3 1 100").update();
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
        cm.setCommand("R 2 3 9 7").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(9, 6).getPixelChar(),
                "Line should be drawn at the side of rectangle");
        assertNotEquals(cm.getLineChar(), cm.getPixelAt(5, 4).getPixelChar(),
                "Line should not be drawn inside the rectangle");

        cm.setCommand("R 1 2 1 2").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(1, 2).getPixelChar(),
                "Dot should be supported for rectangle");

        cm.setCommand("R 2 3 5 5").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(5, 3).getPixelChar(),
                "Line should be drawn if one rectangle is contained in another");
        assertNotEquals(cm.getLineChar(), cm.getPixelAt(4, 4).getPixelChar(),
                "Line should not be drawn inside the inner rectangle");

        cm.setCommand("R 6 4 13 9").update();
        assertEquals(cm.getLineChar(), cm.getPixelAt(9, 4).getPixelChar(),
                "Line should be drawn if two rectangles overlap");
        assertNotEquals(cm.getLineChar(), cm.getPixelAt(7, 5).getPixelChar(),
                "Line should not be drawn in the overlapping area");
    }

    @Test
    void testFillWhenClickInRectangle() {
        cm.setCommand("R 2 3 9 7").update();
        cm.setCommand("B 3 4 c").update();
        assertEquals('c', cm.getPixelAt(6, 4).getPixelChar());
        assertNotEquals('c', cm.getPixelAt(10, 8).getPixelChar());
    }

    @Test
    void testFillWhenClickAtLine() {
        cm.setCommand("L 9 3 9 7").update();
        cm.setCommand("B 9 5 v").update();
        assertNotEquals('v', cm.getPixelAt(10, 8).getPixelChar());
    }

    @Test
    void testFillInCaseOfHalfOpenSpace() {
        cm.setCommand("L 2 4 10 4").update();
        cm.setCommand("L 7 1 7 5").update();
        cm.setCommand("L 10 2 2 2").update();
        cm.setCommand("L 10 1 10 5").update();
        cm.setCommand("B 3 4 u").update();
        assertEquals('u', cm.getPixelAt(7, 9).getPixelChar());
        assertNotEquals('u', cm.getPixelAt(8, 4).getPixelChar());
    }

    @Test
    void testFillInOverlappingRectangle() {
        cm.setCommand("R 2 3 9 7").update();
        cm.setCommand("R 6 4 13 9").update();

        cm.setCommand("B 7 5 i").update();
        assertEquals('i', cm.getPixelAt(8, 6).getPixelChar());
        assertNotEquals('i', cm.getPixelAt(4, 4).getPixelChar(),
                "Should not be filled since in a different enclosure");
        assertNotEquals('i', cm.getPixelAt(10, 2).getPixelChar(),
                "Should not be filled since out of rectangles");
    }

    @Test
    void testFillOutOfOverlappingRectangle() {
        cm.setCommand("R 2 3 9 7").update();
        cm.setCommand("R 6 4 13 9").update();

        cm.setCommand("B 10 2 p").update();
        assertEquals('p', cm.getPixelAt(4, 8).getPixelChar());
        assertNotEquals('p', cm.getPixelAt(8, 6).getPixelChar(),
                "Should not be filled since in overlapping area");
        assertNotEquals('p', cm.getPixelAt(4, 4).getPixelChar(),
                "Should not be filled since in one of rectangles");
    }

    @Test
    void testFillTwiceInEmptySpace() {
        cm.setCommand("B 10 2 a").update();
        cm.setCommand("B 10 2 b").update();
        assertEquals('b', cm.getPixelAt(7, 9).getPixelChar());
        assertNotEquals('a', cm.getPixelAt(8, 6).getPixelChar(),
                "Should have been overwritten");
    }

    @Test
    void testFillTwiceInDifferentEnclosure() {
        cm.setCommand("R 2 3 9 7").update();
        cm.setCommand("R 6 4 13 9").update();

        cm.setCommand("B 10 2 m").update();
        cm.setCommand("B 7 5 n").update();
        assertEquals('m', cm.getPixelAt(4, 8).getPixelChar());
        assertEquals('n', cm.getPixelAt(8, 6).getPixelChar());

        assertTrue('m' != cm.getPixelAt(4, 4).getPixelChar()
                && 'n' != cm.getPixelAt(4, 4).getPixelChar());
        assertTrue('m' != cm.getPixelAt(4, 4).getPixelChar()
                && 'n' != cm.getPixelAt(9, 8).getPixelChar());
    }
}