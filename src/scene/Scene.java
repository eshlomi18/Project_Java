package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Box;
import geometries.Geometries;
import primitives.Color;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Scene {


    public Geometries geometries = null;
    public Color background = Color.BLACK;
    public AmbientLight ambientLight = new AmbientLight();
    public List<LightSource> lights = new LinkedList<>();
    private Node<Geometries> geometriesTree;
    private final String name;

    /**
     * Hierarchical construction of geometries of the scene
     *
     * @param <T> generic, used T = Geometries
     */
    public class Node<T> extends Geometries {

        private T data = null;
        private List<Node<T>> children = new ArrayList<>();
        private Node<T> parent = null;

        /**
         * CTOR with parameter
         *
         * @param data geometries
         */
        public Node(T data) {
            this.data = data;
        }

        /**
         * add child node
         *
         * @param child node to add
         * @return the child
         */
        public Node<T> addChild(Node<T> child) {
            child.setParent(this);
            this.children.add(child);
            return child;
        }

        /**
         * getter of node's children
         *
         * @return list of children
         */
        public List<Node<T>> getChildren() {
            return children;
        }

        /**
         * getter of data
         *
         * @return the data, used geometry
         */
        public T getData() {
            return data;
        }

        /**
         * setter parent of node
         *
         * @param parent to set
         */
        private void setParent(Node<T> parent) {
            this.parent = parent;
        }
    }

    public Scene(String name) {
        this.name = name;
        geometries = new Geometries();
        lights = new LinkedList<LightSource>();
        geometriesTree = new Node<Geometries>(new Box());

    }
    //region getters/setters

    /**
     * get the name of the scene
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * get the color of the background
     *
     * @return the color of the background
     */
    public Color getBackground() {
        return background;
    }

    /**
     * get the ambientLight
     *
     * @return the ambientLight
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * get all of shapes of the image
     *
     * @return the list of geometries in the picture
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * get all light of the scene
     *
     * @return list of lights
     */
    public List<LightSource> getLights() {
        return lights;
    }

    /**
     * getter of root of the tree
     *
     * @return root of the tree
     */
    public Node<Geometries> getGeometriesTree() {
        return geometriesTree;
    }


    /**
     * set the color of the background of the screen
     *
     * @param _background type Color
     * @return
     */
    public Scene setBackground(Color _background) {
        this.background = _background;
        return this;
    }

    /**
     * set the light of the scene
     *
     * @param _ambientLight type AmbientLight
     * @return
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
