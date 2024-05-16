package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

/**
 * This class represents a cylinder geometry, likely inheriting from a more general Tube class.
 * A cylinder can be seen as a tube with a finite height.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    protected final double height;

    public Cylinder(double height, double radius, Ray axis) {

        super(radius, axis); // Call the parent class constructor to initialize radius and axis
        this.height = height;
    }

    /**
     * This method is required because it's inherited from the Geometry interface.
     * The current implementation simply returns null, but it should be overridden to
     * calculate and return the normal vector to the surface of the cylinder at a specific point (p).
     *
     * @param p - The point on the cylinder where the normal vector is requested.
     * @return The normal vector to the surface of the cylinder at point p. (needs implementation)
     */
    @Override
    public Vector getNormal(Point p) {
        // The normal vector will depend on whether the point is on the curved surface or the top/bottom cap.
        return null;
    }
}
