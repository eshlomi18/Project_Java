package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

public class Render {

    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private Scene scene;

    public Render() {
    }
    boolean flag = true;
    public void renderImage() throws UnsupportedOperationException, MissingResourcesException {
        try {
            if (imageWriter == null) {
                throw new MissingResourcesException("missing resource", ImageWriter.class.getName(), "");
            }
            if (imageWriter == null) {
                throw new MissingResourcesException("missing resource", Scene.class.getName(), "");
            }
            if (imageWriter == null) {
                throw new MissingResourcesException("missing resource", Camera.class.getName(), "");
            }
            if (imageWriter == null) {
                throw new MissingResourcesException("missing resource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();
            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor = rayTracer.traceRay(ray);
                    imageWriter.writePixel(j, i, pixelColor);
                }
            }
        } catch (MissingResourcesException e) {
            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
        }
    }


    public void printGrid(int interval, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }


    public void writeToImage() {
        imageWriter.writeToImage();
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

    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }
}
