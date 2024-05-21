package geometries;

import primitives.Point;
import primitives.Vector;

/**
 * This interface defines a contract for geometric objects.
 */
public interface Geometry {

    /**
     * This method is mandatory for any class implementing the Geometry interface.
     * It requires calculating and returning the normal vector to the surface of the geometry
     * at a specific point (p).
     *
     * @param p - The point on the geometry where the normal vector is requested.
     * @return The normal vector to the surface of the geometry at point p.
     */
    public abstract Vector getNormal(Point p);

    Vector getNormal();
}
