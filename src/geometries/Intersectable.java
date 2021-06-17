package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

public abstract class Intersectable {
    private static boolean _actBoundingBox = false;

    public static void set_actBoundingBox(boolean _actBoundingBox) {
        Intersectable._actBoundingBox = _actBoundingBox;
    }

    /**
     * Inner class Box represented a struct of  a box parallel to axis
     * that contain the Inetrsectable. By default have infinity values
     * and when the Intersectable is impelements the borders of the
     * Inetersectable is the borders of the box.
     */
    protected class Box {
        protected double _max_X = Double.POSITIVE_INFINITY;
        protected double _min_X = Double.NEGATIVE_INFINITY;
        protected double _max_Y = Double.POSITIVE_INFINITY;
        protected double _min_Y = Double.NEGATIVE_INFINITY;
        protected double _max_Z = Double.POSITIVE_INFINITY;
        protected double _min_Z = Double.NEGATIVE_INFINITY;

    }

    protected Box box = new Box();

    public Box getBox() {
        return box;
    }

    public void set_max_X(double _max_X) {
        this.box._max_X = _max_X;
    }

    public void set_min_X(double _min_X) {
        this.box._min_X = _min_X;
    }

    public void set_max_Y(double _max_Y) {
        this.box._max_Y = _max_Y;
    }

    public void set_min_Y(double _min_Y) {
        this.box._min_Y = _min_Y;
    }

    public void set_max_Z(double _max_Z) {
        this.box._max_Z = _max_Z;
    }

    public void set_min_Z(double _min_Z) {
        this.box._min_Z = _min_Z;
    }

    public double getMiddleX() {
        return box._min_X + (box._max_X - box._min_X) / 2;
    }

    public double getMiddleY() {
        return box._min_Y + (box._max_Y - box._min_Y) / 2;
    }

    public double getMiddleZ() {
        return box._min_Z + (box._max_Z - box._min_Z) / 2;
    }

    /**
     * Initial the max/min values in order that the first values of box
     * will give the right value
     */
    protected void ResetBox() {
        this.box._max_X = Double.NEGATIVE_INFINITY;
        this.box._min_X = Double.POSITIVE_INFINITY;
        this.box._max_Y = Double.NEGATIVE_INFINITY;
        this.box._min_Y = Double.POSITIVE_INFINITY;
        this.box._max_Z = Double.NEGATIVE_INFINITY;
        this.box._min_Z = Double.POSITIVE_INFINITY;
    }

    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor
         *
         * @param geometry The shape that is cut
         * @param point    The point where it is cut
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

    public List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public List<GeoPoint> findGeoIntersections(Ray ray, double max) {
        if (!_actBoundingBox || isIntersectionWithBox(ray))
            return findGeoIntersections(ray, max);
        return null;
    }

    /**
     * Calculates the points of intersection
     * @param ray
     * @return list of the points of intersection
     */
    public List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().
                map(gp -> gp.point)
                .collect(Collectors.toList());
    }

    /**
     * Calculate if the ray intersect the box. Using parametric
     * presentation of line.
     *
     * @param ray a ray in the scene that intersect the Intersectable
     * @return boolean value if their is intersection with the box.
     */
    public boolean isIntersectionWithBox(Ray ray) {

        Point3D start = ray.getP0();

        double start_X = start.getX();
        double start_Y = start.getY();
        double start_Z = start.getX();

        Point3D direction = ray.getDir().getHead();

        double direction_X = direction.getX();
        double direction_Y = direction.getY();
        double direction_Z = direction.getX();

        double max_t_for_X;
        double min_t_for_X;

        //If the direction_X is negative then the _min_X give the maximal value
        if (direction_X < 0) {
            max_t_for_X = (box._min_X - start_X) / direction_X;
            // Check if the Intersectable is behind the camera
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (box._max_X - start_X) / direction_X;
        } else if (direction_X > 0) {
            max_t_for_X = (box._max_X - start_X) / direction_X;
            if (max_t_for_X <= 0) return false;
            min_t_for_X = (box._min_X - start_X) / direction_X;
        } else {
            if (start_X >= box._max_X || start_X <= box._min_X)
                return false;
            else {
                max_t_for_X = Double.POSITIVE_INFINITY;
                min_t_for_X = Double.NEGATIVE_INFINITY;
            }
        }

        double max_t_for_Y;
        double min_t_for_Y;

        if (direction_Y < 0) {
            max_t_for_Y = (box._min_Y - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (box._max_Y - start_Y) / direction_Y;
        } else if (direction_Y > 0) {
            max_t_for_Y = (box._max_Y - start_Y) / direction_Y;
            if (max_t_for_Y <= 0) return false;
            min_t_for_Y = (box._min_Y - start_Y) / direction_Y;
        } else {
            if (start_Y >= box._max_Y || start_Y <= box._min_Y)
                return false;
            else {
                max_t_for_Y = Double.POSITIVE_INFINITY;
                min_t_for_Y = Double.NEGATIVE_INFINITY;
            }
        }

        //Check the maximal and the minimal value for t
        double temp_max = max_t_for_Y < max_t_for_X ? max_t_for_Y : max_t_for_X;
        double temp_min = min_t_for_Y > min_t_for_X ? min_t_for_Y : min_t_for_X;
        temp_min = temp_min > 0 ? temp_min : 0;

        if (temp_max < temp_min) return false;

        double max_t_for_Z;
        double min_t_for_Z;

        if (direction_Z < 0) {
            max_t_for_Z = (box._min_Z - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (box._max_Z - start_Z) / direction_Z;
        } else if (direction_Z > 0) {
            max_t_for_Z = (box._max_Z - start_Z) / direction_Z;
            if (max_t_for_Z <= 0) return false;
            min_t_for_Z = (box._min_Z - start_Z) / direction_Z;
        } else {
            if (start_Z >= box._max_Z || start_Z <= box._min_Z)
                return false;
            else {
                max_t_for_Z = Double.POSITIVE_INFINITY;
                min_t_for_Z = Double.NEGATIVE_INFINITY;
            }
        }

        temp_max = max_t_for_Z < temp_max ? max_t_for_Z : temp_max;
        temp_min = min_t_for_Z > temp_min ? min_t_for_Z : temp_min;

        if (temp_max < temp_min) return false;

        return true;
    }
}
