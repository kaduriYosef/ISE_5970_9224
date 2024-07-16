package renderer;

import static java.awt.Color.*;

import geometries.Polygon;

import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 */
public class ReflectionRefractionTests {
    /**
     * Scene for the tests
     */
    private final Scene scene = new Scene("Test scene");
    /**
     * Camera builder for the tests with triangles
     */
    private final Camera.Builder cameraBuilder = Camera.getBuilder()
            .setDirection(new Vector(0, 0, -1), new Vector(0, 1, 0))
            .setRayTracer(new SimpleRayTracer(scene));

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheres() throws CloneNotSupportedException {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100).setKT(0.3)),
                new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100)));
        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));

        Camera cam = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheresOnMirrors() throws CloneNotSupportedException {
        scene.geometries.add(
                new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 50, 100))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)
                                .setKT(new Double3(0.5, 0, 0))),
                new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKD(0.25).setKS(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000))
                        .setEmission(new Color(20, 20, 20))
                        .setMaterial(new Material().setKR(new Double3(0.5, 0, 0.4))));
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));
        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4))
                .setKl(0.00001).setKq(0.000005));

        Camera cam = cameraBuilder.setLocation(new Point(0, 0, 10000)).setVpDistance(10000)
                .setVpSize(2500, 2500)
                .setImageWriter(new ImageWriter("reflectionTwoSpheresMirrored", 500, 500))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }

    /**
     * Produce a picture of  two triangles lighted by a spotlight with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() throws CloneNotSupportedException {
        scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)),
                new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));
        scene.lights.add(
                new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1))
                        .setKl(4E-5).setKq(2E-7));

        Camera cam = cameraBuilder.setLocation(new Point(0, 0, 1000)).setVpDistance(1000)
                .setVpSize(200, 200)
                .setImageWriter(new ImageWriter("refractionShadow", 600, 600))
                .build();
        cam.renderImage();
        cam.writeToImage();
    }


    /**
     * Test case to verify the rendering of a scene involving a table, two spheres (representing balls),
     * and multiple spotlights. The test focuses on the interaction of light reflection, refraction,
     * and ambient lighting on the objects, especially how the spotlights highlight the balls and their
     * reflection on the table.
     */
    @Test
    public void tableSpotlightsBallReflection() throws CloneNotSupportedException {
        // ----- Material Definitions -----
        /*
         * Material for the table surface.
         * - KD (Diffuse coefficient): 0.8  (Highly diffuse, scatters light in many directions)
         * - KS (Specular coefficient): 0.2 (Moderately reflective, forms a shiny spot)
         * - Shininess: 30                (Controls the size and sharpness of the specular highlight)
         * - KR (Reflection coefficient): 0.5 (Reflects 50% of light)
         */
        Material tableMaterial = new Material()
                .setKD(0.8)
                .setKS(0.2)
                .setShininess(30)
                .setKR(new Double3(0.5));

        /*
         * Material for the first ball.
         * - KD: 0.5
         * - KS: 0.5
         * - Shininess: 100 (Very high gloss)
         * - KR: 0 (No reflection)
         * - KT: 0.3 (30% of light is transmitted through the ball - some transparency)
         */
        Material ballMaterial1 = new Material()
                .setKD(0.5)
                .setKS(0.5)
                .setShininess(50)
                .setKR(new Double3(0))
                .setKT(0.9);

        /*
         * Material for the second ball, similar to the first but less transparent.
         * - KD: 0.5
         * - KS: 0.5
         * - Shininess: 50 (Moderate gloss)
         * - KR: 0
         * - KT: 0.1 (10% of light transmitted)
         */
        Material ballMaterial2 = new Material()
                .setKD(0.5)
                .setKS(0.5)
                .setShininess(50)
                .setKR(new Double3(0))
                .setKT(0.9);

        // ----- Scene Construction -----
        /*
         * Add geometric objects to the scene:
         * - A flat polygon representing the table
         * - Two spheres representing balls with distinct materials and emission colors
         */
        scene.geometries.add(
                new Polygon(
                        new Point(-100, -20, 0),
                        new Point(100, -20, 0),
                        new Point(100, -20, -100),
                        new Point(-100, -20, -100))
                        .setMaterial(tableMaterial),
                new Sphere(new Point(0, -10, -30), 10) // larger ball
                        .setEmission(new Color(0, 191, 255))  // Cyan emission
                        .setMaterial(ballMaterial1),
                new Sphere(new Point(0, 5, -30), 5) // Smaller ball
                        .setEmission(new Color(0, 191, 255)) // Cyan emission
                        .setMaterial(ballMaterial2)
        );

        // ----- Lighting Setup -----
        /*
         * Add spotlights with different colors and positions to create interesting lighting effects.
         *  - Kl, Kq: attenuation factors controlling how light intensity diminishes with distance
         */
        scene.lights.add(
                new SpotLight(new Color(255, 153, 51), new Point(-100, 50, 0), new Vector(100, -70, -50))
                        .setKl(0.0001)
                        .setKq(0.000005)
        );
        scene.lights.add(
                new SpotLight(new Color(51, 255, 153), new Point(100, 50, 0), new Vector(-100, -70, -50))
                        .setKl(0.0001)
                        .setKq(0.000005)
        );
        scene.lights.add(
                new SpotLight(new Color(153, 51, 255), new Point(0, 75, 100), new Vector(0, -95, -150))
                        .setKl(0.0001)
                        .setKq(0.000005)
        );





        // ----- Camera Setup -----
        /*
         * Configure the camera position and image output settings.
         */
        Camera camera = cameraBuilder
                .setLocation(new Point(0, 20, 50))
                .setVpDistance(80)
                .setVpSize(150, 150)
                .setImageWriter(new ImageWriter("table_spotlights_ball_reflection", 600, 600))
                .build();

        // ----- Rendering -----
        camera.renderImage();
        camera.writeToImage();
    }


}