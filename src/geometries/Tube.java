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
     * @param axis   - The central axis of the tube represented by a Ray object.
     */
    public Tube(Ray axis, double radius) {
        super(radius);  // Call the parent class constructor to initialize the radius
        this.axis = axis;
    }

    @Override
    public Vector getNormal(Point p) {

        //Calculate the vector from the point Head to the point we got
        Vector v = p.subtract(axis.getHead());//There may be an exception here that the vector is zero
        //We will do a scalar multiplication between the vector of the line and the vector we got.
        // And because the vector of the line is normalized, the result will be the projection of
        // our vector on the line
        double d = v.dotProduct(axis.getDir());
        //We will build a point according to the direction of the vector
        // and add to it the projection from previous
        Point o = axis.getPoint(d);
        //We will return the vector from point O from place to point I gave normalized

        return p.subtract(o).normalize();
    }
}
