package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;

public interface Intersectable {
    List<Point> findIntsersections(Ray ray);

}
