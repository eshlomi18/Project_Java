package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * interface of non natural light sources
 */
public interface LightSource {
    /***
     *gets the intensity of the light
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p);

    /***
     *gets the direction of the light
     * @param p
     * @return
     */
    public Vector getL(Point3D p);

}
