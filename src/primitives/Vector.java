package primitives;

import java.util.Objects;

public class Vector {
    private Point3D head;

  public  Vector add(Vector){

    }

    public  Vector subtract(Vector){

    }

    public Vector scale (double){

    }

    public Vector crossProduct(Vector){

    }

    public double dotProduct(Vector){

    }

    public double lengthSquared(){

    }

    public double length(){

    }

    public Vector normalize(){

    }

    public Vector normalized(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return head.equals(vector.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(head);
    }
}
