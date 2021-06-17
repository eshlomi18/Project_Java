package primitives;

import geometries.Intersectable;

import static geometries.Intersectable.GeoPoint;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static primitives.Point3D.ZERO;

public class Ray {
    private Point3D p0;
    private Vector dir;
    private static final double DELTA = 0.1;

    /**
     * constructor
     * @param point3D 3D point
     * @param vector  vector
     */
    public Ray(Point3D point3D, Vector vector) {
        p0 = new Point3D(point3D.x, point3D.y, point3D.z);
        dir = new Vector(vector.getHead());
        dir.normalize();
    }

    /**
     * constructor
     * @param point
     * @param dir
     * @param n
     */
    public Ray(Point3D point, Vector dir, Vector n) {
        Vector delta = n.scale(n.dotProduct(dir) > 0 ? DELTA : -DELTA);
        Point3D p = point.add(delta);
        p0 = p;
        this.dir = dir;
    }

    /**
     * getter
     * @return start point p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter
     * @return ray direction
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return p0.equals(ray.p0) && dir.equals(ray.dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString() +
                '}';
    }

    /**
     * getter - gets double and return the target on the ray
     * @param t the scalar for the vector
     * @return the desired target point
     */
    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    /**
     * Finds the closest point to the beginning of the ray
     * @param point3DList intersection point list
     * @return the closest point to the beginning of the ray
     */
    public Point3D findClosestPoint(List<Point3D> point3DList) {

        double distance = Double.POSITIVE_INFINITY;
        Point3D nearPoint = null;
        if (point3DList != null) {
            for (Point3D p : point3DList) {
                double dis = p.distance(p0);
                if (dis < distance) {
                    distance = dis;
                    nearPoint = p;
                }

            }
        }
        return nearPoint;
    }
    /**
     * Finds the closest point to the beginning of the ray
     * @param geoPointList A collection of points
     * @return the closest point to the beginning of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        double distance = Double.POSITIVE_INFINITY;
        GeoPoint nearPoint = null;
        if (geoPointList != null) {
            for (GeoPoint geo : geoPointList) {
                double dis = geo.point.distance(p0);
                if (dis < distance) {
                    distance = dis;
                    nearPoint = geo;
                }

            }
        }
        return nearPoint;
    }

    /**
     * create beam of rays for picture improvement
     * @param center
     * @param radius
     * @return list of rays
     */
    public LinkedList<Ray> getListRays(Point3D center, int radius) {
        LinkedList<Ray> listRay = new LinkedList<Ray>();
        listRay.add(this);

        //int radius = (int) lightSource.getBulb().getRadius();
        //Point3D center = lightSource.getBulb().getCenter();
        double centerX = center.getX();
        double centerY = center.getY();
        double centerZ = center.getZ();

        Random rand = new Random();
        for (int i = 0; i < 50; i++) {
            double x = rand.nextInt((int) centerX + radius - (int) (centerX - radius) + 1) + (centerX - radius);
            double y = rand.nextInt((int) centerY + radius - (int) (centerY - radius) + 1) + (centerY - radius);
            double z = rand.nextInt((int) centerZ + radius - (int) (centerZ - radius) + 1) + (centerZ - radius);

            Point3D pointOfSphere = new Point3D(x, y, z);
            Vector dest = pointOfSphere.subtract(this.p0);
            Ray r = new Ray(this.p0, dest);
            listRay.add(r);
        }
        return listRay;
    }
}
