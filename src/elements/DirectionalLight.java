package elements;

import geometries.Sphere;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;
    protected Sphere bulb;

    /***
     * ctor that sets intensity and direction
     * @param intensity
     * @param direction
     */
    protected DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return this.direction.normalize();
    }

    @Override
    public double getDistance(Point3D point) {
        return Double.POSITIVE_INFINITY;
    }

    @Override
    public Sphere getBulb() {
        return  new Sphere(Double.POSITIVE_INFINITY, new Point3D( Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
    }
}
