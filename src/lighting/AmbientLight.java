package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in a scene, contributing uniform light across all objects.
 */
public class AmbientLight extends Light {

    /**
     * Constructs an AmbientLight with the given intensity and attenuation factor.
     *
     * @param intensity The color intensity of the ambient light.
     * @param kA        The attenuation factor for the ambient light.
     */
    public AmbientLight(Color intensity, Double3 kA) {
        super(intensity.scale(kA));
    }

    /**
     * Constructor of the class
     *
     * @param intensity - Color of the ambient light
     * @param kA        - double of the ambient light
     */
    public AmbientLight(Color intensity, double kA) {
        super(intensity.scale(kA));
    }

    /**
     * A constant representing no ambient light.
     */
    final public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);


}