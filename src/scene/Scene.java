package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Scene {


    public Geometries geometries = null;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight(new Color(192, 192, 192), 1.d);
    public List<LightSource> lights;

    private final String name;

    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        lights = new LinkedList<LightSource>();
    }

    //chaining methods
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /***
     *
     * @param lights
     * @return builder pattern
     */
    public Scene setlights(LinkedList<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
