package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in a scene, contributing uniform light across all objects.
 */
public class AmbientLight {
    /**
     * the intensity of the color
     */
    final private Color intensity;

    /**
     * Constructs an AmbientLight with the given intensity and attenuation factor.
     *
     * @param intensity The color intensity of the ambient light.
     * @param kA        The attenuation factor for the ambient light.
     */
    public AmbientLight(Color intensity, Double3 kA) {
        this.intensity = intensity.scale(kA);
    }

    /**
     * Constructs an AmbientLight with the given intensity and attenuation factor.
     *
     * @param intensity The color intensity of the ambient light.
     * @param kA        The attenuation factor for the ambient light as a double.
     */
    AmbientLight(Color intensity, double kA) {
        this.intensity = intensity.scale(kA);
    }

    /**
     * A constant representing no ambient light.
     */
    final public static AmbientLight NONE = new AmbientLight(Color.BLACK, 0);

    /**
     * Returns the intensity of the ambient light.
     *
     * @return The color intensity of the ambient light.
     */
    public Color getIntensity() {
        return this.intensity;
    }
}
