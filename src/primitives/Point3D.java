package primitives;

import java.util.Objects;

/**
 *The class represents a three-dimensional point
 */
public class Point3D {
    final Coordinate x;
    final Coordinate y;
    final Coordinate z;

    public static final Point3D ZERO = new Point3D(0, 0, 0);

    /**
     * constructor for Point3D
     *
     * @param x coordinate for x axis
     * @param y coordinate for y axis
     * @param z coordinate for z axis
     */
    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this(x.coord, y.coord, z.coord);
    }

    /**
     * constructor for Point3D
     *
     * @param X value for x coordinate
     * @param Y value for y coordinate
     * @param Z value for z coordinate
     */
    public Point3D(double X, double Y, double Z) {
        x = new Coordinate(X);
        y = new Coordinate(Y);
        z = new Coordinate(Z);
    }

    /**
     * Adding a vector to a point
     *
     * @param vector The second point
     * @return New point
     */
    public Point3D add(Vector vector) {
        return new Point3D(
                x.coord + vector.getHead().x.coord,
                y.coord + vector.getHead().y.coord,
                z.coord + vector.getHead().z.coord)
                ;

    }

    /**
     * Vector subtraction
     *
     * @param point3D The second point
     * @return Vector from the second point to the point on which the action is performed
     */
    public Vector subtract(Point3D point3D) {
        if (point3D.equals(this)) {
            throw new IllegalArgumentException("cannot create vector to point (0,0,0)");
        }
        double x = this.x.coord - point3D.x.coord;
        double y = this.y.coord - point3D.y.coord;
        double z = this.z.coord - point3D.z.coord;

        return new Vector(x, y, z);
    }

    /**
     * @param point3D The second point
     * @return Square distance between two three-dimensional points
     */
    public double distanceSquared(Point3D point3D) {
        final double x1 = x.coord;
        final double y1 = y.coord;
        final double z1 = z.coord;
        final double x2 = point3D.x.coord;
        final double y2 = point3D.y.coord;
        final double z2 = point3D.z.coord;
        return ((x2 - x1) * (x2 - x1)) + ((y2 - y1) * (y2 - y1)) + ((z2 - z1) * (z2 - z1));


    }

    /**
     * @param point3D
     * @return euclidean distance between 2 3D points
     */
    public double distance(Point3D point3D) {
        return Math.sqrt(distanceSquared(point3D));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

}


