package geometries;

import primitives.Point;
import primitives.Vector;

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
    public Sphere(double radius, Point center) {
        super(radius);  // Call the parent class constructor to initialize the radius
        this.center = center;
    }

    /**
     * This method is required because it's inherited from the Geometry interface,
     * but it's not currently implemented for spheres. It might need to be overridden
     * in a subclass to provide meaningful normal vector calculation for spheres.
     *
     * @param p - The point on the sphere (might not be used in the current implementation).
     * @return  Currently returns null, indicating this method needs implementation.
     */
    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}
