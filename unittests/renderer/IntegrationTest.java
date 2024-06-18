package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for camera rays and geometric intersections.
 */
public class IntegrationTest {

    /**
     * Camera builder for the tests
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setLocation(Point.ZERO)
            .setDirection(new Vector(0, 0, -1), new Vector(0, -1, 0))
            .setVpDistance(1);


    /**
     * Constructs rays for all pixels in the view plane.
     *
     * @return a linked list of rays for all pixels.
     */
    private LinkedList<Ray> constructRayOnAllPixels() throws CloneNotSupportedException {
        /**
         * camera for test
         */
        Camera camera = cameraBuilder.setVpSize(3, 3).build();
        /**
         * list of rays
         */
        final var listOfRays = new LinkedList<Ray>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                listOfRays.add(camera.constructRay(3, 3, j, i));
            }
        }
        return listOfRays;
    }

    /**
     * Counts the number of intersection points between the given geometry and the rays.
     *
     * @param body the geometric body to check intersections with.
     * @return the total number of intersection points.
     */
    private int countIntersectionSphere(Intersectable body) throws CloneNotSupportedException {
        LinkedList<Ray> listOfRays = constructRayOnAllPixels();
        int sum = 0;
        for (Ray ray : listOfRays) {
            List<Point> temp;
            temp = body.findIntersections(ray);
            sum += temp == null ? 0 : temp.size();
        }
        return sum;
    }

    /**
     * Test method for
     * {@link renderer.Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testIntegration() throws CloneNotSupportedException {
        Camera camera = cameraBuilder.setVpSize(3, 3).build();
        LinkedList<Ray> listOfRays = constructRayOnAllPixels();
        //TC's for in intersection points of sphere and view plane
        Point p00m2 = new Point(0, 0, -2);

        // group tests of construct rays and intersections with sphere:

        // TC01: Verify that the method returns 2 intersection points for a sphere with radius 1 located in front of the center pixel.
        Sphere sphere1 = new Sphere(new Point(0, 0, -3), 1);
        assertEquals(2, countIntersectionSphere(sphere1),
                "Failed to return 2 intersection points for a sphere with radius 1 located in front of the center pixel (TC01)");

        // TC02: Verify that the method returns 18 intersection points for a sphere that matches the size of the view plane.
        camera = cameraBuilder.setLocation(new Point(0, 0, 0.5)).build();
        listOfRays = constructRayOnAllPixels();
        Sphere sphere2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        assertEquals(18, countIntersectionSphere(sphere2),
                "Failed to return 18 intersection points for a sphere that matches the size of the view plane (TC02)");

        // TC03: Verify that the method returns 10 intersection points for a sphere with a size almost equal to the view plane.
        Sphere sphere3 = new Sphere(p00m2, 2);
        assertEquals(10, countIntersectionSphere(sphere3),
                "Failed to return 10 intersection points for a sphere with a size almost equal to the view plane (TC03)");

        // TC04: Verify that the method returns 9 intersection points when the view plane is inside the sphere.
        Sphere sphere4 = new Sphere(new Point(0, 0, -0.5), 4);
        assertEquals(9, countIntersectionSphere(sphere4),
                "Failed to return 9 intersection points when the view plane is inside the sphere (TC04)");

        // TC05: Verify that the method returns 0 intersection points when the sphere is located behind the view plane.
        Sphere sphere5 = new Sphere(new Point(0, 0, 1), 0.5);
        assertEquals(0, countIntersectionSphere(sphere5),
                "Failed to return 0 intersection points when the sphere is located behind the view plane (TC05)");

        // group tests of construct rays and intersections with plane:

        // TC's for intersection points of plane and view plane
        Point p00m4 = new Point(0, 0, -4);
        Point p01m4 = new Point(0, 1, -4);
        Vector v001 = new Vector(0, 0, 1);

        // TC06: Verify that the method returns 9 intersection points when the plane is parallel to the view plane and in front of it.
        Plane plane1 = new Plane(p00m4, v001);
        assertEquals(9, countIntersectionSphere(plane1),
                "Failed to return 9 intersection points when the plane is parallel to the view plane and in front of it (TC06)");

        // TC07: Verify that the method returns 9 intersection points when the plane is not parallel to the view plane but still intersects it in 9 points.
        Plane plane2 = new Plane(p00m4, new Point(1, 0, -3.5), p01m4);
        assertEquals(9, countIntersectionSphere(plane2),
                "Failed to return 9 intersection points when the plane is not parallel to the view plane but still intersects it in 9 points (TC07)");

        // TC08: Verify that the method returns 6 intersection points when the plane is not parallel to the view plane and intersects it in 6 points.
        Plane plane3 = new Plane(p00m4, new Point(1, 0, -1.5), p01m4);
        assertEquals(6, countIntersectionSphere(plane3),
                "Failed to return 6 intersection points when the plane is not parallel to the view plane and intersects it in 6 points (TC08)");

        // group tests of construct rays and intersections with sphere:

        //TC's for intersection points of triangle and view plane
        Point p1m1m2 = new Point(1, -1, -2);
        Point pm1m1m2 = new Point(-1, -1, -2);
        Point p01m2 = new Point(0, 1, -2);

        // TC09: Verify that the method returns 1 intersection point when the triangle is parallel to the view plane but small.
        Triangle triangle1 = new Triangle(p01m2, p1m1m2, pm1m1m2);
        assertEquals(1, countIntersectionSphere(triangle1),
                "Failed to return 1 intersection point when the triangle is parallel to the view plane but small (TC09)");

        // TC10: Verify that the method returns 2 intersection points when the triangle is parallel to the view plane and intersects it in 2 points.
        Triangle triangle2 = new Triangle(new Point(0, 20, -2), p1m1m2, pm1m1m2);
        assertEquals(2, countIntersectionSphere(triangle2),
                "Failed to return 2 intersection points when the triangle is parallel to the view plane and intersects it in 2 points (TC10)");

    }

}
