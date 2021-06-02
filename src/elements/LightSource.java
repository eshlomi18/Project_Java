package elements;

import geometries.Sphere;
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

    double getDistance(Point3D point);

    /**
     * After refactoring, we add that the lightSource is not only an illuminating point
     * it's also a geometry. So we add a bulb for PointLight abd SpotLight
     * @return the bulb
     */
    Sphere getBulb();
}
