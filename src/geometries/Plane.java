package geometries;

import primitives.Vector;
import primitives.Point;

public class Plane implements Geometry {

    private final Point p;
    private final Vector normal;

    public Plane(Point p, Vector normal) {
        this.p = p;
        this.normal = normal;
    }

    public Plane(Point x, Point y, Point z) {
        p = x;
        Vector v1 = x.subtract(y);
        Vector v2 = x.subtract(z);
        normal = v1.crossProduct(v2).normalize();
    }

    public Vector getNormal(Point p) {
        return this.normal.normalize();
    }

    public Vector getNormal() {
        return this.normal.normalize();
    }
}
