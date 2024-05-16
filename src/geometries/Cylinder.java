package geometries;

import primitives.Point;
import primitives.Vector;

public class Cylinder extends Tube {
    protected final double height;

    public Cylinder(double height) {
        this.height = height;
    }
    public Vector getNormal(Point p) {
        return null;
    }
}