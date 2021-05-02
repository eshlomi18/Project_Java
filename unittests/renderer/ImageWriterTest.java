package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;
import renderer.ImageWriter.*;


public class ImageWriterTest {
    @Test
    void writeImageTest() {
        ImageWriter myImage = new ImageWriter("testBlue", 800, 500);

        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                myImage.writePixel(i, j, new Color(java.awt.Color.BLUE));
            }
        }

        myImage.writeToImage();

    }


}
