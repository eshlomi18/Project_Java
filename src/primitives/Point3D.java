package primitives;

import java.util.Objects;

public class Point3D {
    private Coordinate x;
    private Coordinate y;
    private Coordinate z;

    public static final Point3D ZERO = new Point3D(0, 0, 0);


    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //להבין
    public Point3D(double X, double Y, double Z) {
        x = new Coordinate(X);
        y = new Coordinate(Y);
        z = new Coordinate(Z);
    }

    public Point3D add(Vector) {

    }

    //לסדר
    public Vector subtract(Point3D a) {


        return vector;

    }

    public double distanceSquared(Point3D) {

    }

    public double distance(Point3D) {

    }

    //לבדוק לגבי הערכים האפסיים
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point3D point3D = (Point3D) o;
        return x.equals(point3D.x) && y.equals(point3D.y) && z.equals(point3D.z);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}


