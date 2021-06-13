package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

/**
 * triangle shape at the moment nothing special
 */
public class Triangle extends Polygon {

    public Triangle(Point3D... vertices) {
        super(vertices);

    }


    @Override
    public String toString() {
        return "Triangle{} " + super.toString();
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return super.getNormal(point3D);
    }

 //  @Override
 //  public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
 //      List<GeoPoint> intersections = plane.findGeoIntersections(ray, max);
 //      if (intersections == null) return null;

 //      Point3D pt = ray.getP0();
 //      Vector v = ray.getDir();
 //      Vector v1 = vertices.get(0).subtract(pt);
 //      Vector v2 = vertices.get(1).subtract(pt);
 //      Vector v3 = vertices.get(2).subtract(pt);

 //      double t1 = v.dotProduct(v1.crossProduct(v2));
 //      if (isZero(t1)) return null;
 //      double t2 = v.dotProduct(v2.crossProduct(v3));
 //      if (isZero(t2)) return null;
 //      double t3 = v.dotProduct(v3.crossProduct(v1));
 //      if (isZero(t3)) return null;

 //      if ((t1 > 0 && t2 > 0 && t3 > 0) || (t1 < 0 && t2 < 0 && t3 < 0)) {
 //          //for GeoPoint
 //          intersections.get(0).geometry = this;
 //          return intersections;
 //          //return intersections;
 //      }

 //      // the intersection point with the plane is outside the triangle
 //      return null;
 //  }
  //  @Override
  //  public List<Point3D> findIntersections(Ray ray) {
  //      return super.findIntersections(ray);
  //  }
//
//    public List<GeoPoint> findGeoIntersections(Ray ray){
//        return super.findGeoIntersections(ray);
//    }

}



