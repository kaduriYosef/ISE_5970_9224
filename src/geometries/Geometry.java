package geometries;

import primitives.Point;
import primitives.Vector;
import primitives.Color;

/**
 * This interface defines a contract for geometric objects.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
  //  private Material material = new Material();


    /**
     * Retrieves the emission color.// זה תאורה עצמית
     *
     * @return         	the emission color
     */
    public Color getEmission() {
        return emission;
    }

//    /**
//     * Get the material of this object.
//     *
//     * @return the material of this object
//     */
//    public Material getMaterial() {
//        return material;
//    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param  emission  the color to set as the emission
     * @return           the updated Geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

//    /**
//     * Sets the material of the geometry.
//     *
//     * @param  material  the material to set
//     * @return          the updated geometry with the new material
//     */
//    public Geometry setMaterial(Material material) {
//        this.material = material;
//        return this;
//    }
    /**
     * This method is mandatory for any class implementing the Geometry interface.
     * It requires calculating and returning the normal vector to the surface of the geometry
     * at a specific point (p).
     *
     * @param p - The point on the geometry where the normal vector is requested.
     * @return The normal vector to the surface of the geometry at point p.
     */
    public abstract Vector getNormal(Point p);

}
