package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

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
        return this.direction;
    }
}
