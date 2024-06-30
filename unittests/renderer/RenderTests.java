package renderer;

import static java.awt.Color.*;

import org.junit.jupiter.api.Test;

import geometries.*;
import lighting.AmbientLight;
import primitives.*;
import primitives.Color;
import scene.Scene;
import scene.SceneBuilder;


/**
 * Test rendering a basic image.
 * This class contains integration tests for rendering scenes using the Camera and various geometries.
 * It includes tests for rendering scenes directly and from an XML file.
 *
 * @author Dan
 */
public class RenderTests {
    /**
     * The scene used for the tests.
     */
    private final Scene scene = new Scene("Test scene");

    /**
     * The camera builder used for the tests.
     */
    private final Camera.Builder camera = Camera.getBuilder()
            .setRayTracer(new SimpleRayTracer(scene))
            .setLocation(new Vector(0, 0, -1)).setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setVpDistance(100)
            .setVpSize(500, 500);

    /**
     * Produces a scene with a basic 3D model and renders it into a PNG image with a grid.
     * <p>
     * This test sets up a scene with a sphere and three triangles, adds ambient light,
     * and sets a background color. It then renders the image, adds a grid, and writes
     * the final image to a file.
     *
     * @throws CloneNotSupportedException if the camera cannot be cloned
     */
    @Test
    public void renderTwoColorTest() throws CloneNotSupportedException {
        scene.geometries.add(new Sphere(new Point(0, 0, -100), 50d),
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100)), // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100)), // down left
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))); // down right
        scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), Double3.ONE))
                .setBackground(new Color(75, 127, 90));

        Camera cam = camera
                .setImageWriter(new ImageWriter("base render test", 1000, 1000))
                .build();
        cam.renderImage();
        cam.printGrid(100, new Color(YELLOW));
        cam.writeToImage();
    }

    /**
     * Produce a scene with basic 3D model - including individual lights of the
     * bodies and render it into a png image with a grid
     */
    @Test
    public void renderMultiColorTest() throws CloneNotSupportedException {
        scene.geometries.add( // center
                new Sphere(new Point(0, 0, -100), 50),
                // up left
                new Triangle(new Point(-100, 0, -100), new Point(0, 100, -100), new Point(-100, 100, -100))
                        .setEmission(new Color(GREEN)),
                // down left
                new Triangle(new Point(-100, 0, -100), new Point(0, -100, -100), new Point(-100, -100, -100))
                        .setEmission(new Color(RED)),
                // down right
                new Triangle(new Point(100, 0, -100), new Point(0, -100, -100), new Point(100, -100, -100))
                        .setEmission(new Color(BLUE)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.2, 0.2, 0.2))); //

        Camera cam = camera
                .setImageWriter(new ImageWriter("color render test", 1000, 1000))
                .build();
        cam.renderImage();
        cam.printGrid(100, new Color(WHITE));
        cam.writeToImage();
    }

    /**
     * Produces a scene from an XML file and renders it into a PNG image with a grid.
     * <p>
     * This test builds a scene from an XML file, sets up the camera, and renders the image.
     * It then adds a grid and writes the final image to a file.
     *
     * @throws CloneNotSupportedException if the camera cannot be cloned
     */
    @Test
    public void basicRenderXml() throws CloneNotSupportedException {
        /*
         * Directory path for the image file generation - relative to the user
         * directory
         */
        final String FOLDER_PATH = System.getProperty("user.dir") + "/renderTestTwoColors";

        // Build scene from XML
        Scene scene = SceneBuilder.buildSceneFromXml(FOLDER_PATH + ".xml");

        Camera cam = camera
                .setRayTracer(new SimpleRayTracer(scene))
                .setImageWriter(new ImageWriter("xml render test", 1000, 1000))
                .build();
        cam.renderImage();
        cam.printGrid(100, new Color(java.awt.Color.YELLOW));
        cam.writeToImage();
    }


}
