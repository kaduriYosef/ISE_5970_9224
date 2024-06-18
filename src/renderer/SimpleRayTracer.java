package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

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
        List<Point> points = scene.geometries.findIntersections(ray);
        if (points == null) {
            return scene.background; // Return the background color if no intersections are found
        }
        return calcColor(ray.findClosestPoint(points)); // Calculate the color at the closest intersection point
    }

    /**
     * Calculates the color at a given intersection point.
     *
     * @param point The intersection point.
     * @return The color at the intersection point.
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity(); // Return the ambient light intensity as the color
    }
}
