package elements;

import org.junit.Test;

import geometries.*;
import primitives.*;
import renderer.*;
import renderer.UnsupportedOperationException;
import scene.Scene;

/**
 * Test rendering a basic image
 *
 * @author Dan
 */
public class LightsTests {
    private Scene scene1 = new Scene("Test scene");
    private Scene scene2 = new Scene("Test scene")//
            .setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
    private Camera camera1 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(150, 150) //
            .setDistance(1000);
    private Camera camera2 = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200) //
            .setDistance(1000);

    private static Geometry triangle1 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(150, -150, -150), new Point3D(75, 75, -150));
    private static Geometry triangle2 = new Triangle( //
            new Point3D(-150, -150, -150), new Point3D(-70, 70, -50), new Point3D(75, 75, -150));
    private static Geometry sphere = new Sphere(50, new Point3D(0, 0, -50)) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100));

    /**
     * Produce a picture of a sphere lighted by a directional light
     */
    @Test
    public void sphereDirectional() throws MissingResourcesException, UnsupportedOperationException {


        scene1.geometries.add(sphere);
        scene1.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("lightSphereDirectional", 500, 500);
       // Render render = new Render()//
       //         .setImageWriter(imageWriter) //
       //         .setCamera(camera1) //
       //         .setRayTracer(new BasicRayTracer(scene1));

        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a point light
     */
    @Test
    public void spherePoint() throws MissingResourcesException, UnsupportedOperationException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new PointLight(new Color(500, 300, 0), new Point3D(-50, -50, 50))//
                .setKl(0.00001).setKq(0.000001));

        ImageWriter imageWriter = new ImageWriter("lightSpherePoint", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void sphereSpot() throws MissingResourcesException, UnsupportedOperationException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
                .setKl(0.00001).setKq(0.00000001));

        ImageWriter imageWriter = new ImageWriter("lightSphereSpot", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void SphereMultiLightSource() throws MissingResourcesException, UnsupportedOperationException {
        scene1.geometries.add(sphere);
        scene1.lights.add(new SpotLight(new Color(500, 300, 0), new Point3D(-50, -50, 50), new Vector(1, 1, -2)) //
                .setKl(0.00001).setKq(0.001));
        scene1.lights.add(new PointLight(new Color(100, 200, 200), new Point3D(50, 300, -50))//
                .setKl(0.00001).setKq(0.000001));
        scene1.lights.add(new DirectionalLight(new Color(200, 300, 50), new Vector(1, 1, -1)));

        ImageWriter imageWriter = new ImageWriter("SphereMultiLightSource", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera1) //
                .setRayTracer(new BasicRayTracer(scene1))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }
    /**
     * Produce a picture of a two triangles lighted by a directional light
     */
    @Test
    public void trianglesDirectional() throws MissingResourcesException, UnsupportedOperationException {
        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(300)));
        scene2.lights.add(new DirectionalLight(new Color(300, 150, 150), new Vector(0, 0, -1)));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesDirectional", 500, 500);
        Render render = new Render()//
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a point light
     */
    @Test
    public void trianglesPoint() throws MissingResourcesException, UnsupportedOperationException {
        Intersectable.set_actBoundingBox(true);

        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)), //
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new PointLight(new Color(500, 250, 250), new Point3D(10, -10, -130)) //
                .setKl(0.0005).setKq(0.0005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesPoint", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light
     */
    @Test
    public void trianglesSpot() throws MissingResourcesException, UnsupportedOperationException {
        Intersectable.set_actBoundingBox(true);

        scene2.geometries.add(triangle1.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),
                triangle2.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)));
        scene2.lights.add(new SpotLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-2, -2, -1)) //
                .setKl(0.0001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("lightTrianglesSpot", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera2) //
                .setRayTracer(new BasicRayTracer(scene2))
                .setMultithreading(2)
                .setDebugPrint();
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a narrow light
     */
//    @Test
//    public void trianglesNarrow() throws MissingResourcesException, UnsupportedOperationException {
//        scene2.geometries.add(triangle1.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)),
//                triangle2.setMaterial(new Material().setkD(0.5).setkS(0.5).setnShininess(300)));
//        scene2.lights.add(new NarrowLight(new Color(500, 250, 250), new Point3D(10, -10, -130), new Vector(-10, -5, -10)) //
//                .setkL(0.0001).setkQ(0.000005));
//        ImageWriter imageWriter = new ImageWriter("lightTrianglesNarrow", 500, 500);
//        Render render = new Render()//
//                .setImageWriter(imageWriter) //
//                .setCamera(camera2) //
//                .setRayTracer(new RayTracerBasic(scene2));
//        render.renderImage();
//        render.writeToImage();
//    }

}
