package renderer;

import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

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
     * Checks if a point is unshaded by a specific light source.
     *
     * @param gp    The geometric point representing the intersection.
     * @param light The light source.
     * @param l     The direction vector from the point to the light source.
     * @param n     The normal vector at the intersection point.
     * @param nl    The dot product between the normal vector and the direction vector to the light source.
     * @return True if the point is unshaded by the light source, false otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);//(-1)

        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;
        // Calculate squared distance to light source
        double distanceToLightSquared = light.getDistance(gp.point);

        // Check if any intersection is closer to the point than the light source
        for (GeoPoint geoPoint : intersections) {
            double distanceToIntersectionSquared = gp.point.distance(geoPoint.point);
            if (distanceToIntersectionSquared < distanceToLightSquared) {
                return false; // Point is shaded
            }

        }
        return true;
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
        public Color traceRay (Ray ray){
            List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
            if (points == null) {
                return scene.background; // Return the background color if no intersections are found
            }
            return calcColor(ray.findClosestGeoPoint(points), ray); // Calculate the color at the closest intersection point
        }


        /**
         * private Color calcColor(GeoPoint point) {
         * return scene.ambientLight.getIntensity();
         * }
         */

        private Color calcColor (GeoPoint geoPoint, Ray ray){
            return scene.ambientLight.getIntensity().add(geoPoint.geometry.getEmission())
                    .add(calcLocalEffects(geoPoint, ray));
        }


        /**
         * Calculates the effect of different light sources on a point in the scene
         * according to the Phong model.
         *
         * @param intersection The point on the geometry in the scene.
         * @param ray          The ray from the camera to the intersection.
         * @return The color of the point affected by local light sources.
         */
        private Color calcLocalEffects (GeoPoint intersection, Ray ray){
            Vector v = ray.getDir();
            Vector n = intersection.geometry.getNormal(intersection.point);
            double nv = alignZero(n.dotProduct(v));
            if (nv == 0)
                return Color.BLACK;

            int nShininess = intersection.geometry.getMaterial().shininess;

            Double3 kd = intersection.geometry.getMaterial().kD, ks = intersection.geometry.getMaterial().kS;

            Color color = Color.BLACK;
            for (LightSource lightSource : scene.lights) {
                Vector l = lightSource.getL(intersection.point);
                double nl = alignZero(n.dotProduct(l));

                if ((nl * nv > 0) && unshaded(intersection, lightSource, l, n, nl)) {
                    Color lightIntensity = lightSource.getIntensity(intersection.point);
                    color = color.add(calcDiffuse(kd, nl, lightIntensity),
                            calcSpecular(ks, l, n, nl, v, nShininess, lightIntensity));
                }
            }
            return color;
        }

        /**
         * Calculates the diffuse component of light reflection.
         *
         * @param kd             The diffuse reflection coefficient. חדות
         * @param nl             The dot product between the normal vector and the light
         *                       vector.
         * @param lightIntensity The intensity of the light source.
         * @return The color contribution from the diffuse reflection.
         */
        private Color calcDiffuse (Double3 kd,double nl, Color lightIntensity){
            return lightIntensity.scale(kd.scale(Math.abs(nl)));
        }

        /**
         * Calculates the specular component of light reflection.
         *
         * @param ks             The specular reflection coefficient.פיזור
         * @param l              The light vector.
         * @param n              The normal vector.
         * @param nl             The dot product between the normal vector and the light
         *                       vector.
         * @param v              The view vector.
         * @param nShininess     The shininess coefficient.
         * @param lightIntensity The intensity of the light source.
         * @return The color contribution from the specular reflection.
         */
        private Color calcSpecular (Double3 ks, Vector l, Vector n,double nl, Vector v,int nShininess,
        Color lightIntensity){
            Vector r = l.add(n.scale(-2 * nl)); // nl must not be zero!
            double minusVR = -alignZero(r.dotProduct(v));
            if (minusVR <= 0) {
                return new primitives.Color(Color.BLACK.getColor()); // View from direction opposite to r vector
            }
            return lightIntensity.scale(ks.scale(Math.pow(minusVR, nShininess)));
        }
    }

