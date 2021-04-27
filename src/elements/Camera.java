package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    final Point3D p0;
    final Vector vTo;
    final Vector vUp;
    final Vector vRight;
    private double distance;
    private double width;
    private double height;

    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vTo = vTo.normalized();
        this.vUp = vUp.normalized();
        if (!isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vUp is not orthogonal to vTo");
        }
        vRight = vTo.crossProduct(vUp);
    }

    /**
     * borrowing from builder pattern
     *
     * @param width
     * @param height
     * @return the camera object itself
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * borrowing from builder pattern
     *
     * @param distance
     * @return returns the camera object itself
     */
    public Camera setDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point3D Pc = p0.add(vTo.scale(distance));

        double Ry = height / nY;
        double Rx = height / nX;
        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;
        Point3D Pij = Pc;

        if (isZero(Yi) && isZero(Xj)) {
            return new Ray(p0, Pij.subtract(p0));
        }
        if (isZero(Xj)) {
            Pij = Pc.add(vUp.scale(Yi));
            return new Ray(p0, Pij.subtract(p0));
        }
        if (isZero(Yi)) {
            Pij = Pc.add(vRight.scale(Xj));
            return new Ray(p0, Pij.subtract(p0));
        }

        Pij = Pc.add(vRight.scale(Xj).add(vUp.scale(Yi)));
        return new Ray(p0, Pij.subtract(p0));

    }

    public Point3D getP0() {
        return p0;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvRight() {
        return vRight;
    }
}
