package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * interface that contains a function that return a vector normal to the shape
 */
public interface Geometry {
    public Vector getNormal(Point3D point3D);
}
