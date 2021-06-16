package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.beans.VetoableChangeListener;
import java.util.List;
import static geometries.Geometries.*;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * class plane with 2 different constructors first one with 3 points and the second
 * with a point and a vector
 */
public class Plane extends Geometry {
    private Point3D q0;
    private Vector normal;

    /**
     * constructor and it calculate the normal vector
     * @param q1 the first point
     * @param q2 the second point
     * @param q3 the third point
     */
    public Plane(Point3D q1, Point3D q2, Point3D q3) {
        this.q0 = q1;

        Vector U = q2.subtract(q1);
        Vector V = q3.subtract(q1);
        Vector N = U.crossProduct(V);
        N.normalize();
        this.normal = N;

        Xmax = MAX;
        Ymax = MAX;
        Zmax = MAX;
        Xmin = MIN;
        Ymin = MIN;
        Zmin = MIN;
    }

    /**
     * constructor
     * @param q0
     * @param normal
     */
    public Plane(Point3D q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalized();
    }

    /**
     * getter
     * @return q0
     */
    public Point3D getQ0() {
        return q0;
    }

    /**
     * getter
     * @return normal vector
     */
    public Vector getNormal() {
        return normal;
    }

    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }

    /**
     * getter
     * @param point3D
     * @return normal vector
     */
    @Override
    public Vector getNormal(Point3D point3D) {
        return normal;
    }


    /**
     * Calculates the points of intersection of a ray with the plane and returns it
     * @param ray The cutting ray
     * @param maxDistance the distance from the light
     * @return list intersections of GeoPoints
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
        Point3D P0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = normal;

        if (q0.equals(P0)) {
            return null;
        }

        Vector P0_Q0 = q0.subtract(P0);

        //numerator
        double nP0Q0 = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0)) {
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if (isZero(nv)) {
            return null;
        }

        double t = alignZero(nP0Q0 / nv);

        if (t <= 0|| alignZero(t-maxDistance)>0) {
            return null;
        }

        Point3D point = ray.getPoint(t);

        return List.of(new GeoPoint(this, point));
    }






































//    @Override
//    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
//        Point3D P0 = ray.getP0();
//        Vector v = ray.getDir();
//        Vector n = normal;
//
//        if (q0.equals(P0)) {
//            return null;
//        }
//
//
//        if (isZero(n.dotProduct(v))) {
//            return null;
//        }
//
//        double t = n.dotProduct(q0.subtract(P0)) / n.dotProduct(v);
//
//        if (t > 0 && alignZero(t - maxDistance) <= 0) {
//            return List.of(new GeoPoint(this, ray.getPoint(t)));
//        }
//
//        return null;
//
//
//    }

}
