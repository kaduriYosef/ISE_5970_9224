package lighting;

import org.junit.jupiter.api.Test;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * LightSource class represents a light source in the scene.
 */
public interface LightSource {

    /**
     * A method to retrieve the intensity color.
     *
     * @param p the point at which to calculate the intensity
     * @return the intensity color
     */
    public Color getIntensity(Point p);

    /**
     * Retrieves the vector from the specified point.
     *
     * @param p the point from which to retrieve the vector
     * @return the vector retrieved from the specified point
     */
    public Vector getL(Point p);

    /**
     * Computes the distance from the light source to a given point.
     *
     * @param point The point to which the distance is calculated.
     * @return The distance from the light source to the point.
     */
    double getDistance(Point point);

    /**
     * Get the target area of the object
     *
     * @return the target area
     */
    TargetArea getTargetArea();
}