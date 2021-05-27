package renderer;

import elements.AmbientLight;
import elements.Camera;
import elements.SpotLight;
import geometries.*;
import org.junit.Test;
import primitives.*;
import scene.Scene;

public class shadRefleREfract {
    private Scene scene = new Scene("Test scene");//
    ;
    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200).setDistance(1000);

    @Test
    public void trianglesSphere() throws MissingResourcesException, UnsupportedOperationException {

        scene.geometries.add( //
             // new Tube(new Ray(new Point3D(-60,-20,40),new Vector(new Point3D( -20,-20,25))),20.0)
             //         .setEmission(new Color(java.awt.Color.green)) //
             //         .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
               new Sphere(100, new Point3D(0, 0, -200)) //
                       .setEmission(new Color(java.awt.Color.BLUE)) //
                       .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(30).setkT(0.6)), //
               new Polygon(new Point3D(75, 0, 0), new Point3D(0, -75, 0),new Point3D(-75, 0, 0),new Point3D(0, 75, 0)) //
                       .setEmission(new Color(java.awt.Color.green)) //
                       .setMaterial(new Material().setKd(0.1).setKs(0.1).setShininess(30).setkT(0.6)), //
                new Triangle(new Point3D(-50,-45,0), new Point3D(-60,-10,0), new Point3D(-20,-28,0)) //
                        .setEmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(10).setkT(0.6))
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("alltogether", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
}
