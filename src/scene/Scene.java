package scene;

import elements.AmbientLight;
import elements.LightSource;
//import geometries.Box;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Scene {


    public Geometries geometries = null;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public List<LightSource> lights = new LinkedList<>();
    //  private Node<Geometries> geometriesTree;
    private final String name;


    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        lights = new LinkedList<LightSource>();
    }


    /**
     * set the color of the background of the screen
     * @param _background type Color
     * @return design pattern
     */
    public Scene setBackground(Color _background) {
        this.background = _background;
        return this;
    }

    /**
     * set the light of the scene
     *
     * @param _ambientLight type AmbientLight
     * @return design pattern
     */
    public Scene setAmbientLight(AmbientLight _ambientLight) {
        this.ambientLight = _ambientLight;
        return this;
    }

    /**
     * set the List of shapes to represent the picture
     *
     * @param _geometries type Geometries
     */
    public void setGeometries(Geometries _geometries) {
        this.geometries = _geometries;
    }

    //endregion


    /**
     * add many geometries in the the group of geometries of the picture
     * @param geometries type Intersectable
     */
    public void addGeometries(Intersectable... geometries) {
        this.geometries.add(geometries);
        for (int i = 0; i < geometries.length; i++)
            this.geometries.add(geometries[i]);
    }
}
