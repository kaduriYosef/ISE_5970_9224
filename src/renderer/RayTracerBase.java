package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The RayTracerBase class serves as an abstract base class for ray tracing in a given scene.
 */
public abstract class RayTracerBase {

    /**
     * The scene to be ray traced
     */
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene The scene to be ray traced.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * Traces a ray in the scene and returns the color resulting from this ray.
     *
     * @param ray The ray to be traced.
     * @return The color resulting from tracing the ray.
     */
    public abstract Color traceRay(Ray ray);
}
