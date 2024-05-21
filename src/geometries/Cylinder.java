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

    @Override
    public Vector getNormal(Point p) {
        // The normal vector will depend on whether the point is on the curved surface or the top/bottom cap.
        return null;
    }
}
