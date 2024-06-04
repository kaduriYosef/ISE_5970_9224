package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a triangle geometry, likely inheriting from a more general Polygon class.
 */
public class Triangle extends Polygon {
    /**
     * @param x - The first point on the Triangle.
     * @param y - The second point on the Triangle.
     * @param z - The third point on the Triangle.
     */
    public Triangle(Point x, Point y, Point z) {
        super(x, y, z);
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        // Find intersections with the plane containing the triangle
        List<Point> intersections = plane.findIntersections(ray);

        // If there are no intersections with the plane, return null
        if (intersections == null) {
            return null;
        }
        List<Point> triangleIntersections = new ArrayList<Point>();
        for (Point p : intersections) {
            // Check if the intersection point is inside the triangle
            Vector v1 = vertices.get(0).subtract(ray.getHead());
            Vector v2 = vertices.get(1).subtract(ray.getHead());
            Vector v3 = vertices.get(2).subtract(ray.getHead());

            Vector n1 = v1.crossProduct(v2);
            Vector n2 = v2.crossProduct(v3);
            Vector n3 = v3.crossProduct(v1);

            double t1 = ray.getDir().dotProduct(n1);
            double t2 = ray.getDir().dotProduct(n2);
            double t3 = ray.getDir().dotProduct(n3);

            if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) {
                triangleIntersections.add(p);
            }
        }
        // If there are no intersections with the triangle, return null
        if (triangleIntersections.isEmpty()) {
            return null;
        }

        return triangleIntersections;
    }
}