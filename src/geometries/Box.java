package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Create Box in order to contain  shapes
 */
public class Box extends Geometries{

    private Geometries geometrie;

    protected static double MAX = 100000;
    protected static double MIN = -100000;


    /**
     * Default Constructor
     */
    public Box() {
        Xmin = MAX;
        Xmax = MIN;
        Ymin = MAX;
        Ymax = MIN;
        Zmin = MAX;
        Zmax = MIN;
        intersectables = new ArrayList();
    }

    /**
     * CTOR with geometry
     * @param inter the geometry to put in box
     */
    public Box(Intersectable inter){
        setBox(inter.getXmin(), inter.getXmax(), inter.getYmin(), inter.getYmax(), inter.getZmin(), inter.getZmax());
        geometrie=new Geometries(inter);
    }

    /**
     * Create the box according to shape's length
     * @param Xmin min axe X
     * @param Xmax max axe x
     * @param Ymin min axe y
     * @param Ymax max axe y
     * @param Zmin min axe z
     * @param Zmax max axe z
     */
    public void setBox(double Xmin, double Xmax, double Ymin, double Ymax, double Zmin, double Zmax) {
        this.Xmin = Xmin;
        this.Xmax = Xmax;
        this.Ymin = Ymin;
        this.Ymax = Ymax;
        this.Zmin = Zmin;
        this.Zmax = Zmax;

        if(intersectables==null)
            intersectables=new ArrayList();
        else
            intersectables.clear();
        intersectables.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmax, Ymax, Zmin), new Point3D(Xmin, Ymax, Zmin)));
        intersectables.add(new Polygon(new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmax, Ymax, Zmax), new Point3D(Xmin, Ymax, Zmax)));
        intersectables.add(new Polygon(new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmax, Ymax, Zmax), new Point3D(Xmax, Ymax, Zmin)));
        intersectables.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmin, Ymin, Zmax), new Point3D(Xmin, Ymax, Zmax), new Point3D(Xmin, Ymax, Zmin)));
        intersectables.add(new Polygon(new Point3D(Xmin, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmin), new Point3D(Xmax, Ymin, Zmax), new Point3D(Xmin, Ymin, Zmax)));
        intersectables.add(new Polygon(new Point3D(Xmin, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmin), new Point3D(Xmax, Ymax, Zmax), new Point3D(Xmin, Ymax, Zmax)));



    }

    /**
     * this function recalculate the dimensions of the box after adding geometries in the box
     * @param geometries geometries to add
     */
    public void addGeometries(Intersectable ... geometries) {
        double Xmingeometries = Xmin;
        double Ymingeometries = Ymin;
        double Zmingeometries = Zmin;
        double Xmaxgeometries = Xmax;
        double Ymaxgeometries = Ymax;
        double Zmaxgeometries = Zmax;

        for (Intersectable i : geometries) {
            if (Xmingeometries > i.getXmin()) Xmingeometries = i.getXmin();
            if (Ymingeometries > i.getYmin()) Ymingeometries = i.getYmin();
            if (Zmingeometries > i.getZmin()) Zmingeometries = i.getZmin();

            if (Xmaxgeometries < i.getXmax()) Xmaxgeometries = i.getXmax();
            if (Ymaxgeometries < i.getYmax()) Ymaxgeometries = i.getYmax();
            if (Zmaxgeometries < i.getZmax()) Zmaxgeometries = i.getZmax();
        }

        setBox(Xmingeometries, Xmaxgeometries, Ymingeometries, Ymaxgeometries, Zmingeometries, Zmaxgeometries);

        if (geometries.length==1) {
            geometrie=new Geometries(geometries[0]);
        }
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        List<GeoPoint> intersectionPoint = null;
        for (Intersectable p : intersectables)
            if (intersectionPoint == null)
                intersectionPoint = p.findGeoIntersections(ray, max);

        return intersectionPoint;
    }

    /**
     * getter of geometries of the box
     * @return list of geometries
     */
    public List<Intersectable> getGeometries() {
        return geometrie.getGeometries();
    }



}
