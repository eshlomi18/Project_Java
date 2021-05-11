package elements;

import primitives.Color;

/***
 * class that inherit from abstract class Light
 * default sets black ambient light
 * otherwise sets a color multiplied by a number
 * which defines a nuance of the color
 */
public class AmbientLight extends Light {

    /***
     * default call the parent ctor with black color
     */
    public AmbientLight() {
        super(Color.BLACK);
    }

    /***
     *  call ctor by sending a color times a number
     *  which defines a nuance
     * @param Ia
     * @param Ka
     */
    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }


}
