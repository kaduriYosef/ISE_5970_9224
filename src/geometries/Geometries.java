
package geometries;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import primitives.Point;
import primitives.Ray;

/**
 * The `Geometries` class represents a collection of geometric shapes in 3D space.
 * It implements the `Intersectable` interface, allowing it to be treated as a single
 * entity for ray intersection calculations.
 */
public class Geometries extends Intersectable {

    /**
     * A list of `Intersectable` objects representing the individual geometric shapes
     * in the collection.
     */
    private final List<Intersectable> intersectables;

    /**
     * Creates an empty `Geometries` object.
     */
    public Geometries() {
        this.intersectables = new LinkedList<>();
    }

    /**
     * Creates a `Geometries` object containing the provided `Intersectable` objects.
     *
     * @param geometries An array of `Intersectable` objects representing the shapes.
     */
    public Geometries(Intersectable... geometries) {
        this();
        add(geometries);
    }

    /**
     * Adds the provided `Intersectable` objects to the internal list.
     *
     * @param geometries An array of `Intersectable` objects to be added.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(this.intersectables, geometries);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> result = null;
        for (Intersectable shape : intersectables) {
            List<GeoPoint> shapePoints = shape.findGeoIntersectionsHelper(ray);
            if (shapePoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(shapePoints);
            }
        }
        return result;
    }
}