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
    @Test
    void testFindClosestPoint() {

        Point p100 = new Point(1, 0, 0);
        Point p200 = new Point(2, 0, 0);
        Point p300 = new Point(3, 0, 0);
        Vector v100 = new Vector(1,0,0);
        List<Point> list = List.of(p100, p200, p300);
        // ============ Equivalence Partitions Tests ==============
        // TC01: closest point is in the middle of the list
        assertEquals(p200,new Ray(new Point(2.1,0,0),v100).findClosestPoint(list),"closest point is in the middle of the list" );


        // =============== Boundary Values Tests ==================

        // TC11: empty list
        assertNull(new Ray(new Point(2.1,0,0),v100).findClosestPoint(null),"empty list" );


        // TC12:  first point in the list
        assertEquals(p100,new Ray(new Point(1.1,0,0),v100).findClosestPoint(list),"first point in the list" );


        // TC13: last point in the list
        assertEquals(p300,new Ray(new Point(3.1,0,0),v100).findClosestPoint(list),"last point in the list" );

    }
}
