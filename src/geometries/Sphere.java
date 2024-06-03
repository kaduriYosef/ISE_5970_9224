package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * This class represents a sphere geometry in 3D space.
 */
public class Sphere extends RadialGeometry {

    /**
     * The center point of the sphere.
     */
    final private Point center;


    /**
     * Constructor that creates a sphere with a given radius and center point.
     *
     * @param radius - The radius of the sphere (positive value expected).
     * @param center - The center point of the sphere.
     */
    public Sphere(Point center, double radius) {
        super(radius);  // Call the parent class constructor to initialize the radius
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(center);//There may be an exception here that the vector is zero
        return v.normalize();
    }

    @Override
    public List<Point> findIntsersections(Ray ray) {
        Vector u = center.subtract(ray.getHead());
        double tm = u.dotProduct(ray.getDir());
        double dSquared = u.lengthSquared() - tm * tm;

        if (dSquared >= radius * radius) {
            // No collision
            return null;
        }

        double th = Math.sqrt(radius * radius - dSquared);
        double t1 = tm - th;
        double t2 = tm + th;

        if (t2 <= 0) {
            // Both intersection points are behind the ray
            return null;
        }

        List<Point> result;
        if (t1 <= 0) {
            // One intersection point is behind the ray, the other is in front
            result = List.of(ray.getHead().add(ray.getDir().scale(t2)));
        } else {
            // Both intersection points are in front of the ray
            result = List.of(
                    ray.getHead().add(ray.getDir().scale(t1)),
                    ray.getHead().add(ray.getDir().scale(t2))
            );
        }

        return result;
    }
}
