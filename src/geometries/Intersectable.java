package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;

/**
 * This interface defines objects that can be intersected by a ray.
 */
public interface Intersectable {

    /**
     * Finds all the intersection points between the given `Ray` and the geometric shape represented by this object.
     *
     * @param ray The `Ray` to check for intersections with.
     * @return A `List` of `Point` objects representing the intersection points, or `null` if there are no intersections.
     */
    List<Point> findIntersections(Ray ray);
}

