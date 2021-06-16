package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * composite class for all geometries
 */
public class Geometries implements Intersectable {
    protected List<Intersectable> intersectables= new LinkedList<>();
    protected double Xmin; // the X-value minimum of the Geometry
    protected double Ymin; // the Y-value minimum of the Geometry
    protected double Zmin; // the Z-value minimum of the Geometry
    protected double Xmax; // the X-value maximum of the Geometry
    protected double Ymax; // the Y-value maximum of the Geometry
    protected double Zmax; // the Z-value maximum of the Geometry
    protected static double MAX = 100000;
    protected static double MIN = -100000;

    /**
     * create a list who will contain shapes
     */
    public Geometries() {
        this.intersectables = new ArrayList<Intersectable>();
    }

    /**
     * constructor
     * @param list  of Intersectable gemotries
     */
    public  Geometries(Intersectable...list){
        this();
        add(list);
    }

    /**
     * add geometries to the list
     * @param list The updated new list
     */
    public void add(Intersectable... list) {
        Collections.addAll(intersectables, list);
    }

    /**
     * framing shapes
     * @param listGeometries list of shapes
     */
    public void addList(List listGeometries) {
        this.intersectables.addAll(listGeometries);

        //calculate the min and max of coordinates X,Y,Z of Geometries
        double XminGeo = intersectables.get(0).getXmin();
        double YminGeo = intersectables.get(0).getYmin();
        double ZminGeo = intersectables.get(0).getZmin();
        double XmaxGeo = intersectables.get(0).getXmax();
        double YmaxGeo = intersectables.get(0).getYmax();
        double ZmaxGeo = intersectables.get(0).getZmax();

        for (Intersectable i : intersectables) {

            if (XminGeo > i.getXmin()) XminGeo = i.getXmin();
            if (YminGeo > i.getYmin()) YminGeo = i.getYmin();
            if (ZminGeo > i.getZmin()) ZminGeo = i.getZmin();

            if (XmaxGeo < i.getXmax()) XmaxGeo = i.getXmax();
            if (YmaxGeo < i.getYmax()) YmaxGeo = i.getYmax();
            if (ZmaxGeo < i.getZmax()) ZmaxGeo = i.getZmax();
        }
        this.Xmin = XminGeo;
        this.Xmax = XmaxGeo;
        this.Ymin = YminGeo;
        this.Ymax = YmaxGeo;
        this.Zmin = ZminGeo;
        this.Zmax = ZmaxGeo;

    }
    /**
     *getter of x min
     * @return Xmin
     */
    public double getXmin() {
        return Xmin;
    }

    /**
     * getter of y min
     * @return Ymin
     */
    public double getYmin() {
        return Ymin;
    }

    /**
     * getter of z min
     * @return z
     */
    public double getZmin() {
        return Zmin;
    }

    /**
     * getter of x max
     * @return x max
     */
    public double getXmax() {
        return Xmax;
    }

    /**
     * getter of y max
     * @return ymax
     */
    public double getYmax() {
        return Ymax;
    }

    /**
     * getter of z max
     * @return Zmax
     */
    public double getZmax() {
        return Zmax;
    }
    public List<Intersectable> getGeometries(){
        return intersectables;
    }


    /**
     * find geo points intersections of ray and geometries
     * @param ray The cutting ray
     * @param maxDistance the distance from the light
     * @return list of GeoPoint intersections
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        List<GeoPoint>result=null;
        for (Intersectable geo:intersectables){
            List<GeoPoint> geoPoints= geo.findGeoIntersections(ray,maxDistance);
            if(geoPoints!=null){
                if(result==null){
                    result=new LinkedList<>();
                }
                result.addAll(geoPoints);
            }
        }
        return result;
    }
}
