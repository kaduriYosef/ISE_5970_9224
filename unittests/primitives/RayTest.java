package primitives;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Point class
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(0, 0, 1));

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test the getPoint of ray when the scalar is positive
        assertEquals(new Point(0, 0, 4), ray.getPoint(3), "Test when the scalar is positive (TC01)");

        // TC02: Test when the scalar is negative
        assertEquals(new Point(0, 0, -2), ray.getPoint(-3), "Test when the scalar is negative (TC02)");

        // =============== Boundary Values Tests ==================
        // TC03: Test when the scalar is zero
        assertEquals(new Point(0, 0, 1), ray.getPoint(0), "Test when the scalar is zero (TC03)");

    }
}