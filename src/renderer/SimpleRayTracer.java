package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase class,
 * responsible for tracing rays in a scene and determining the color of the closest intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Constructs a SimpleRayTracer with the given scene.
     *
     * @param scene The scene to be ray traced.
     */
    public SimpleRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray and determines the color at the closest intersection point.
     *
     * @param ray The ray to be traced.
     * @return The color at the closest intersection point, or the background color if no intersections are found.
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        if (points == null) {
            return scene.background; // Return the background color if no intersections are found
        }
        return calcColor(ray.findClosestGeoPoint(points)); // Calculate the color at the closest intersection point
    }

    /**
     * Calculates the color of a GeoPoint based on ambient light and geometry emission.
     *
     * <p>This method combines the intensity of the scene's ambient light with the emission of the
     * GeoPoint's geometry to determine its final color.</p>
     *
     * @param geoPoint The GeoPoint for which to calculate the color.
     * @return The calculated Color of the GeoPoint.
     */
    private Color calcColor(GeoPoint geoPoint) {
        return scene.ambientLight.getIntensity()
                .add(geoPoint.geometry.getEmission());
    }
}
