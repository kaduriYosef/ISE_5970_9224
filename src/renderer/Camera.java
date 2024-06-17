package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;

import java.security.cert.CertPathBuilder;


public class Camera implements Cloneable {
    private Point cameraPosition;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;

    public static class Builder {
        private final Camera camera = new Camera();
        private static final String MISSING_RESOURCE_MESSAGE = "Missing rendering data";
        private static final String CAMERA_CLASS_NAME = Camera.class.getSimpleName();

        public Builder setLocation(Point p) {

            camera.cameraPosition = p;
            return this;
        }

        public Builder setDirection(Vector Vto, Vector Vup) {
            if (Vto == null || Vup == null) {
                throw new IllegalArgumentException("Direction Vto, Vup should not be null");
            }
            camera.vTo = Vto.normalize();
            camera.vUp = Vup.normalize();
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
            if (distance <= 0){
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }
        public Camera build() throws CloneNotSupportedException {
            if (camera.cameraPosition == null) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"cameraPosition");
            }
            if (camera.vRight == null) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"vRight");

            }
            if (camera.vUp == null) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"vUp");

            }
            if (camera.vTo == null) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"vTo");

            }
            if (camera.distance <= 0) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"distance");
            }
            if (camera.height <= 0) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"height");
            }
            if (camera.width <= 0) {
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME,"width");
            }
            return (Camera) camera.clone();
        }

    }

    /**
     *
     */
    private Camera() {
    }

    /**
     * @return
     */
    public static Builder getBuilder() {
        return new Builder();
    }


    public Point getCameraPosition() {
        return cameraPosition;
    }

    public Vector getvRight() {
        return vRight;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    /**
     * @param nX
     * @param nY
     * @param j
     * @param i
     * @return
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }
}