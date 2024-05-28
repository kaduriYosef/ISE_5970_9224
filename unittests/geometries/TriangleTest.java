package geometries;

import org.junit.jupiter.api.Test;
import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.isZero;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Triangle claas
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Triangle lying on the xy-plane
        Point a = new Point(0, 0, 0);
        Point b = new Point(1, 0, 0);
        Point c = new Point(0, 1, 0);
        Triangle triangleEP = new Triangle(a, b, c);

        // generate the test result
        Vector result = triangleEP.getNormal(new Point(0, 0, 0));

        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");

        // ensure the result matches the expected normal pointing along z-axis
        assertTrue(result.equals(new Vector(0, 0, 1)) || result.equals(new Vector(0, 0, -1)), "Triangle's normal is incorrect");


        // Ensure the result is orthogonal to all the edges
        assertTrue(isZero(result.dotProduct(b.subtract(a))), "Triangle's normal is not orthogonal to edge AB in TC02");
        assertTrue(isZero(result.dotProduct(c.subtract(b))), "Triangle's normal is not orthogonal to edge BC in TC02");
        assertTrue(isZero(result.dotProduct(a.subtract(c))), "Triangle's normal is not orthogonal to edge CA in TC02");

    }

}