package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * interface that contains a function that return a vector normal to the shape
 */
public abstract class Geometry extends Intersectable {


    protected Color emission = Color.BLACK;
    private Material material = new Material();
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

}
