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

    /**
     * getter
     * @return emission
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * setter
     * @param emission
     * @return geometry
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * return normal for a given point on the geometry should be zero for flat geometries
     * @param point3D the point on the geometry
     * @return the normal vector to the geometry
     */
    public abstract Vector getNormal(Point3D point3D);

    /***
     *getter
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * setter
     * @param material
     * @return geometry
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /***
     * getter
     * @return the X-value minimum of the Geometry
     */
    @Override
    public double getXmin() {
        return Xmin;
    }

    /**
     * getter
     * @return the Y-value minimum of the Geometry
     */
    @Override
    public double getYmin() {
        return Ymin;
    }

    /**
     * getter
     * @return the Z-value minimum of the Geometry
     */
    @Override
    public double getZmin() {
        return Zmin;
    }

    /**
     * getter
     * @return X-value maximum of the Geometry
     */
    @Override
    public double getXmax() {
        return Xmax;
    }

    /**
     * getter
     * @return Y-value maximum of the Geometry
     */
    @Override
    public double getYmax() {
        return Ymax;
    }

    /**
     * getter
     * @return Z-value maximum of the Geometry
     */
    @Override
    public double getZmax() {
        return Zmax;
    }
}
