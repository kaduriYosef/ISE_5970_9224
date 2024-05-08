package primitives;

/**
 * This class represents a vector in a three-dimensional space.
 * It extends the {@link Point} class, which means it has x, y, and z coordinates.
 */
public class Vector extends Point {

    /**
     * Returns true if the Vector object is equal to a given object.
     *
     * @param o the object to compare with the Vector
     * @return true if the Vector object is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof Vector && super.equals(o);

    }

    /**
     * Returns a string representation of this vector.
     *
     * @return a string representation of this vector
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Constructs a new vector from a {@link Double3} object.
     *
     * @param d the {@link Double3} object that contains the x, y, and z coordinates of the vector
     */
    public Vector(Double3 d) {
        super(d);
    }

    /**
     * Calculates the length of this vector squared.
     *
     * @return the length of this vector squared
     */
    public Double lengthSquared() {
        return this.distanceSquared(this);
    }

    /**
     * Calculates the length of this vector.
     *
     * @return the length of this vector
     */
    public Double length() {
        return this.distance(this);
    }

    /**
     * Adds a vector to this vector and returns the result as a new vector.
     *
     * @param v the vector to add to this vector
     * @return the sum of this vector and the given vector as a new vector
     */
    @Override
    public Vector add(Vector v) {
        super.add(v);
        return this;
    }

    /**
     * Scales this vector by a given factor and returns the result as a new vector.
     *
     * @param d the scaling factor
     * @return the scaled vector as a new vector
     */
    public Vector scale(Double d) {
        return new Vector(new Double3(xyz.d1 * d, xyz.d2 * d, xyz.d3 * d));
    }

    /**
     * Calculates the dot product of this vector and another vector.
     *
     * @param v the other vector
     * @return the dot product of this vector and the given vector
     */
    public double dotProduct(Vector v) {
        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }

    /**
     * Calculates the cross product of this vector and another vector.
     *
     * @param v the other vector
     * @return the cross product of this vector and the given vector as a new vector
     */
    public Vector crossProduct(Vector v) {
        return new Vector(new Double3(xyz.d2 * v.xyz.d3 - v.xyz.d2 * xyz.d3, xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3,
                xyz.d1 * v.xyz.d2 - xyz.d3 * v.xyz.d1));
    }

    /**
     * Normalizes this vector and returns the result as a new vector.
     *
     * @return the normalized vector as a new vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.reduce(length()));
    }
}
