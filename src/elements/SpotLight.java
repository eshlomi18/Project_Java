package elements;

import primitives.*;
import primitives.Util;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class SpotLight extends PointLight {
    private Vector direction;

    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    public SpotLight(Color intensity, Point3D position, Vector direction, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction;
    }

    //TODO:multiplication par i0?
    @Override
    public Color getIntensity(Point3D p) {
        double factor = alignZero(Math.max(0, direction.dotProduct(getL(p))));
        return super.getIntensity().scale(factor);
    }

    //TODO:normalized?
    @Override
    public Vector getL(Point3D p) {
        return direction.normalized();
    }
}
