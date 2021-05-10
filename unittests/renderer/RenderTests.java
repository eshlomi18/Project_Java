package renderer;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class RenderTests {
    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setDistance(100) //
            .setViewPlaneSize(500, 500);

    /**
     * Produce a scene with basic 3D model and render it into a png image with a
     * grid
     */
    @Test
    public void basicRenderTwoColorTest() throws MissingResourcesException, UnsupportedOperationException {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
                .setBackground(new Color(75, 127, 90));

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
                // right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
                // left
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
        // right

        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    /**
     * Test for XML based scene - for bonus
     */
    @Test
    public void basicRenderXml() throws MissingResourcesException, UnsupportedOperationException {
        Scene scene = new Scene("XML Test scene");
        // enter XML file name and parse from XML file into scene object
        // ...

        ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.YELLOW));
        render.writeToImage();
    }

    // For stage 6 - please disregard in stage 5
    /**
     * Produce a scene with basic 3D model - including individual lights of the bodies
     * and render it into a png image with a grid
     */
    @Test
    public void basicRenderMultiColorTest() throws MissingResourcesException, UnsupportedOperationException {
        Scene scene = new Scene("Test scene")//
                .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //

        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50) //
                        .setEmission(new Color(java.awt.Color.CYAN)), //
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)) // up left
                        .setEmission(new Color(java.awt.Color.GREEN)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)) // down left
                        .setEmission(new Color(java.awt.Color.RED)),
                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100)) // down right
                        .setEmission(new Color(java.awt.Color.BLUE)));

        ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene));

        render.renderImage();
        render.printGrid(100, new Color(java.awt.Color.WHITE));
        render.writeToImage();
    }
}

//package renderer;
//
//import org.junit.Test;
//
//import java.beans.XMLDecoder;
//import java.beans.PersistenceDelegate;
//import java.beans.XMLEncoder;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.File;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.DocumentBuilder;
//
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;
//import org.w3c.dom.Element;
//
//import elements.*;
//import geometries.*;
//import primitives.*;
//import renderer.*;
//import scene.Scene;
//
///**
// * Test rendering a basic image
// *
// * @author Dan
// */
//public class RenderTests {
//    private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//            .setDistance(100) //
//            .setViewPlaneSize(500, 500);
//
//    /**
//     * Produce a scene with basic 3D model and render it into a jpeg image with a
//     * grid
//     */
//    @Test
//    public void basicRenderTwoColorTest() throws UnsupportedOperationException, MissingResourcesException {
//
//        Scene scene = new Scene("Test scene")//
//                .setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)) //
//                .setBackground(new Color(75, 127, 90));
//
//        scene.geometries.add(new Sphere(new Point3D(0, 0, -100), 50),
//                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up left
//                new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up right
//                new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down left
//                new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down right
//
//        ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
//        Render render = new Render() //
//                .setImageWriter(imageWriter) //
//                .setCamera(camera) //
//                .setRayTracer(new RayTracerBasic(scene));
//
//        render.renderImage();
//        render.printGrid(100, new Color(java.awt.Color.YELLOW));
//        render.writeToImage();
//    }
//}
//    /**
//     * Test for XML based scene - for bonus
//     */
//// @Test
//// public void basicRenderXml() throws UnsupportedOperationException, MissingResourcesException, FileNotFoundException {
////     Scene scene = new Scene("basicRenderTestTwoColors.xml");
////     // String filePath = "C:\\Users\\shmouel\\IdeaProjects\\Project_Java\\unittests\\renderer\\basicRenderTestTwoColors.xml";
////     // XMLDecoder decoder = new XMLDecoder(new FileInputStream(filePath));
////     // try {
////     //     // deserialisation of the object
////     //     scene = (Scene) decoder.readObject();
////     // } finally {
////     //     // close  decoder
////     //     decoder.close();
////     // }
////     try {
////         File inputFile = new File("C:\\Users\\shmouel\\IdeaProjects\\Project_Java\\unittests\\renderer\\basicRenderTestTwoColors.xml");
////         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
////         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
////         Document doc = dBuilder.parse(inputFile);
////         doc.getDocumentElement().normalize();
////         NodeList nList = doc.getElementsByTagName("student");
////         for (int temp = 0; temp < nList.getLength(); temp++) {
////             Node nNode = nList.item(temp);
//
////             if (nNode.getNodeType() == Node.ELEMENT_NODE) {
////                 Element eElement = (Element) nNode;
////                Scene scene1= new Scene("test")
////                        .setBackground(eElement.getAttribute("background-color"))
////                        .setAmbientLight(eElement.getElementsByTagName("ambient-light") .item(0).getTextContent())
////                        .setGeometries()
//
////               //  System.out.println("First Name : "
////               //          + eElement
////               //          .getElementsByTagName("firstname")
////               //          .item(0)
////               //          .getTextContent());
//
////             }
////         }
////     } catch (Exception e) {
////         e.printStackTrace();
////     }
//
////     ImageWriter imageWriter = new ImageWriter("xml render test", 1000, 1000);
////     Render render = new Render() //
////             .setImageWriter(imageWriter) //
////             .setScene(scene) //
////             .setCamera(camera) //
////             .setRayTracer(new RayTracerBasic(scene));
//
////     render.renderImage();
////     render.printGrid(100, new Color(java.awt.Color.YELLOW));
////     render.writeToImage();
//// }
//
//
////
