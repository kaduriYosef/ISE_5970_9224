package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class represents a camera in 3D space with a specific position and orientation.
 * It is used to define the view of a scene for rendering purposes.
 * <p>
 * This class includes the position of the camera, orientation vectors, and parameters
 * for the view plane and the rendering mechanism.
 */
public class Camera implements Cloneable {
    /**
     * Represents the position of the camera in the 3D scene.
     */
    private Point cameraPosition;

    /**
     * Represents the right direction vector of the camera, typically pointing to the right in the camera's local coordinate system.
     */
    private Vector vRight;

    /**
     * Represents the up direction vector of the camera, typically pointing upwards in the camera's local coordinate system.
     */
    private Vector vUp;

    /**
     * Represents the forward direction vector of the camera, typically pointing in the direction the camera is facing.
     */
    private Vector vTo;

    /**
     * Represents the height of the view plane or the screen that the camera is looking at.
     */
    private double height = 0.0;

    /**
     * Represents the width of the view plane or the screen that the camera is looking at.
     */
    private double width = 0.0;

    /**
     * Represents the distance from the camera to the view plane.
     */
    private double distance = 0.0;

    /**
     * Responsible for writing the image to an output file. It handles the creation of the image file from the pixel data.
     */
    private ImageWriter imageWriter;

    /**
     * Responsible for tracing rays from the camera through the pixels on the view plane and determining the color of each pixel.
     */
    private RayTracerBase rayTracer;

    /**
     * Represents the center point of the view plane in the 3D scene, relative to the camera's position and orientation.
     */
    private Point pCenter;

    /**
     * Pixel manager for supporting:
     * <ul>
     * <li>multi-threading</li>
     * <li>debug print of progress percentage in Console window/tab</li>
     * </ul>
     */
    private PixelManager pixelManager;

    /**
     * Number of threads to use for rendering
     */
    private int threadsCount = 0;

    /**
     * Builder class for constructing a Camera instance.
     */
    public static class Builder {
        private final Camera camera = new Camera();
        private static final String MISSING_RESOURCE_MESSAGE = "Missing rendering data";
        private static final String CAMERA_CLASS_NAME = Camera.class.getSimpleName();

        /**
         * Sets the location of the camera.
         *
         * @param p Point representing the camera position.
         * @return The current Builder instance.
         */
        public Builder setLocation(Point p) {
            camera.cameraPosition = p;
            return this;
        }

        /**
         * Sets the direction vectors of the camera.
         *
         * @param Vto Vector pointing to the viewing direction.
         * @param Vup Vector pointing upwards.
         * @return The current Builder instance.
         */
        public Builder setDirection(Vector Vto, Vector Vup) {
            if (Vto == null || Vup == null) {
                throw new IllegalArgumentException("Direction Vto, Vup should not be null");
            }
            camera.vTo = Vto.normalize();
            camera.vUp = Vup.normalize();
            return this;
        }

        /**
         * Sets the size of the view plane.
         *
         * @param width  Width of the view plane.
         * @param height Height of the view plane.
         * @return The current Builder instance.
         */
        public Builder setVpSize(double width, double height) {
            if (width <= 0 || height <= 0)
                throw new IllegalArgumentException("View plane dimension must be positives");
            camera.width = width;
            camera.height = height;
            return this;
        }

        /**
         * Sets the distance from the camera to the view plane.
         *
         * @param distance Distance to the view plane.
         * @return The current Builder instance.
         */
        public Builder setVpDistance(double distance) {
            if (distance <= 0) {
                throw new IllegalArgumentException("Distance must be positive");
            }
            camera.distance = distance;
            return this;
        }

        /**
         * Sets the RayTracerBase instance for the camera.
         *
         * @param rayTracer RayTracerBase instance.
         * @return The current Builder instance.
         */
        public Builder setRayTracer(RayTracerBase rayTracer) {
            camera.rayTracer = rayTracer;
            return this;
        }

        /**
         * Sets the ImageWriter instance for the camera.
         *
         * @param imageWriter ImageWriter instance.
         * @return The current Builder instance.
         */
        public Builder setImageWriter(ImageWriter imageWriter) {
            camera.imageWriter = imageWriter;
            return this;
        }

