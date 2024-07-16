package lighting;

import primitives.*;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * TargetArea represents a target area for super sampling
 */
public class TargetArea {
    /**
     * The rib of the square of the target area
     */
    private double rib = 0;

    /**
     * The center point of the target
     */
    private Point center = null;

    /**
     * The amount of rows and columns
     */
    private int grid = 0;

    /**
     * Set the center of the target area
     *
     * @param center for setting the center point
     * @return the TargetArea itself
     */
    public TargetArea setCenter(Point center) {
        this.center = center;
        return this;
    }

    /**
     * Set the size of the rib of the square
     *
     * @param rib the rib of the square
     * @return the TargetArea itself
     */
    public TargetArea setRib(double rib) {
        this.rib = rib;
        return this;
    }

    /**
     * Set the rows and columns of the grid
     *
     * @param grid the rows and columns
     * @return the TargetArea itself
     */
    public TargetArea setGrid(int grid) {
        this.grid = grid;
        return this;
    }

    /**
     * Scatter points in the form of jittered include the center point
     *
     * @param vTo the vector from the center ray to the center point of the target area that is orthogonal to the target area
     * @return List of all the scattered points include the center points
     */
    public List<Point> scatterPoints(Vector vTo) {
        if (isZero(grid) || isZero(rib))
            return null;
        Vector vX = new Vector(0, -vTo.getZ(), vTo.getY()).normalize(); //(x,y,z) orthogonal (0,-z,y)
        Vector vY = vX.crossProduct(vTo).normalize();
        double ribOverGrid = rib / grid;
        int gridOverTwo = grid / 2;
        List<Point> pointList = new LinkedList<>();
        for (int i = -gridOverTwo; i <= gridOverTwo; i++)
            for (int j = -gridOverTwo; j <= gridOverTwo; j++) {
                Point p = this.center;
                double rand;
                if (j != 0) {
                    rand = Math.random() * ribOverGrid;
                    p = p.add(vY.scale(j * ribOverGrid + rand));
                }
                if (i != 0) {
                    rand = Math.random() * ribOverGrid;
                    p = p.add(vX.scale(i * ribOverGrid + rand));
                }
                pointList.add(p);
            }
        return pointList;
    }

}