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

    /**
     * This method is required because it's inherited from the Geometry interface,
     * but it's not currently implemented for tubes. It might need to be overridden
     * in a subclass to provide meaningful normal vector calculation for points on the tube's surface.
     *
     * @param p - The point on the tube (might not be used in the current implementation).
     * @return  Currently returns null, indicating this method needs implementation.
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
