package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * composite class for all geometries
 */
public class Geometries implements Intersectable {
    private List<Intersectable> intersectables= new LinkedList<>();
    public  Geometries(Intersectable...list){
        add(list);
    }

    public void add(Intersectable... list) {
        Collections.addAll(intersectables, list);
    }


    public List<GeoPoint> findGeoIntersections(Ray ray){
        List<GeoPoint>result=null;
        for (Intersectable geo:intersectables){
            List<GeoPoint> geoPoints= geo.findGeoIntersections(ray);
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
