package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static primitives.Util.alignZero;

/**
 * This class represents a cylinder geometry, likely inheriting from a more general Tube class.
 * A cylinder can be seen as a tube with a finite height.
 */
public class Cylinder extends Tube {

    /**
     * The height of the cylinder.
     */
    protected final double height;

    public Cylinder(Ray axis, double radius, double height) {

        super(axis, radius); // Call the parent class constructor to initialize radius and axis
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {

        if (super.axis.getHead().equals(p)) {
            return super.axis.getDir();
        }

        //Calculate the vector from the point P0 to the point we got
        Vector v = p.subtract(axis.getHead());//There may be an exception here that the vector is zero
        //We will do a scalar multiplication between the vector of the line and the vector we got.
        // And because the vector of the line is normalized, the result will be the projection of
        // our vector on the line
        double d = alignZero(Math.abs(v.dotProduct(axis.getDir())));

        if (d == 0 || alignZero(d - height) == 0) {
            return super.axis.getDir();
        }

        if (alignZero(height - d) > 0) {
            return super.getNormal(p);
        }

        if (alignZero(d - height) > 0) {
            throw new IllegalArgumentException("this point it's outside the cylinder");
        }

        // If none of the above conditions are true, then the point is on the lateral surface of the cylinder.
        // We can compute the normal vector by subtracting the projection of the vector v onto the axis ray from v itself.
        // return v.subtract(super.axisRay.getDir().scale(d)).normalize();
        //impossible case
        return null;
    }
}
