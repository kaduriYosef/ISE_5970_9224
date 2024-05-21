package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * This class represents a tube geometry in 3D space, likely defined by a radius and a central axis.
 */
public class Tube extends RadialGeometry {

    /**
     * The central axis of the tube represented by a Ray object.
     */
    protected final Ray axis;


    /**
     * Constructor that creates a tube with a given radius and central axis.
     *
     * @param radius - The radius of the tube (positive value expected).
     * @param axis - The central axis of the tube represented by a Ray object.
     */
    public Tube(double radius, Ray axis) {
        super(radius);  // Call the parent class constructor to initialize the radius
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
