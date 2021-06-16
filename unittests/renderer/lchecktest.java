//package renderer;
//
//import elements.AmbientLight;
//import elements.Camera;
//import elements.PointLight;
//import elements.SpotLight;
//import geometries.*;
//import org.junit.Test;
//import primitives.Color;
//import primitives.Material;
//import primitives.Point3D;
//import primitives.Vector;
//import scene.Scene;
//
//public class lchecktest {
//
//
//    /**
//     * Produce a picture of a nice picture including dozens of 10+
//     * objects with different material structures
//     */
//    @Test
//    public void twoSpheres() throws MissingResourcesException, UnsupportedOperationException {
//        Intersectable.set_actBoundingBox(true);
//        ImageWriter imageWriter = new ImageWriter("check", 500, 500);
//         Scene scene = new Scene("Test scene")
//                .setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.0002));
//scene.geometries.add(
//        new Plane(new Point3D(0,-2170,100),new Point3D(580,-2170,100),new Point3D(0,-2170,-1000))
//        .setEmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)),
//        new Plane(new Point3D(500,0,-2000),new Point3D(0,0,-2000),new Point3D(250,-500,-2000))
//                .setEmission(new Color(java.awt.Color.blue)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)),
//        new Plane(new Point3D(0 ,2170 ,-1000),new Point3D(580,2170,-100),new Point3D(0,2170,1000))
//                .setEmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setkT(0.6)));
//
//        Camera camera = new Camera(new Point3D(0, 0, 2000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(150, 150).setDistance(300);
//
//
//
//
//
//        Render render = new Render() //
//                .setImageWriter(imageWriter) //
//                .setCamera(camera) //
//                .setRayTracer(new BasicRayTracer(scene))
//                .setMultithreading(2)
//                .setDebugPrint();
//
//        render.renderImage();
//        render.writeToImage();
//    }
//}
