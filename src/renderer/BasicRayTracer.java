package renderer;

import elements.DirectionalLight;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

import primitives.*;
import scene.Scene;

import java.util.ArrayList;
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
    private static final int MAX_CALC_COLOR_LEVEL = 10;//stop condition that represents the number of levels we want to enter in the recursion
    private static final double MIN_CALC_COLOR_K = 0.001;// stop condition that make sure there are no more unnecessary recursions
    private static final boolean GLOSSY_SURFACE = true;
    private static final boolean SOFT_SHADOW = true;
    //private static final boolean ALGO_IMPROV = false;

    private static final double INITIAL_K = 1.0;
    private int _threads = 1;
    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean _print = false; // printing progress percentage

    /**
     * constructs a ray tracer object with a given scene
     * @param scene the scene for ray tracing
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }


    /**
     * traces a given ray and returns the color of the hit object
     *
     * @param ray the ray to trace
     * @return the color of the hit object
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K + 5);
    }

    /***
     * calculate the color of point
     * @return  the color of point
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
     * @return new ray for the rafracted
     */
    private List<Ray> constructRefractedRay(Point3D point, Vector v, Vector n) {

        Ray refractedRay = new Ray(point, v, n);
        List<Ray> list = new LinkedList<>();
        list.add(refractedRay);
        if (GLOSSY_SURFACE) {
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
        }
        return list;


    }

    /***
     *
     * @param point
     * @param v
     * @param n
     * @return new ray for the reflected
     */
    private List<Ray> constructReflectedRay(Point3D point, Vector v, Vector n) {


        double temp = v.dotProduct(n) * 2;
        Vector r = v.subtract(n.scale(temp));
        Ray reflectedRay = new Ray(point, r, n);
        List<Ray> list = new LinkedList<>();
        list.add(reflectedRay);
        if (GLOSSY_SURFACE) {
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


        }
        return list;

    }

    /**
     *
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return color with global effect
     */
    private Color calcGlobalEffect(List<Ray> ray, int level, double kx, double kkx) {
        List<Color> colors = new LinkedList<>();
        for (Ray r : ray) {
            GeoPoint gp = findClosestIntersection(r);
            colors.add((gp == null ? scene.background : calcColor(gp, r, level - 1, kkx)).scale(kx));
        }
        return average(colors);
    }

    /**
     *
     * @param geopoint
     * @param inRay
     * @param k
     * @return color with local effect
     */
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

    /**
     * help function that calculates the diffusion for each object
     * @param kd diffusion coefficient of diffusion
     * @param l the vector from the light source
     * @param n the normal of the object
     * @param lightIntensity the intensity of the light
     * @return
     */
    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        double factor = kd * Math.abs(l.dotProduct(n));
        return lightIntensity.scale(factor);
    }

    /**
     * help function that calculates the specular for each object
     * @param ks the discount factor of the specular
     * @param l the vector from the light source
     * @param n the normal of the object
     * @param v the vector from the camera
     * @param nShininess the value of the Shininess of the material
     * @param lightIntensity the intensity of the light
     * @return
     */
    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        Vector r = l.subtract(n.scale(2 * l.dotProduct(n)));
        double minusVr = v.dotProduct(r) * -1;
        return lightIntensity.scale(ks * Math.pow(Math.max(0, minusVr), nShininess));
    }

    /**
     * check if there is a particular shadow on a particular point
     * @param light  LightSource
     * @param l the vector from the light source
     * @param n the normal of the object
     * @param geopoint
     * @return
     */
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

        // if the light source is Directional, there isn't softshadow
        if (light instanceof DirectionalLight || !SOFT_SHADOW) {
            double lightDistance = light.getDistance(geoPoint.point);
           // var intersections = improvementIntersection(lightRay, Double.POSITIVE_INFINITY);
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
        } else {
            // get list of several rays
            LinkedList<Ray> listRay = lightRay.getListRays(light.getBulb().getCenter(), (int) light.getBulb().getRadius());

            double lightDistance = light.getDistance(geoPoint.point);

            double ktr = 1.0;
            double sumKtr = 0;//sum ktr of all intersection points for all rays
            boolean flagIntersection = false;

            for (Ray r : listRay) {
                    List<GeoPoint> intersecOneRay = scene.geometries.findGeoIntersections(r, lightDistance);


             //   List<GeoPoint> intersecOneRay = improvementIntersection(r, lightDistance);

                // if the ray 'r' don't crosses any geometries, it's like it crosses geometries transparent
                if (intersecOneRay == null) ktr = 1.0;
                else {
                    flagIntersection = true;//there is at least one intersection point
                    // calculate an accumulation of ktr for all geometries crossed by the ray 'r'
                    for (GeoPoint gpt : intersecOneRay) {
                        ktr *= gpt.geometry.getMaterial().kT;
                    }
                }

                sumKtr += ktr;
                ktr = 1.0;
            }

            if (flagIntersection == false)//if there aren't any intersection
                return 1.0;

            int numRay = listRay.size();
            double ktrAverage = sumKtr / numRay;

            return ktrAverage;
        }
    }
    /**
     * Finds the closest point to the beginning of the ray
     * @param ray
     * @return the closest point to the beginning of the ray
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> gp = scene.geometries.findGeoIntersections(ray);
     //   List<GeoPoint> intersectionPoints = improvementIntersection(ray, Double.POSITIVE_INFINITY);

        GeoPoint closestPoint = ray.findClosestGeoPoint(gp);
      //  if (closestPoint == null || !ALGO_IMPROV)
      //      return closestPoint;
//
      //  double distanceToLastPoint = gp.get(0).point.distance(ray.getP0());
      //  if (distanceToLastPoint < Double.POSITIVE_INFINITY) {
      //      closestPoint = gp.get(0);
      //  }
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

    /**
     *  calculate the avarage color of pixel
     * @param list
     * @return the avarage color
     */
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

   ///**
   // * this function calculate intersections points after the improvement
   // *
   // * @param ray ray of the light
   // * @param max distance max
   // * @return intersectionPoints
   // */
   //private List<GeoPoint> improvementIntersection(Ray ray, double max) {

   //    Scene.Node<Geometries> root = scene.getGeometriesTree();
   ///        List<Intersectable> box =root.getData().getGeometries();
   //    Geometries box2 = root.getData();

   //    // check intersection with the Outer Box containing all geometries of the scene
   //    List<GeoPoint> intersectionPointsWithOuterBox = box2.findGeoIntersections(ray, max);
   //    if (intersectionPointsWithOuterBox == null || !ALGO_IMPROV)
   //        return scene.getGeometries().findGeoIntersections(ray, max);

   //    //return _scene.getGeometries().findGeoIntersections(ray, max);

   //    List<GeoPoint> interPointsWithInnerBox = null;
   //    List<GeoPoint> intersectionPointsWithGeometries = null;
   //    List<Scene.Node<Geometries>> children = scene.getGeometriesTree().getChildren();
   //    for (Scene.Node<Geometries> g : children) {
   //        interPointsWithInnerBox = g.getData().findGeoIntersections(ray, max);
   //        if (interPointsWithInnerBox != null) {
   //            List<GeoPoint> interPointsGeometry = g.getChildren().get(0).getData().findGeoIntersections(ray, max);
   //            if (interPointsGeometry != null) {
   //                //initialization of the list for the first intersection's point with the geometry
   //                if (intersectionPointsWithGeometries == null)//
   //                    intersectionPointsWithGeometries = new ArrayList();
   //                for (GeoPoint gp : interPointsGeometry)
   //                    intersectionPointsWithGeometries.add(gp);
   //            }
   //        }
   //    }
   //    return intersectionPointsWithGeometries;
   //}


}


