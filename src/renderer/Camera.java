package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;

public class Camera implements Cloneable {
    private Point location;
    private Vector Vto, Vup, Vright;
    private double width = 0, height = 0, distance = 0;

    public Camera() {
        location = null;
        Vup = null;
        Vto = null;
        Vright = null;
        width = 0;
        height = 0;
        distance = 0;
    }

    public Camera clone() {
        try {
            return (Camera) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Should never happen , since we implement Cloneable ");
        }
    }

    static public Builder getBuilder() {
        return new Builder();
    }

    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }

    public static class Builder {
        private final Camera camera = new Camera();

        private static final String MISSING_RESOURCE_MESSAGE = "Missing rendering data";
        private static final String CAMERA_CLASS_NAME = Camera.class.getSimpleName();

        public Builder setLocation(Point p) {
            return this;
        }

        public Builder setDirection(Vector Vto, Vector Vup) {
            return this;
        }

        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("View plane dimension must be positives");
            camera.width = width;
            camera.height = height;
            return this;
        }

        public Builder setVpDistance(double distance) {
            return this;
        }
    }

}
