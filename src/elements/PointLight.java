package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    private Point3D position;
    private double kC;
    private double kL;
    private double kQ;

    /***
     *
     * @param intensity
     * @param position
     */
    protected PointLight(Color intensity, Point3D position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }

    /***
     *
     * @param intensity
     * @param position
     * @param kC
     * @param kL
     * @param kQ
     */
    protected PointLight(Color intensity, Point3D position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }


    @Override
    public Color getIntensity(Point3D p) {

        double denominator, distance;
        distance = position.distance(p);
        denominator = kC + kL * distance + kQ * distance * distance;
        return getIntensity().reduce(denominator);
    }

    //TODO:comprendre pk sub p;
    @Override
    public Vector getL(Point3D p) {
        return position.subtract(p);
    }


    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}