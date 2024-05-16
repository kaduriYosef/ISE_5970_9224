package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Ray;

public class Cylinder extends Tube {
    protected final double height;

    public Cylinder(double height, double radius, Ray axis) {

        super(radius, axis);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        return null;
    }
}