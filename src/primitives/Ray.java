package primitives;

import java.util.Objects;

/**
*
 *  Represents a ray in three-dimensional space, defined by a starting point and a direction vector.
 */
public class Ray {
    private final Point head;
    private final Vector direction;

    /**
     * Constructs a new ray with the given starting point and direction.
     *
     * @param head      The starting point of the ray.
     * @param direction The direction vector of the ray.
     */

    public Ray(Point head, Vector direction) {
        this.head = head;
        this.direction = direction.normalize();
    }

    /**
     * func that a string representation of this Ray.
     * @return Returns a string representation of this Ray.
     */
    @Override
    public String toString() {
        return "Ray{" +
                "head=" + head +
                ", direction=" + direction +
                '}';
    }

    /**
     * Returns true if the Ray object is equal to a given object.
      * @param  obj the object to compare with the Ray
     * @return true if the Ray object is equal to the given object, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return (obj instanceof Ray other)
                && head.equals(other.head)
                && direction.equals(other.direction);
    }

}
