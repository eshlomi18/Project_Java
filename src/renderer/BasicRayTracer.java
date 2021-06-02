package renderer;

import elements.LightSource;
import geometries.Intersectable;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.*;
import scene.Scene;

import java.util.List;

/**
 * this class inherit from RayTracerBase
 * so it has the parameter scene as the parent class
 * it implements the traceRay method
 * and has a calcColor method
 */
public class BasicRayTracer extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double INITIAL_K = 1.0;

    public BasicRayTracer(Scene scene) {
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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K + 5);
    }

    /***
     *
     * @param intersection
     * @param ray
     * @param level
     * @param k
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = scene.ambientLight.getIntensity()
                .add(intersection.geometry.getEmission());

        color = color.add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }


    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();

        double kkr = k * material.kR;
        if (kkr > MIN_CALC_COLOR_K)
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);

        double kkt = k * material.kT;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    /***
     *
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
        return new Ray(point, v, n);
    }

    /***
     *
     * @param point
     * @param v
     * @param n
     * @return
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        double temp = v.dotProduct(n) * 2;
        Vector r = v.subtract(n.scale(temp));
        return new Ray(point, r, n);
    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    private Color calcLocalEffects(GeoPoint geopoint, Ray inRay, double k) {
        Color color = geopoint.geometry.getEmission();
        Vector v = inRay.getDir();
        Vector n = geopoint.geometry.getNormal((geopoint.point));
        double nv = alignZero(n.dotProduct(v));
        Material material = geopoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;

        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(geopoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                double ktr = transparency(lightSource, l, n, geopoint);
                if (ktr * k > MIN_CALC_COLOR_K) {
                    Color lightIntensity = lightSource.getIntensity(geopoint.point).scale(ktr);
                    color = color.add(calcDiffusive(material.kD, l, n, lightIntensity),
                            calcSpecular(material.kS, l, n, v, nShininess, lightIntensity));
                }
            }
        }
        return color;
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd * Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);
    }

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
        double minusVr = v.dotProduct(r) * -1;
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess));
    }

    private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geopoint.point, lightDirection, n); // refactored ray head move
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        double lightDistance = light.getDistance(geopoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().kT == 0)
                return false;
        }
        return true;
    }


    private double transparency(LightSource light, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        double lightDistance = light.getDistance(geoPoint.point);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return 1.0;
        double ktr = 1.0;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr *= gp.geometry.getMaterial().kT;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }
        }
        return ktr;
    }

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> gp = scene.geometries.findGeoIntersections(ray);
        GeoPoint closestPoint = ray.findClosestGeoPoint(gp);
        return closestPoint;
    }
}


