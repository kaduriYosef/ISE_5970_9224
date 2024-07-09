package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import static primitives.Util.alignZero;
import static java.lang.Math.*;

import java.util.List;

/**
 * SimpleRayTracer is a basic implementation of the RayTracerBase class,
 * responsible for tracing rays in a scene and determining the color of the closest intersection point.
 */
public class SimpleRayTracer extends RayTracerBase {

    /**
     * Represents a constant delta value used for numerical approximations or small
     * adjustments. This delta value is set to {@value #DELTA}. It is typically used
     * to control precision or small increments in calculations.
     */
    private static final double DELTA = 0.1;

    /**
     * Maximum recursion level for calculating colors considering transparency or
     * reflection. This constant defines the depth limit to prevent excessive
     * recursion. Adjust this value based on scene complexity and performance
     * considerations.
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    /**
     * Minimum threshold value for the accumulated coefficient of transparency or
     * reflection. If the accumulated coefficient falls below this threshold,
     * recursion for transparency or reflection terminates to avoid negligible
     * contributions to the final color. Adjust this value based on scene specifics
     * and desired precision.
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * The initial coefficient for transparency or reflection calculations.
     * This constant is used as the starting value for the accumulated coefficient in recursive color calculations.
     */
    private static final Double3 INITIAL_K = Double3.ONE;

    /**
     * Checks if a point is unshaded by a specific light source.
     *
     * @param gp    The geometric point representing the intersection.
     * @param light The light source.
     * @param l     The direction vector from the point to the light source.
     * @param n     The normal vector at the intersection point.
     * @return True if the point is unshaded by the light source, false otherwise.
     */
    private Double3 transparency(GeoPoint gp, LightSource light, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return Double3.ONE;
        Double3 ktr = Double3.ONE;
        for (GeoPoint intersectionPoint : intersections) {
            if (alignZero(intersectionPoint.point.distance(gp.point) - light.getDistance(gp.point)) <= 0) {
                ktr = ktr.product(intersectionPoint.geometry.getMaterial().kT);
                if (ktr.equals(Double3.ZERO))
                    break;
            }
        }
        return ktr;
    }


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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }


    /**
     * Calculates the color at a given geometric point considering ambient light,
     * emission from the geometry, and local lighting effects.
     * emission from the geometry, and local lighting effects. This method uses
     * recursive ray tracing to handle transparency and reflection up to a specified
     * recursion level.
     *
     * @param gp  The geometric point at which to calculate the color.
     * @param ray The ray that intersected with the geometry at the geometric point.
     * @return The calculated color at the geometric point, taking into account
     * ambient light, emission, and local lighting effects (diffuse and
     * specular reflections).
     * ambient light, emission, local lighting effects (diffuse and specular
     * reflections), and recursive effects of transparency or reflection.
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }

    /**
     * Calculates the color at a given geometric intersection point considering
     * local lighting effects and potentially global effects such as transparency
     * or reflection.
     *
     * @param ray   The ray that intersected with the geometry at the
     *              intersection point.
     * @param level The current recursion level for handling transparency
     *              or reflection effects.
     * @param k     The accumulated coefficient (e.g., transparency or
     *              reflection coefficient) up to the current recursion level.
     * @return The calculated color at the intersection point, considering local
     * lighting effects and global effects up to the specified recursion level.
     */
    private Color calcColor(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(gp, ray, k);
        return level == 1 ? color : color.add(calcGlobalEffects(gp, ray, level, k));

    }

    /**
     * Calculates the effect of different light sources on a point in the scene
     * according to the Phong model.
     *
     * @param intersection The point on the geometry in the scene.
     * @param ray          The ray from the camera to the intersection.
     * @return The color of the point affected by local light sources.
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        Color color = intersection.geometry.getEmission();
        if (nv == 0)
            return color;
        Material material = intersection.geometry.getMaterial();
        Double3 kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));

            if (nl * nv > 0) {
                Double3 ktr = transparency(intersection, lightSource, l, n);
                if (ktr.product(k).greaterThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(intersection.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl).add(calcSpecular(material, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the combined global effects (such as reflection and refraction)
     * at a given geometric point using recursive ray tracing.
     *
     * @param gp    The geometric point at which to calculate global effects.
     * @param ray   The view direction vector.
     * @param level The current recursion level for handling transparency or reflection effects.
     * @param k     The accumulated coefficient (e.g., reflection coefficient kR or transparency coefficient kT).
     * @return The calculated color representing combined global effects at the geometric point.
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        return calcGlobalEffect(constructRefractedRay(gp, v, n), material.kT, level, k)
                .add(calcGlobalEffect(constructReflectedRay(gp, v, n), material.kR, level, k));
    }
    /**
     * Constructs a refracted ray based on the intersection point and incoming ray.
     * The refraction ray is determined by Snell's law, considering the refractive
     * indices of the materials involved.
     *
     * @param gp The geometric point of intersection.
     * @return The refracted ray originating from the intersection point.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
        }

    /**
     * Constructs a reflected ray based on the intersection point and incoming ray.
     * The reflection ray moves in the direction opposite to the normal vector at
     * the intersection point.
     *
     * @param gp The geometric point of intersection.
     * @return The reflected ray originating from the intersection point.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        double nv = n.dotProduct(v);
        if (nv == 0)
            return null;

        Vector vec = v.subtract(n.scale(2 * nv));
        return new Ray(gp.point, vec, n);
    }

    /**
     * Calculates the global effect (reflection or refraction) for a given ray and coefficient.
     *
     * @param ray   The ray to trace for the global effect.
     * @param level The current recursion level for handling transparency or reflection effects.
     * @param k     The accumulated coefficient (e.g., reflection coefficient kR or transparency coefficient kT).
     * @param kx    The coefficient for the specific effect being calculated (kR for reflection, kT for refraction).
     * @return The calculated color representing the global effect for the given ray and coefficient.
     */
    private Color calcGlobalEffect(Ray ray, Double3 kx, int level, Double3 k) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) {
            return Color.BLACK; // Return no contribution if the combined coefficient is too small
        }
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null)
            return scene.background; // If no intersection found, return background color
        else
            return calcColor(gp, ray, level - 1, kkx).scale(kx); // Recursively calculate color with scaled coefficient

    }

    /**
     * Finds the closest intersection point of a ray with the geometries in the scene.
     *
     * @param ray The ray for which to find the closest intersection.
     * @return The closest intersection point (GeoPoint) of the ray with the geometries,
     * or null if no intersections are found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {

        return ray.findClosestGeoPoint(scene.geometries.findGeoIntersections(ray));
    }

    /**
     * Calculates the diffuse reflection component based on the material properties
     * and the cosine of the angle between the normal vector and the light direction
     * vector.
     *
     * @param material The material of the geometry.
     * @param nl       The dot product of the normal vector and the light direction
     *                 vector.
     * @return The diffuse reflection color component.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.kD.scale(abs(nl));
    }

    /**
     * Calculates the specular reflection component based on the material
     * properties, the normal vector, light direction vector, view direction vector,
     * and the cosine of the angle between the view direction and the reflection
     * direction.
     *
     * @param material The material of the geometry.
     * @param n        The normal vector at the geometric point.
     * @param l        The direction vector from the point to the light source.
     * @param nl       The dot product of the normal vector and the light direction
     *                 vector.
     * @param v        The view direction vector.
     * @return The specular reflection color component.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector reflectVector = (l).subtract(n.scale(nl * 2));
        double max0_var = max(0, v.scale(-1).dotProduct(reflectVector));
        return material.kS.scale(pow(max0_var, material.shininess));
    }

}

