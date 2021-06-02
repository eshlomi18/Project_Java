package renderer;

import elements.LightSource;
import geometries.Intersectable;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.*;
import scene.Scene;

import java.util.LinkedList;
import java.util.Iterator;
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
    private List<Ray> constructRefractedRay(Point3D point, Vector v, Vector n) {

        Ray refractedRay = new Ray(point, v, n);
        List<Ray> list = new LinkedList<>();
        list.add(refractedRay);

        Vector v1 = Ver(refractedRay).normalize().scale(0.026); // Multiply the vertical vector in tan (1.5)
        Vector v2 = refractedRay.getDir().crossProduct(v1).scale(0.026); // Vertical vector to v1 and refractedRay

        Point3D start = refractedRay.getP0();

        Vector r1 = refractedRay.getDir().add(v1).add(v2);
        Vector r2 = refractedRay.getDir().add(v1).subtract(v2);
        Vector r3 = refractedRay.getDir().subtract(v1).add(v2);
        Vector r4 = refractedRay.getDir().subtract(v1);
        Vector r5 = refractedRay.getDir().add(v1);
        Vector r6 = refractedRay.getDir().subtract(v1);
        Vector r7 = refractedRay.getDir().add(v2);
        Vector r8 = refractedRay.getDir().subtract(v2);

        Ray refracted1 = new Ray(start, r1);
        Ray refracted2 = new Ray(start, r2);
        Ray refracted3 = new Ray(start, r3);
        Ray refracted4 = new Ray(start, r4);
        Ray refracted5 = new Ray(start, r5);
        Ray refracted6 = new Ray(start, r6);
        Ray refracted7 = new Ray(start, r7);
        Ray refracted8 = new Ray(start, r8);

        list.add(refracted1);
        list.add(refracted2);
        list.add(refracted3);
        list.add(refracted4);
        list.add(refracted5);
        list.add(refracted6);
        list.add(refracted7);
        list.add(refracted8);

        return list;
    }

    /***
     *
     * @param point
     * @param v
     * @param n
     * @return
     */
    private List<Ray> constructReflectedRay(Point3D point, Vector v, Vector n) {
        double temp = v.dotProduct(n) * 2;
        Vector r = v.subtract(n.scale(temp));
        Ray reflectedRay = new Ray(point, r, n);
        List<Ray> list = new LinkedList<>();
        list.add(reflectedRay);

        Vector v1 = Ver(reflectedRay).normalize().scale(0.026); // Multiply the vertical vector in tan (1.5)
        Vector v2 = reflectedRay.getDir().crossProduct(v1).scale(0.026); // Vertical vector to v1 and refractedRay

        Point3D start = reflectedRay.getP0();

        Vector r1 = reflectedRay.getDir().add(v1).add(v2);
        Vector r2 = reflectedRay.getDir().add(v1).subtract(v2);
        Vector r3 = reflectedRay.getDir().subtract(v1).add(v2);
        Vector r4 = reflectedRay.getDir().subtract(v1);
        Vector r5 = reflectedRay.getDir().add(v1);
        Vector r6 = reflectedRay.getDir().subtract(v1);
        Vector r7 = reflectedRay.getDir().add(v2);
        Vector r8 = reflectedRay.getDir().subtract(v2);

        Ray reflected1 = new Ray(start, r1);
        Ray reflected2 = new Ray(start, r2);
        Ray reflected3 = new Ray(start, r3);
        Ray reflected4 = new Ray(start, r4);
        Ray reflected5 = new Ray(start, r5);
        Ray reflected6 = new Ray(start, r6);
        Ray reflected7 = new Ray(start, r7);
        Ray reflected8 = new Ray(start, r8);


        list.add(reflected1);
        list.add(reflected2);
        list.add(reflected3);
        list.add(reflected4);
        list.add(reflected5);
        list.add(reflected6);
        list.add(reflected7);
        list.add(reflected8);


        return list;
    }

    private Color calcGlobalEffect(List<Ray> ray, int level, double kx, double kkx) {
        List<Color> colors = new LinkedList<>();
        for (Ray r : ray) {
            GeoPoint gp = findClosestIntersection(r);
            colors.add((gp == null ? scene.background : calcColor(gp, r, level - 1, kkx)).scale(kx));
        }
        return average(colors);
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

    /***
     * This function return the vertical vector
     * @param ray
     * @return
     */
    private Vector Ver(Ray ray) {
        double x = ray.getDir().getHead().getX();
        double y = ray.getDir().getHead().getY();
        double z = ray.getDir().getHead().getZ();

        Vector v;

        if ((x != 0 && y == 0 && z == 0) || (x != 0 && y != 0 && z == 0)) {
            v = new Vector(0, 0, 1);
        } else if ((x == 0 && y != 0 && z == 0) || (x == 0 && y != 0 && z != 0)) {
            v = new Vector(1, 0, 0);
        } else if ((x == 0 && y == 0 && z != 0) || (x != 0 && y == 0 && z != 0)) {
            v = new Vector(0, 1, 0);
        } else {
            v = new Vector(-y, x, 0);
        }

        return v;
    }


    private Color average(List<Color> list) {
        double r = 0, g = 0, b = 0;

        Iterator<Color> it = list.iterator();
        while (it.hasNext()) {
            Color c = it.next();
            java.awt.Color cc = c.getColor();
            r += cc.getRed();
            g += cc.getGreen();
            b += cc.getBlue();
        }
        r /= list.size();
        g /= list.size();
        b /= list.size();

        return new Color((int) r, (int) g, (int) b);
    }

}


