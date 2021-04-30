package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    public Color traceRay(Ray ray) {
        return null;
    }
}
