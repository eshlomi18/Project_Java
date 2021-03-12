package primitives;

import java.util.Objects;

public class Point3D {
    private Coordinate x;
    private Coordinate y;
    private Coordinate z;

    public Point3D(Coordinate x, Coordinate y, Coordinate z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point3D(double x, double y, double z) {
       this.x = x;
    }

    public Point3D  add(Vector){

    }

  public Vector  subtract(Point3D){

    }

    public double distanceSquared(Point3D){

    }

    public double distance(Point3D){

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


