package geometries;

import primitives.Ray;
import primitives.Point;

import java.util.List;
import java.util.Objects;

/**
 * Intersectable class for geometries that can be intersected.
 */
public abstract class Intersectable {

    /**
     * The bounding box of the geometry
     */
    protected BoundingBox boundingBox;

    /**
     * The geometry that was hit.
     */
    public static class GeoPoint {
        /**
         * The geometry that was hit.
         */
        public Geometry geometry;
        /**
         * The point where the geometry was hit.
         */
        public Point point;

        /**
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        /**
         * Compares this GeoPoint with the specified object for equality.
         *
         * @param obj the object to be compared for equality with this GeoPoint
         * @return true if the specified object is equal to this GeoPoint
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            GeoPoint geoPoint = (GeoPoint) obj;
            return Objects.equals(geometry.getClass().getSimpleName(), geoPoint.geometry.getClass().getSimpleName())
                    && Objects.equals(point, geoPoint.point);
        }


        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**
     * Finds the intersections of a given ray with a list of points.
     *
     * @param ray the ray to find intersections with
     * @return a list of points representing the intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Find the intersections of a given Ray with GeoPoints
     *
     * @param ray the Ray for which intersections are to be found
     * @return a list of GeoPoints representing the intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        if (boundingBox != null && !boundingBox.hasIntersections(ray))
            return null;
        return findGeoIntersectionsHelper(ray);
    }

    /**
     * This method is a helper function to find intersections of a ray with geo points.
     *
     * @param ray the ray for which intersections are to be found
     * @return a list of geo points representing the intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

    /**
     * Calculate the bounding box of this intersectable.
     */
    public abstract void calcBoundingBox();

}

