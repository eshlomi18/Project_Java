package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Ray {
    private Point3D p0;
    private Vector dir;

    public Ray(Point3D point3D, Vector vector) {
        p0 = new Point3D(point3D.x, point3D.y, point3D.z);
        dir = new Vector(vector.getHead());
        dir.normalize();
    }

    public Point3D getP0() {
        return p0;
    }

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
}
