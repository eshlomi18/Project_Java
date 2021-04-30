package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {

    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point3D> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) return scene.background;
        Point3D closestPoint = ray.getClosestPoint(intersections);
        return calcColor(closestPoint);
    }

    private Color calcColor(Point3D point) {
        return scene.ambientLight.getIntensity();
    }
}
