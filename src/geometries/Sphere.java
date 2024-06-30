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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        // Degenerate case: Ray originates from the circle center
        if (center.equals(ray.getHead())) {
            return List.of(new GeoPoint(this, ray.getPoint(radius))); // Point on circle with radius distance
        }

        // Calculate the vector from circle center to ray origin (head)
        Vector u = center.subtract(ray.getHead());

        // Calculate the projection of u onto the ray direction vector
        double tm = u.dotProduct(ray.getDir());  // tm = projection of u onto ray direction

        // Distance squared between center and the projection of ray origin onto ray direction
        double dSquared = u.dotProduct(u) - tm * tm;  // Avoids redundant lengthSquared calculation

        // Check if the distance is greater than the radius squared (no intersection)
        if (dSquared >= radius * radius) {
            return null; // No intersection
        }

        // Calculate the distance along the ray for the two intersection points
        double th = Math.sqrt(radius * radius - dSquared);
        double t1 = tm - th;
        double t2 = tm + th;

        // Check if both intersection points are behind the ray origin (no intersection)
        if (t2 <= 0) {
            return null; // Both intersections behind the ray
        }

        // Check if only one intersection point is in front of the ray origin
        if (t1 <= 0) {
            return List.of(new GeoPoint(this, ray.getHead().add(ray.getDir().scale(t2))));
        } else {
            // Both intersection points are in front of the ray origin
            return List.of(new GeoPoint(this,
                    ray.getHead().add(ray.getDir().scale(t1))), new GeoPoint(this,
                    ray.getHead().add(ray.getDir().scale(t2))));
        }
    }
}

