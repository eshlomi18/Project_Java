package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * sphere shape
 * contain a point for the center and a double number for the radius which is a finite number
 */
public class Sphere extends Geometry {
    private Point3D center;


    private double radius;

    public Sphere(double radius, Point3D center) {
        this.radius = radius;
        this.center = center;
    }

    public Point3D getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        if (point3D.equals(center)) {
            throw new IllegalArgumentException("point cannot be the center of the sphere");
        }
        return point3D.subtract(center).normalize();
    }




    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {

        Point3D p0 = ray.getP0();

        Vector v = ray.getDir();
        if (p0.equals(center)) {
            Point3D p =center.add(v.scale(radius));
            if(alignZero(radius-maxDistance)<=0){
                return List.of(new GeoPoint(this, p));
            }

        }
        Vector u = center.subtract(p0);
        double tm =alignZero(v.dotProduct(u));
        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
        if (d >= radius) {
            return null;
        }
        double th = alignZero(Math.sqrt(radius * radius - d * d));
        double t1 = alignZero(tm - th);
        double t2 = alignZero(tm + th);

        boolean t1Flag=alignZero(t1-maxDistance)<=0;
        boolean t2Flag=alignZero(t1-maxDistance)<=0;
        if (t1 > 0 && t2 > 0 && t2Flag && t2Flag) {
            return (List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))));
        }
        if (t1 > 0 && t1Flag) {

            return (List.of(new GeoPoint(this, ray.getPoint(t1))));
        }
        if (t2 > 0 && t2Flag) {

            return (List.of(new GeoPoint(this, ray.getPoint(t2))));
        }
        return null;
    }



















































//    @Override
//    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
//
//        Point3D p0 = ray.getP0();
//        Point3D o = center;
//        Vector v = ray.getDir();
//        if (o.equals(p0)) {
//            return List.of(new GeoPoint(this, p0.add(v.scale(radius))));
//        }
//        Vector u = o.subtract(p0);
//        double tm = v.dotProduct(u);
//        double d = alignZero(Math.sqrt(u.lengthSquared() - tm * tm));
//        if (d >= radius) {
//            return null;
//        }
//        double th = alignZero(Math.sqrt(radius * radius - d * d));
//        double t1 = alignZero(tm - th);
//        double t2 = alignZero(tm + th);
//
//        if (t1 > 0 && t2 > 0 && alignZero(t1 - maxDistance) <= 0 && alignZero(t2 - maxDistance) <= 0) {
//            return (List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2))));
//        }
//        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
//
//            return (List.of(new GeoPoint(this, ray.getPoint(t1))));
//        }
//        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
//
//            return (List.of(new GeoPoint(this, ray.getPoint(t2))));
//        }
//        return null;
//    }
}











