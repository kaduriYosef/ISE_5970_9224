package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

import java.security.cert.CertPathBuilder;


public class Camera implements Cloneable {
    private Point cameraPosition;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

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
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        public Camera build() throws CloneNotSupportedException {
            camera.vRight = camera.vTo.crossProduct(camera.vUp);
            if (camera.cameraPosition == null)
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "cameraPosition");
            if (camera.vUp == null)
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "vUp");
            if (camera.vTo == null)
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "vTo");
            if (isZero(camera.height))
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "height");
            if (isZero(camera.width))
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "width");
            if (isZero(camera.distance))
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "distance");
            if (camera.rayTracer == null)
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "rayTracer");
            if (camera.imageWriter == null)
                throw new MissingResourceException(MISSING_RESOURCE_MESSAGE, CAMERA_CLASS_NAME, "imageWriter");
            if (camera.height < 0)
                throw new IllegalArgumentException("The height value is invalid");
            if (camera.width < 0)
                throw new IllegalArgumentException("The width value is invalid");
            if (camera.distance < 0)
                throw new IllegalArgumentException("The distance value is invalid");

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
        // Image center
        Point pointCenter = cameraPosition.add(vTo.scale(distance));

        // Calculate the size of each pixel
        double Rx = width / nX;
        double Ry = height / nY;

        // Calculation of displacement according to i j
        double Xj = (j - (double) (nX - 1) / 2) * Rx;
        double Yi = -(i - (double) (nY - 1) / 2) * Ry;

        // Calculating the pixel's position according to i j and gives a point
        Point Pij = pointCenter;
        if (!isZero(Xj)) {
            Pij = Pij.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            Pij = Pij.add(vUp.scale(Yi));
        }

        // Calculation of the vector from the point to the screen according to i j
        Vector viewIJ = Pij.subtract(cameraPosition);

        // Returns the ray from the point by i j
        return new Ray(cameraPosition, viewIJ);
    }

    public void renderImage() {
        for (int row = 0; row < imageWriter.getNy(); ++row)
            for (int col = 0; col < imageWriter.getNx(); ++col) {
                castRay(imageWriter.getNx(), imageWriter.getNy(), col, row);
            }
    }

    private void castRay(int Nx, int Ny, int column, int row) {
        imageWriter.writePixel(column, row, rayTracer.traceRay(constructRay(Nx, Ny, column, row)));
    }

    public void printGrid(int interval, Color color) throws MissingResourceException {
        if (imageWriter != null) {
            for (int i = 0; i < imageWriter.getNx(); i++) {
                for (int j = 0; j < imageWriter.getNy(); j++) {
                    if (i % interval == 0 || j % interval == 0) {
                        imageWriter.writePixel(i, j, color);
                    }
                }
            }
        } else {
            throw new MissingResourceException("ImageWriter not initialized", "ImageWriter", "Missing");
        }
    }

    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter not initialized.", "Camera", "Missing");
        }
        imageWriter.writeToImage();
    }
}