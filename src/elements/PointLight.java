package elements;

import geometries.Sphere;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class PointLight extends Light implements LightSource {

    protected Point3D position;
    private double kC;
    private double kL;
    private double kQ;
    protected Sphere bulb;
    protected static final int RADIUS=5;

    /***
     *constructor
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
     *constructor
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

    /**
     * getter - calculate the intensity at the point where the point light arrived
     * @param p the point of the shape the light arrived
     * @return   intensity
     */
    @Override
    public Color getIntensity(Point3D p) {

        double denominator, distance;
        distance = position.distance(p);
        denominator = kC + kL * distance + kQ * distance * distance;
        return getIntensity().reduce(denominator);
    }

    /**
     * getter - find the dirction of the light that went to from the point light to the shape
     * @param p
     * @return normal vector from p to position
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(position).normalize();
    }

    /**
     * getter- get the distance for the point light after calculate
     * @param p
     * @return the distance between position and p
     */
    @Override
    public double getDistance(Point3D p) {
        return position.distance(p);
    }

    /**
     * getter
     * @return new sphere
     */
    @Override
    public Sphere getBulb() {
        return new Sphere(RADIUS,this.position);
    }

    /**
     * setter
     * @param kC
     * @return
     */
    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    /**
     * setter
     * @param kL
     * @return
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    /**
     * setter
     * @param kQ
     * @return
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
}