package renderer;

import geometries.Intersectable;
import static geometries.Intersectable.GeoPoint;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * this class inherit from RayTracerBase
 * so it has the parameter scene as the parent class
 * it implements the traceRay method
 * and has a calcColor method
 */
public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * method suppose to return us
     * the color of a given pixel
     *
     * @param ray
     * @return
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> geoList = scene.geometries.findGeoIntersections(ray);
        if (geoList != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(geoList);
            return calcColor(closestPoint);
        }
        return scene.background;
    }

    /**
     * gives us the intensity of the ambient light
     *
     * @param point
     * @return
     */
    private Color calcColor(GeoPoint point) {
        Color result = point.geometry.getEmission();

        return result.add(scene.ambientLight.getIntensity());
    }
}
