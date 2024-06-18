package renderer;

import geometries.Geometry;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Camera class and its interaction with various geometries.
 * These tests verify the number of intersection points between rays constructed by the camera
 * and geometries such as spheres, planes, and triangles.
 */
class IntegrationTest {
    /** Camera builder for the tests */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(new Scene("Test")))
            .setImageWriter(new ImageWriter("Test", 1, 1))
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(1).setVpSize(3, 3);

    List<Point> pointsIntersections;

    /**
     * Tests the construction of rays with a sphere and verifies the number of intersections.
     *
     * @throws CloneNotSupportedException if the camera cannot be cloned
     */
    @Test
    void testConstructRayWithSphere() throws CloneNotSupportedException {
        // TC01: First test case
        cameraBuilder.setLocation(new Point(0, 0, 0));
        cameraBuilder.build();
        assertEquals(2, getIntersections(new Sphere(new Point(0, 0, -3), 1)).size(), "Wrong number of intersections in case 1");

        // TC02: Second test case
        cameraBuilder.setLocation(new Point(0, 0, 0.5));
        cameraBuilder.build();
        assertEquals(18, getIntersections(new Sphere(new Point(0, 0, -2.5), 2.5)).size(), "Wrong number of intersections in case 2");

        // TC03: Third test case
        cameraBuilder.setLocation(new Point(0, 0, 0.5));
        cameraBuilder.build();
        assertEquals(10, getIntersections(new Sphere(new Point(0, 0, -2), 2)).size(), "Wrong number of intersections in case 3");

        // TC04: Fourth test case
        cameraBuilder.setLocation(new Point(0, 0, 0.5));
        cameraBuilder.build();
        assertEquals(9, getIntersections(new Sphere(new Point(0, 0, 0), 4)).size(), "Wrong number of intersections in case 4");

        // TC05: Fifth test case
        cameraBuilder.setLocation(new Point(0, 0, 0));
        cameraBuilder.build();
        assertEquals(0, getIntersections(new Sphere(new Point(0, 0, 1), 0.5)).size(), "Wrong number of intersections in case 5");
    }

    /**
     * Tests the construction of rays with a plane and verifies the number of intersections.
     *
     * @throws CloneNotSupportedException if the camera cannot be cloned
     */
    @Test
    void testConstructRayWithPlane() throws CloneNotSupportedException {
        // TC01: First test case
        cameraBuilder.setLocation(new Point(0, 0, 1));
        cameraBuilder.build();
        assertEquals(9, getIntersections(new Plane(new Point(0, 0, -1), new Point(1, 0, -1), new Point(0, 1, -1))).size(), "Wrong number of intersections in case 1");

        // TC02: Second test case
        cameraBuilder.setLocation(new Point(0, 0, 1));
        cameraBuilder.build();
        assertEquals(9, getIntersections(new Plane(new Point(0, 0, -2), new Point(-3, 0, 0), new Point(-3, 2, 0))).size(), "Wrong number of intersections in case 2");

        // TC03: Third test case
        cameraBuilder.setLocation(new Point(0, 0, 1));
        cameraBuilder.build();
        assertEquals(6, getIntersections(new Plane(new Point(0, 0, -4), new Point(-3, 0, 0), new Point(-3, 2, 0))).size(), "Wrong number of intersections in case 3");
    }

    /**
     * Tests the construction of rays with a triangle and verifies the number of intersections.
     *
     * @throws CloneNotSupportedException if the camera cannot be cloned
     */
    @Test
    void testConstructRayWithTriangle() throws CloneNotSupportedException {
        // TC01: First test case
        cameraBuilder.setLocation(new Point(0, 0, 0.5));
        cameraBuilder.build();
        assertEquals(1, getIntersections(new Triangle(new Point(0, 1, -2), new Point(-1, -1, -2), new Point(1, -1, -2))).size(), "Wrong number of intersections in case 1");

        // TC02: Second test case
        cameraBuilder.setLocation(new Point(0, 0, 1));
        cameraBuilder.build();
        assertEquals(2, getIntersections(new Triangle(new Point(0, 20, -2), new Point(-1, -1, -2), new Point(1, -1, -2))).size(), "Wrong number of intersections in case 2");
    }

    /**
     * Helper method to get the list of intersection points between rays constructed by the camera
     * and a given geometry.
     *
     * @param geometry the geometry to test against
     * @return the list of intersection points
     * @throws CloneNotSupportedException if the camera cannot be cloned
     */
    private List<Point> getIntersections(Geometry geometry) throws CloneNotSupportedException {
        pointsIntersections = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = cameraBuilder.build().constructRay(3, 3, j, i);
                List<Point> intersections = geometry.findIntersections(ray);
                if (intersections != null) {
                    pointsIntersections.addAll(intersections);
                }
            }
        }
        return pointsIntersections;
    }
}
