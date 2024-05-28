package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for geometries.Tube claas
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // Create a new Tube with center (1,1,1) and radius 2.
        Point centerPoint = new Point(1, 1, 1);
        Tube tube = new Tube(new Ray(centerPoint, new Vector(0, 0, 1)), 2);

        // Define some test points.
        Point pointOnSurface1 = new Point(1, 3, 3);


        // ============ Equivalence Partitions Tests ==============

        // Test that the normal vector at a point on the surface of the sphere points
        // outwards from the center of the sphere.
        assertEquals(new Vector(0, 1, 0).normalize(), tube.getNormal(pointOnSurface1), "GetNormal() test for point on surface");


        // =============== Boundary Values Tests ==================

        // Test that an IllegalArgumentException is thrown when calling getNormal()
        // with a point at the center of the sphere.
        assertThrows(IllegalArgumentException.class, () -> tube.getNormal(centerPoint), "GetNormal() test for center point");
    }
}