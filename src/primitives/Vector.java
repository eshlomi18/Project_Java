package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Vector {
    final Point3D head;

    public Vector(double X, double Y, double Z) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("head of vector cannot be point(0,0,0)");
        }

        head = new Point3D(X, Y, Z);


    }

    public Vector add(Vector) {

    }

    public Vector subtract(Vector) {

    }

    public Point3D getHead() {
        return head;
    }

    public Vector scale(double) {

    }

    public Vector crossProduct(Vector v) {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = v.head.x.coord;
        double v2 = v.head.y.coord;
        double v3 = v.head.z.coord;
        return new Vector
                (u2 * v3 - u3 * v2,
                        u3 * v1 - u1 * v3,
                        u1 * v2 - u2 * v1);
    }

    public double dotProduct(Vector v) {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = v.head.x.coord;
        double v2 = v.head.y.coord;
        double v3 = v.head.z.coord;
        return (u1 * v1 + u2 * v2 + u3 * v3);

    }

    public double lengthSquared() {

    }

    public double length() {

    }

    public Vector normalize() {

    }

    public Vector normalized() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }


}
