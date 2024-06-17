package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.alignZero;

import java.security.cert.CertPathBuilder;


public class Camera implements Cloneable{
    private Point cameraPosition;
    private Vector vRight;
    private Vector vUp;
    private Vector vTo;
    private double height = 0.0;
    private double width = 0.0;
    private double distance = 0.0;
    public static class Builder{
        private final Camera camera = new Camera();

        public CertPathBuilder setViewPlaneSize(int i, int i1) {
        }
    }
    /**
     *
     */
    private Camera() {
    }

    /**
     *
     * @return
     */
    public Builder getBuilder()
    {

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
     *
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