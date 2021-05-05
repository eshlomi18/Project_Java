package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Ray {
    private Point3D p0;
    private Vector dir;

    /**
     * constructor
     *
     * @param point3D 3D point
     * @param vector  vector
     */
    public Ray(Point3D point3D, Vector vector) {
        p0 = new Point3D(point3D.x, point3D.y, point3D.z);
        dir = new Vector(vector.getHead());
        dir.normalize();
    }

    /**
     * getter
     *
     * @return start point p0
     */
    public Point3D getP0() {
        return p0;
    }

    /**
     * getter
     *
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

    public Point3D getPoint(double t) {
        return p0.add(dir.scale(t));
    }

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
}
