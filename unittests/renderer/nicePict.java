package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import scene.Scene;

public class nicePict {
    private Scene scene = new Scene("Test scene").setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)).setBackground(new Color(75, 127, 90));

    /**
     * Produce a picture of a nice picture including dozens of 10+
     * objects with different material structures
     */
    @Test
    public void twoSpheres() throws MissingResourcesException, UnsupportedOperationException {
        //   Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
        //           .setViewPlaneSize(2500, 2500).setDistance(10000); //

        //   scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        //   scene.geometries.add( //
        //           new Sphere(400, new Point3D(-950, -900, -1000)) //
        //                   .setEmission(new Color(0, 0, 100)) //
        //                   .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setkT(0.5)),
        //          // new Sphere(200, new Point3D(-950, -900, -1000)) //
        //          //         .setEmission(new Color(100, 20, 20)) //
        //          //         .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
        //          new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
        //                  new Point3D(670, 670, 3000)) //
        //                  .setEmission(new Color(20, 20, 20)) //
        //                  .setMaterial(new Material().setkR(1)),
        //          new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
        //                  new Point3D(-1500, -1500, -2000)) //
        //                  .setEmission(new Color(20, 20, 20)) //
        //                  .setMaterial(new Material().setkR(0.5)));

        //   scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
        //           .setKl(0.00001).setKq(0.000005));

        //   ImageWriter imageWriter = new ImageWriter("nicePict", 500, 500);
        //   Render render = new Render() //
        //           .setImageWriter(imageWriter) //
        //           .setCamera(camera) //
        //           .setRayTracer(new BasicRayTracer(scene))
        //           .setMultithreading(2)
        //           .setDebugPrint();

        //   render.renderImage();
        //   render.writeToImage();
        //
    }
}
