package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/***
 * interface of non natural light sources
 */
public interface LightSource {
    /***
     *
     * @param p
     * @return intensity of light source in point p
     */
    public Color getIntensity(Point3D p);

    /***
     * @param p
     * @return the vector of the light
     */
    public Vector getL(Point3D p);

}
