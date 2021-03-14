package geometries;

import primitives.Point3D;

/**
 * triangle shape at the moment nothing special
 */
public class Triangle extends Polygon{

    public Triangle(Point3D... vertices) {
        super(vertices);

    }

    @Override
    public String toString() {
        return "Triangle{} " + super.toString();
    }
}