        /**
         * Set the number of threads to use for rendering
         *
         * @param threadsCount the number of threads to use for rendering
         * @return the camera builder
         */
        public Builder setMultithreading(int threadsCount) {
            if (camera.threadsCount < 0)
                throw new MissingResourceException("threads count can't be smaller than 0", "Camera", "");
            camera.threadsCount = threadsCount;
            return this;
        }

        /**
         * Builds the Camera instance.
         *
         * @return A new Camera instance.
         * @throws CloneNotSupportedException If the Camera instance cannot be cloned.
         * @throws MissingResourceException   If any required resource is missing.
         */
        public Camera build() throws CloneNotSupportedException {

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

            camera.vRight = camera.vTo.crossProduct(camera.vUp);
            camera.pCenter = camera.cameraPosition.add(camera.vTo.scale(camera.distance));

            return (Camera) camera.clone();
        }
    }

    /**
     * Private constructor for Camera.
     */
    private Camera() {
    }

    /**
     * Returns a new Builder instance.
     *
     * @return A new Builder instance.
     */
    public static Builder getBuilder() {
        return new Builder();
    }

    /**
     * Gets the position of the camera in 3D space.
     *
     * @return the camera position as a {@code Point}
     */
    public Point getCameraPosition() {
        return cameraPosition;
    }

    /**
     * Gets the right direction vector from the camera's perspective.
     *
     * @return the right direction vector as a {@code Vector}
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Gets the up direction vector from the camera's perspective.
     *
     * @return the up direction vector as a {@code Vector}
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Gets the forward direction vector from the camera's perspective.
     *
     * @return the forward direction vector as a {@code Vector}
     */
    public Vector getvTo() {
        return vTo;
    }

    /**
     * Gets the height of the view plane.
     *
     * @return the height of the view plane as a {@code double}
     */
    public double getHeight() {
        return height;
    }

    /**
     * Gets the width of the view plane.
     *
     * @return the width of the view plane as a {@code double}
     */
    public double getWidth() {
        return width;
    }

    /**
     * Gets the distance from the camera to the view plane.
     *
     * @return the distance from the camera to the view plane as a {@code double}
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Constructs a ray through a specific pixel.
     *
     * @param nX Number of pixels in the x-axis.
     * @param nY Number of pixels in the y-axis.
     * @param j  Pixel column.
     * @param i  Pixel row.
     * @return A Ray from the camera through the specified pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        // Image center
        Point pointCenter = pCenter;

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

    /**
     * Renders the image by casting rays through each pixel.
     */
    public Camera renderImage() {
        final int nx = imageWriter.getNx(), ny = imageWriter.getNy();
        pixelManager = new PixelManager(ny, nx);

        if (threadsCount == 0)
            for (int i = 0; i < ny; i++) {
                for (int j = 0; j < nx; j++) {
                    castRay(nx, ny, j, i);
                }
            }
        else { // option 2
            var threads = new LinkedList<Thread>(); // list of threads
            while (threadsCount-- > 0) // add appropriate number of threads
                threads.add(new Thread(() -> { // add a thread with its code
                    PixelManager.Pixel pixel; // current pixel(row,col)
                    // allocate pixel(row,col) in loop until there are no more pixels
                    while ((pixel = pixelManager.nextPixel()) != null)
                        // cast ray through pixel (and color it – inside castRay)
                        castRay(nx, ny, pixel.col(), pixel.row());
                }));
            // start all the threads
            for (var thread : threads) thread.start();
            // wait until all the threads have finished
            try {
                for (var thread : threads) thread.join();
            } catch (InterruptedException ignore) {
            }
        }

        return this;
    }


    /**
     * Casts a ray through a specific pixel and writes the resulting color.
     *
     * @param Nx     Number of pixels in the x-axis.
     * @param Ny     Number of pixels in the y-axis.
     * @param column Pixel column.
     * @param row    Pixel row.
     */
    private void castRay(int Nx, int Ny, int column, int row) {
        imageWriter.writePixel(column, row, rayTracer.traceRay(constructRay(Nx, Ny, column, row)));
    }

    /**
     * Prints a grid on the image with a specific interval and color.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @throws MissingResourceException If the ImageWriter is not initialized.
     */
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

    /**
     * Writes the rendered image to a file.
     *
     * @throws MissingResourceException If the ImageWriter is not initialized.
     */
    public void writeToImage() {
        if (imageWriter == null) {
            throw new MissingResourceException("ImageWriter not initialized.", "Camera", "Missing");
        }
        imageWriter.writeToImage();
    }
}
