package geometries;

import primitives.Vector;
import primitives.Point;
import primitives.Ray;

import static primitives.Util.isZero;
import static primitives.Util.alignZero;

import java.util.List;

/**
 * This class represents a geometric plane in 3D space.
 */
public class Plane extends Geometry {

    /**
     * A point on the plane.
     */
    private final Point p;

    /**
     * The normal vector to the plane.
     */
    private final Vector normal;

    /**
     * Constructor that creates a plane from a point and a normal vector.
     *
     * @param p      - A point on the plane.
     * @param normal - The normal vector to the plane.
     */
    public Plane(Point p, Vector normal) {
        this.p = p;
        this.normal = normal;
    }

    /**
     * Constructor that creates a plane from three points.
     * The normal vector is calculated from the cross product of the vectors
     * formed by subtracting the second and third points from the first point.
     *
     * @param x - The first point on the plane.
     * @param y - The second point on the plane.
     * @param z - The third point on the plane.
     */
    public Plane(Point x, Point y, Point z) {
        p = x;
        normal = x.subtract(y).crossProduct(x.subtract(z)).normalize();
    }

    @Override
    public Vector getNormal(Point p) {
        return this.normal.normalize();
    }

    ;

    /**
     * Gets the normalized normal vector to the plane.
     *
     * @return The normalized normal vector to the plane.
     */
    public Vector getNormal() {
        return this.normal.normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = null;

        // Get the plane's normal,
        Vector n = getNormal();
        // ray's direction vector,
        Vector v = ray.getDir();
        // and ray's starting point.
        Point p0 = ray.getHead();
        // Check if the ray is not parallel to the plane.
        double nv = n.dotProduct(v);
        if (!isZero(nv)) {
            // Calculate the intersection point between the ray and the plane.
            double t = alignZero(n.dotProduct(p.subtract(p0)) / nv);
            // Check if the intersection point is in front of the starting point of the ray.
            if (t > 0) {
                // Calculate the intersection point coordinates and return it.
                Point intersect = p0.add(v.scale(t));
                result = List.of(new GeoPoint(this, ray.getPoint(t)));
            }
        }
        // Return the intersection point or null if no intersection exists.
        return result;
    }

    @Override
    public void calcBoundingBox() {
    }
}
