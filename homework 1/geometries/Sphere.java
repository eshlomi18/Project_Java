package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * sphere shape
 * contain a point for the center and a double number for the radius which is a finite number
 */
public class Sphere implements Geometry {
    private Point3D center;
    private Double radius;

    public Sphere(Point3D center, Double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point3D getCenter() {
        return center;
    }

    public Double getRadius() {
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
        return null;
    }
}