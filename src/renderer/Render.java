package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class Render {
    private Scene scene;
    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    public Render() {
    }

    public void renderImage() throws UnsupportedOperationException {
        try {
            if (scene == null || camera == null || imageWriter == null || rayTracer == null) {
                throw new MissingResourcesException("one of the arguments is missing arguments");
            }

            for (int i = 0; i < imageWriter.getNx(); i++) {
                for (int j = 0; j < imageWriter.getNy(); j++) {
                    Ray ray = camera.constructRayThroughPixel(imageWriter.getNx(), imageWriter.getNy(), i, j);
                    Color pixelColor = rayTracer.traceRay(ray);
                    imageWriter.writePixel(i, j, pixelColor);
                }
            }
        } catch (MissingResourcesException e) {
            throw new UnsupportedOperationException();
        }
    }


    public void printGrid(int interval, Color color) {
        try {
            if (imageWriter.getNy() == 0 || imageWriter.getNx() == 0) {
                throw new MissingResourcesException();
            }
        } catch (MissingResourcesException e) {
            e.getMessage();
        }

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                imageWriter.writePixel(imageWriter.getNx()/interval,imageWriter.getNy()/interval,color);
            }
        }
    }


    public void writeToImage() {
        imageWriter.writeToImage();
    }


    public Render setScene(Scene scene) {

        this.scene = scene;
        return this;
    }


    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {

        this.rayTracer = rayTracer;
        return this;
    }
}
