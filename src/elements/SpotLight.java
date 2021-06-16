package elements;

import primitives.*;
import primitives.Util;

import static primitives.Util.*;

public class SpotLight extends PointLight {
    private final Vector direction;

    /**
     * constructor
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    /**
     * constructor
     * @param intensity
     * @param position
     * @param direction
     * @param kC
     * @param kL
     * @param kQ
     */
    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalized();
    }

    /**
     * getter - calculate the intensity at the point where the point light arrived
     * @param p the point of the shape the light arrived
     * @return intensity
     */
    @Override
    public Color getIntensity(Point3D p) {
        double cosTheta=direction.dotProduct(getL(p));
        return super.getIntensity(p).scale(Math.max(0,cosTheta));
    }
}
