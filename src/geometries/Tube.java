package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube implements Geometry {
    protected Ray axisRay;
    protected Double radius;

    public Tube(Ray axisRay,Double radius){
        this.axisRay=axisRay;
        this.radius=radius;
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
        return null;
    }
}
