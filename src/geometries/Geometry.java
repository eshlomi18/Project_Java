package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface that contains a function that return a vector normal to the shape
 */
public abstract class Geometry implements Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();
    protected double Xmin; // the X-value minimum of the Geometry
    protected double Ymin; // the Y-value minimum of the Geometry
    protected double Zmin; // the Z-value minimum of the Geometry
    protected double Xmax; // the X-value maximum of the Geometry
    protected double Ymax; // the Y-value maximum of the Geometry
    protected double Zmax; // the Z-value maximum of the Geometry
    protected static double MAX = 100000;
    protected static double MIN = -100000;

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }


    public abstract Vector getNormal(Point3D point3D);

    /***
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    @Override
    public double getXmin() {
        return Xmin;
    }
    @Override
    public double getYmin() {
        return Ymin;
    }
    @Override
    public double getZmin() {
        return Zmin;
    }
    @Override
    public double getXmax() {
        return Xmax;
    }
    @Override
    public double getYmax() {
        return Ymax;
    }
    @Override
    public double getZmax() {
        return Zmax;
    }
}
