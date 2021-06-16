package elements;

import geometries.Sphere;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class NarrowLight extends Light implements LightSource {
    private final Vector direction;
    protected Point3D position;

    /**
     * setter
     * @param kC
     * @return
     */
    public NarrowLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter
     * @param kL
     * @return
     */
    public NarrowLight setkL(double kL) {
        this.kL = kL;
        return this;

    }

    /**
     * setter
     * @param kQ
     * @return
     */
    public NarrowLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;

    }

    private double kC;
    private double kL;
    private double kQ;

    /**
     * constructor
     * @param intensity
     * @param position
     * @param direction normalized direction
     */
    protected NarrowLight(Color intensity, Point3D position, Vector direction) {
        super(intensity);
        this.position = position;

        this.direction = direction.normalized();
    }

    /**
     * getter
     * @param p
     * @return
     */
    public Color getIntensity(Point3D p) {
        double distance = position.distance(p);
        double attenuationCoef = kC + kL * distance + kQ * distance * distance;

        return getIntensity(p).scale(Math.exp(-attenuationCoef * distance));
    }

    /**
     * getter
     * @param p
     * @return normal vector of vector from p to position
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalize();

    }

    /**
     * getter
     * @param point
     * @return 0
     */
    @Override
    public double getDistance(Point3D point) {
        return 0;
    }

    /**
     * getter
     * @return null
     */
    @Override
    public Sphere getBulb() {
        return null;
    }
}
