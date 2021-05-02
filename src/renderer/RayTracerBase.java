package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * abstract class which takes as parameter a scene
 * which is only protected because we want other class to inherit it
 * and an abstract method trace ray
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * constructor
     *
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * abstract method suppose to return us
     * the color of a given pixel
     *
     * @param ray
     * @return
     */
    public abstract Color traceRay(Ray ray);
}
