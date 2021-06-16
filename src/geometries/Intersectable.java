package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

public interface Intersectable {
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         * @param geometry The shape that is cut
         * @param point The point where it is cut
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }

    default List<GeoPoint> findGeoIntersections(Ray ray){
       return findGeoIntersections(ray,Double.POSITIVE_INFINITY);
    }

    List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance);

    /**
     * Calculates the points of intersection
     * @param ray
     * @return list of the points of intersection
     */
    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().
                map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    double getXmin();
    double getYmin();
    double getZmin();
    double getXmax();
    double getYmax();
    double getZmax();




}
