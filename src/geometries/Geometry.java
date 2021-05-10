package geometries;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface that contains a function that return a vector normal to the shape
 */
public abstract class Geometry implements Intersectable {
    protected Color emission = Color.BLACK;

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


    public abstract Vector getNormal(Point3D point3D);
}
