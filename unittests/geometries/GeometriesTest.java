package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {


    @Test
    void findIntersections() {

        Geometries geometries = new Geometries(
                new Plane(new Point(3, 2, 1), new Vector(1, 1, 0)),
                new Triangle(new Point(0, 1, 1), new Point(2, 0, 3), new Point(1, 3, -1)),
                new Sphere(new Point(1, 2, 3), 1)
        );

        // ============ Equivalence Partitions Tests ==============

        // TC01: collides with the plane and the triangle but not with the sphere
        List<Point> result = geometries.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");

        // ============ Boundary Value Tests ======================

        // TC11: collides only with the plane
        result = geometries.findIntersections(new Ray(new Point(1, 0, 0), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        // TC12: Clashes with everything
        result = geometries.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, 1, 1)));
        assertEquals(4, result.size(), "Wrong number of points");
        // TC13: Doesn't collide with anything
        result = geometries.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, -1, 0)));
        assertNull(result, "Wrong number of points");

        Geometries geometries2 = new Geometries();

        // TC14: There are no bodies at all
        result = geometries2.findIntersections(new Ray(new Point(1, 0, 0), new Vector(0, -1, 0)));
        assertNull(result, "Wrong number of points");

    }
}