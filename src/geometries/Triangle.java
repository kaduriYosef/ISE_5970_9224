package geometries;

import primitives.Point;

/**
 * This class represents a triangle geometry, likely inheriting from a more general Polygon class.
 */
public class Triangle extends Polygon {
    /**
     *
     * @param x - The first point on the Triangle.
     * @param y - The second point on the Triangle.
     * @param z - The third point on the Triangle.
     */
    public Triangle(Point x, Point y, Point z) {
        super(x, y, z);
    }
}
