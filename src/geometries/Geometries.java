
package geometries;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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

    /**
     * Add geometries to the list
     *
     * @param geometries list of geometries
     */
    public void add(List<Intersectable> geometries) {
        this.intersectables.addAll(geometries);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {

        List<GeoPoint> result = null;
        for (Intersectable shape : intersectables) {
            List<GeoPoint> shapePoints = shape.findGeoIntersections(ray);
            if (shapePoints != null) {
                if (result == null) {
                    result = new LinkedList<>();
                }
                result.addAll(shapePoints);
            }
        }
        return result;
    }

    @Override
    public void calcBoundingBox() {
        if (intersectables.isEmpty()) {
            boundingBox = null;
        } else {
            boundingBox = new BoundingBox();
            for (Intersectable g : intersectables) {
                g.calcBoundingBox();
                if (g.boundingBox != null)
                    boundingBox = boundingBox.union(g.boundingBox);
            }
        }
    }

    /**
     * Calculate the bounding box for the geometries
     */
    public void makeCBR() {
        calcBoundingBox();
    }

    /**
     * Store the geometries as a BVH
     */
    public void makeBVH() {
        // calculate the bounding box for the geometries so we can sort them
        calcBoundingBox();

        // extract infinite geometries into a separate list
        List<Intersectable> infiniteGeometries = intersectables.stream().filter(g -> g.boundingBox == null).toList();
        intersectables.removeAll(infiniteGeometries);

        // sort geometries based on their bounding box centroids along an axis (e.g. x-axis)
        intersectables.sort(Comparator.comparingDouble(g -> g.boundingBox.getCenter().getX()));

        // combine each 3 geometries into a bounding box
        while (intersectables.size() >= 3)
            intersectables.add(new Geometries(intersectables.removeFirst(),
                    intersectables.removeFirst(), intersectables.removeFirst()));

        calcBoundingBox(); // recalculate the bounding box because the geometries have changed
        intersectables.addAll(infiniteGeometries); // combine the infinite geometries back
    }
}