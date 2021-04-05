package primitives;

import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Vector {
    private Point3D head;

    /**
     * mainly used constructor
     *
     * @param head The point with which we will initialize head
     */
    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("head of vector cannot be point(0,0,0)");
        }
        this.head = new Point3D(head.x, head.y, head.z);

    }

    /**
     * constructor with double values which will use the main constructor
     * @param x value for x coordinate
     * @param y value for y coordinate
     * @param z value for z coordinate
     */
    public Vector(double x, double y, double z) {
        this(new Point3D(x, y, z));

    }

    /**
     * constructor with Coordinate values which will use the main constructor
     * @param x value for x coordinate
     * @param y value for y coordinate
     * @param z value for z coordinate
     */
   /* public Vector(Coordinate x, Coordinate y, Coordinate z) {
        this(new Point3D(x, y, z));
    }*/

    /**
     * vector addition
     * @param vector the second vector
     * @return new vector
     */
    public Vector add(Vector vector) {
        double x = head.x.coord + vector.head.x.coord;
        double y = head.y.coord + vector.head.y.coord;
        double z = head.z.coord + vector.head.z.coord;
        return new Vector(x, y, z);

    }

    /**
     * vector subtraction
     * @param v the second vector
     * @return new vector
     */
    public Vector subtract(Vector v) {
        return head.subtract(v.head);

    }

    /**
     *get head
     * @return head
     */
    public Point3D getHead() {
        return head;
    }

    /**
     * Scalar multiplication
     * @param a scalar
     * @return new vector
     */
    public Vector scale(double a) {
        if (a == 0d) {
            throw new IllegalArgumentException("cannot scale by zero");
        }
        return new Vector(
                (head.x.coord) * a,
                (head.y.coord) * a,
                (head.z.coord) * a);

    }

    /**
     * Cross product
     * @param v the second vector
     * @return new vector
     */
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

    /**
     * dotProduct
     * @param v the second vector
     * @return double type value
     */
    public double dotProduct(Vector v) {
        double u1 = head.x.coord;
        double u2 = head.y.coord;
        double u3 = head.z.coord;
        double v1 = v.head.x.coord;
        double v2 = v.head.y.coord;
        double v3 = v.head.z.coord;
        return (u1 * v1 + u2 * v2 + u3 * v3);

    }

    /**
     * Calculates the length of the vector squared
     *
     * @return the length of the vector squared
     */
    public double lengthSquared() {
        double x = head.x.coord;
        double y = head.y.coord;
        double z = head.z.coord;
        return ((x * x) + (y * y) + (z * z));


    }

    /**
     * Calculates the length of the vector
     *
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        double length = length();
        if (length == 0) {
            throw new ArithmeticException("cannot divide by zero");
        }
        head = new Point3D(head.x.coord / length, head.y.coord / length, head.z.coord / length);
        return this;
    }

    /**
     * normalize vector
     *
     * @return new normalized vector
     */
    public Vector normalized() {
        Vector newVector = new Vector(head);
        newVector.normalize();
        return newVector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "head=" + head.toString() +
                '}';
    }
}
