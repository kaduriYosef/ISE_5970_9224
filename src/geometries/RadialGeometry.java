package geometries;

/**
 * This abstract class represents a base class for geometries
 * defined using a radius value.
 */
public abstract class RadialGeometry extends Geometry {

    /**
     * The radius of the radial geometry.
     */
    protected double radius;

    /**
     * Constructor that initializes the radius of the radial geometry.
     *
     * @param radius - The value of the radius (positive value expected).
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }

    @Override
    public void calcBoundingBox() {
    }
}
