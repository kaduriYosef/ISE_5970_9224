package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;

import static java.awt.Color.*;

/**
 * Test class for ImageWriter.
 * This test writes a test image with a grid pattern where the grid lines are red
 * and the background is blue. The grid lines are drawn every 50 pixels.
 */
public class ImageWriterTest {
    /**
     * Test method for writing an image with a grid pattern.
     * The image will be 500 pixels in height and 800 pixels in width.
     * The grid lines will be red and drawn every 50 pixels, and the rest of the pixels will be blue.
     */
    @Test
    public void writeToImageTest() {
        int height = 500;
        int width = 800;
        ImageWriter imageWriter = new ImageWriter("testImage", width, height);
        final Color red = new Color(RED);
        final Color blue = new Color(GRAY);

        //final Color reh all pixels and set their colors based on their position.
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // Draw red lines every 50 pixels
                if (i % 50 == 0 || j % 50 == 0) {
                    imageWriter.writePixel(i, j, red);
                }
                // Fill the rest with blue
                else {
                    imageWriter.writePixel(i, j, blue);
                }
            }
        }

        // Write the image to file
        imageWriter.writeToImage();
    }
}
