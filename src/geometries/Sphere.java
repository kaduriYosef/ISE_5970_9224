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
    public Sphere( Point center,double radius) {
        super(radius);  // Call the parent class constructor to initialize the radius
        this.center = center;
    }

    @Override
    public Vector getNormal(Point p) {
        Vector v = p.subtract(center);//There may be an exception here that the vector is zero
        return v.normalize();
    }
}
