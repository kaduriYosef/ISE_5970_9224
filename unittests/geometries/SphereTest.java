package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

/**
 * Unit test for geometries.Sphere claas
 */
class SphereTest {

    /**
     * Test method for {@link geometries.Sphere#getNormal(Point)}
     */
    @Test
    void testGetNormal() {
        // Create a new sphere with center (1,1,1) and radius 2.
        Sphere sphere = new Sphere(new Point(1, 1, 1), 2);


        Point pointOnSurface = new Point(1, 1, 3);


        // ============ Equivalence Partitions Tests ==============

        // Test that the normal vector at a point on the surface of the sphere points
        assertEquals(new Vector(0, 0, 2).normalize(), sphere.getNormal(pointOnSurface), "GetNormal() test for point on surface 3");

    }
}
