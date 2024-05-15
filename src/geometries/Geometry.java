package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry {

    public abstract Vector getNormal(Point p);

}
