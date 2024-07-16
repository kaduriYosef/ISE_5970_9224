package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * PointLight class represents a light source in the scene.
 */
public class PointLight extends Light implements LightSource {

    /**
     * The target area for soft shadows
     */
    protected TargetArea targetArea = new TargetArea();

    /**
     * The position of the light source in 3D space.
     */
    private final Point position;
    /**
     * Constant attenuation factor (kC) affecting the intensity of the light source.
     * This factor is independent of the distance from the light source.
     */
    private double kC = 1; // Constant attenuation
    /**
     * Linear attenuation factor (kL) affecting the intensity of the light source.
     * This factor decreases the intensity linearly with the distance from the light
     * source.
     */
    private double kL = 0; // Linear attenuation
    /**
     * Quadratic attenuation factor (kQ) affecting the intensity of the light
     * source. This factor decreases the intensity quadratically with the distance
     * from the light source.
     */
    private double kQ = 0;

    /**
     * Constructor of the class
     *
     * @param intensity the intensity color
     * @param position  the position
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor for the point light.
     *
     * @param kC the constant attenuation factor to set
     * @return the updated PointLight object
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * Set the attenuation factor for light intensity.
     *
     * @param kL the attenuation factor to set
     * @return the updated PointLight object
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * Set the quadratic attenuation factor for the point light.
     *
     * @param kQ the new quadratic attenuation factor
     * @return the updated PointLight object
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }

    /**
     * Set the shading board for soft shadow
     *
     * @param rib  the rib of the square of the target area
     * @param grid the rows and columns of the grid gor the target area
     * @return the point light object
     */
    public PointLight setTargetArea(double rib, int grid) {
        targetArea = new TargetArea().setRib(rib).setGrid(grid).setCenter(position);
        return this;
    }

    @Override
    public Color getIntensity(Point p) {
        double distance = position.distance(p);
        return intensity.scale(1 / (kC + kL * distance + kQ * distance * distance));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(this.position).normalize();
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

    @Override
    public TargetArea getTargetArea() {
        return targetArea;
    }
}