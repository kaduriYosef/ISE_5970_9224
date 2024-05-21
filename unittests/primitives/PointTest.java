package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 * @author Kaduri and Harel
 */
class PointTest {

    @Test
    void testEquals() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 =new Point(1,2,3);
        assertTrue(p1.equals(new Point(1,2,3)), "ERROR: Point.equal does not work correctly");
    }

    @Test
    void add() {
        // ============ Added Partitions Tests ==============
        Point p1 =new Point(1,2,3);
        assertEquals(new Point(0,0,0),
                p1.add(new Vector(-1,-2,-3)),
                "ERROR: Point.add doesn't work correctly");

    }

    @Test
    void subtract() {
        // ============ Subtract Partitions Tests ==============
        Point p1 =new Point(-1,-2,-3);
        assertEquals(new Point(0,0,0),
                p1.subtract(new Vector(-1,-2,-3)),
                "ERROR: Point.subtract doesn't work correctly");
    }

    @Test
    void distance() {
    }

    @Test
    void distanceSquared() {
        // ============ distanceSquared Partitions Tests ==============
        Point p1 =new Point(1,2,3);
        assertEquals(((1-(-1))*(1-(-1))+(2-(-2))*(2-(-2))+(3-(-3))*(3-(-3)))
                ,p1.distanceSquared(new Vector(-1,-2,-3)),
                "ERROR: Point.distanceSquared doesn't work correctly");
    }
}