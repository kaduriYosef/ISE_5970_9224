package geometries;

import primitives.Vector;
import primitives.Point;

/**
 * This class represents a geometric plane in 3D space.
 */
public class Plane implements Geometry {

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
     * @param p - A point on the plane.
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
    };

    /**
     * Gets the normalized normal vector to the plane.
     *
     * @return The normalized normal vector to the plane.
     */
    public Vector getNormal() {
        return this.normal.normalize();
    }
}