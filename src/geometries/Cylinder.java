package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * geometrical shape that inherit from tube shape
 * contain beside of tube's fields a finite number height
 */
public class Cylinder extends Tube  {
    private double height;


    public Cylinder(Ray axisRay, double radius,double height){
        super(axisRay,radius);
        this.height=height;
    }

    public double getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + height +
                "} " + super.toString();
    }

    @Override
    public Vector getNormal(Point3D point3D) {
        return null;
    }
}
