package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

//    @Override
//    public List<Point3D> findIntersections(Ray ray) {
//        return super.findIntersections(ray);
//    }
//
//    public List<GeoPoint> findGeoIntersections(Ray ray){
//        return super.findGeoIntersections(ray);
//    }

}



