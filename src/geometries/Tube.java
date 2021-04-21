package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * tube shape
 * contains a ray and a radius
 */
public class Tube implements Geometry {
    protected Ray axisRay;
    protected Double radius;

    public Tube(Ray axisRay, Double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    public Double getRadius() {
        return radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }


    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        Point3D P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        Vector P1 = point3D.subtract(P0);

        double t = alignZero(v.dotProduct((P1)));

        if (isZero(t)) {
            return P1.normalize();
        }
        Point3D o = P0.add(v.scale(t));

        if (point3D.equals(0)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }

        Vector n = point3D.subtract(o);
        return n.normalized();

    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
        return null;
    }
}
