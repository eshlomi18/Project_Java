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
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
            return calcColor(closestPoint, ray);
        }
        return scene.background;
    }

    /**
     * @param point
     * @return
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return scene.ambientLight.getIntensity()
                .add(point.geometry.getEmission()
                        .add(calcLocalEffects(point, ray)));
    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal((intersection.point));
        double nv = alignZero(n.dotProduct(v));
        Material material = intersection.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.kD, ks = material.kS;
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) {
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity), calcSpecular(ks, l, n, v, nShininess, lightIntensity));
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

    //  private Color calcGlobalEffects(GeoPoint geopoint, Ray ray, int level, double k) {
    //      Color color = Color.BLACK;
    //      Material material = geopoint.geometry.getMaterial();
    //      double kr = material.kR, kkr = k * kr;
    //      if (kkr > MIN_CALC_COLOR_K) {
    //          Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
    //          GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
    //          color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
    //      }
    //      double kt = material.kT, kkt = k * kt;
    //      if (kkt > MIN_CALC_COLOR_K) {
    //          Ray refractedRay = constructRefractedRay(n, geopoint.point, inRay);
    //          GeoPoint refractedPoint = findClosestIntersection(refractedRay);
    //          color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
    //      }
    //      return color;
    //  }

    //  private double transparency(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
    //      Vector lightDirection = l.scale(-1); // from point to light source
    //      Ray lightRay = new Ray(point, lightDirection, n);
    //      double lightDistance = light.getDistance(geopoint.point);
    //      var intersections = scene.geometries.findGeoIntersections(lightRay);
    //      if (intersections == null) return 1.0;
    //      double ktr = 1.0;
    //      for (GeoPoint gp : intersections) {
    //          if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0) {
    //              ktr *= gp.geometry.getMaterial().kT;
    //              if (ktr < MIN_CALC_COLOR_K) return 0.0;
    //          }
    //      }
    //      return ktr;
    //  }

    //  private boolean unshaded(LightSource light, Vector l, Vector n, GeoPoint geopoint) {
    //      Vector lightDirection = l.scale(-1); // from point to light source
    //      Ray lightRay = new Ray(point, lightDirection, n); // refactored ray head move
    //      List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
    //      if (intersections == null) return true;
    //      double lightDistance = light.getDistance(geopoint.point);
    //      for (GeoPoint gp : intersections) {
    //          if (alignZero(gp.point.distance(geopoint.point) - lightDistance) <= 0 &&
    //                  gp.geometry.getMaterial().kT == 0)
    //              return false;
    //      }
    //      return true;
    //  }

}
