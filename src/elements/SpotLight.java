package elements;

import primitives.*;
import primitives.Util;

import static primitives.Util.*;

public class SpotLight extends PointLight {
    private final Vector direction;

    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalized();
    }

    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double cosTheta=direction.dotProduct(getL(p));
        return super.getIntensity(p).scale(Math.max(0,cosTheta));
    }
}
