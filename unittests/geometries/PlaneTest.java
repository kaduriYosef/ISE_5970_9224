package geometries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Plane claas
 *
 */
class PlaneTest {

    /**
     * Test method for {@link geometries.Plane#getNormal(Point)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Plane with normal pointing along z-axis
        Point q1 = new Point(1, 2, 3);
        Point q2 = new Point(2, 1, 3);
        Point q3 = new Point(1, 1, 3);
        Plane plane = new Plane(q1, q2, q3);

        // generate the test result
        Vector result = plane.getNormal();

        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");

        // ensure the result matches the expected normal
        assertTrue(result.equals(new Vector(0, 0, 1))|| result.equals(new Vector(0,0,-1)), "Plane's normal is incorrect");
    }
    @Test
    public void testConstructor(){

        Point q1 = new Point(1, 0, 0);
        Point q2 = new Point(0, 1, 0);
        Point q3 = new Point(0.5, 0.5, 0);
        // ============ Boundary Partitions Tests ==============
        assertThrows(IllegalArgumentException.class,()-> new Plane(q1, q2, q1),"constructor with the same point");
        assertThrows(IllegalArgumentException.class,()-> new Plane(q1, q2, q3),"constructor with points on the same line");
    }
}