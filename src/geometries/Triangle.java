package geometries;

import primitives.Point3D;
import primitives.Vector;

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
}


