package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.Sphere;
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
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere(50, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setkT(0.3)),
                new Sphere(25, new Point3D(0, 0, -50)) //
                        .setEmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("nicePict", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }
}
