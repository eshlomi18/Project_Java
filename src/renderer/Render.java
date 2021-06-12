package renderer;

import primitives.*;
import elements.*;

import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 *
 * @author Dan
 *
 */
public class Render {
    private Camera camera;
    private ImageWriter imageWriter;
    private RayTracerBase tracer;
    private static final String RESOURCE_ERROR = "Renderer resource not set";
    private static final String RENDER_CLASS = "Render";
    private static final String IMAGE_WRITER_COMPONENT = "Image writer";
    private static final String CAMERA_COMPONENT = "Camera";
    private static final String RAY_TRACER_COMPONENT = "Ray tracer";

    private int threadsCount = 0;
    private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
    private boolean print = false; // printing progress percentage

    /**
     * Set multi-threading <br>
     * - if the parameter is 0 - number of cores less 2 is taken
     *
     * @param threads number of threads
     * @return the Render object itself
     */
    public Render setMultithreading(int threads) {
        if (threads < 0)
            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
        if (threads != 0)
            this.threadsCount = threads;
        else {
            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
            this.threadsCount = cores <= 2 ? 1 : cores;
        }
        return this;
    }

    /**
     * Set debug printing on
     *
     * @return the Render object itself
     */
    public Render setDebugPrint() {
        print = true;
        return this;
    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     *
     * @author Dan
     *
     */
    private class Pixel {
        private long maxRows = 0;
        private long maxCols = 0;
        private long pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long counter = 0;
        private int percents = 0;
        private long nextCounter = 0;

        /**
         * The constructor for initializing the main follow up Pixel object
         *
         * @param maxRows the amount of pixel rows
         * @param maxCols the amount of pixel columns
         */
        public Pixel(int maxRows, int maxCols) {
            this.maxRows = maxRows;
            this.maxCols = maxCols;
            this.pixels = (long) maxRows * maxCols;
            this.nextCounter = this.pixels / 100;
            if (Render.this.print)
                System.out.printf("\r %02d%%", this.percents);
        }

        /**
         * Default constructor for secondary Pixel objects
         */
        public Pixel() {
        }

        /**
         * Internal function for thread-safe manipulating of main follow up Pixel object
         * - this function is critical section for all the threads, and main Pixel
         * object data is the shared data of this critical section.<br/>
         * The function provides next pixel number each call.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return the progress percentage for follow up: if it is 0 - nothing to print,
         *         if it is -1 - the task is finished, any other value - the progress
         *         percentage (only when it changes)
         */
        private synchronized int nextP(Pixel target) {
            ++col;
            ++this.counter;
            if (col < this.maxCols) {
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            ++row;
            if (row < this.maxRows) {
                col = 0;
                target.row = this.row;
                target.col = this.col;
                if (Render.this.print && this.counter == this.nextCounter) {
                    ++this.percents;
                    this.nextCounter = this.pixels * (this.percents + 1) / 100;
                    return this.percents;
                }
                return 0;
            }
            return -1;
        }

        /**
         * Public function for getting next pixel number into secondary Pixel object.
         * The function prints also progress percentage in the console window.
         *
         * @param target target secondary Pixel object to copy the row/column of the
         *               next pixel
         * @return true if the work still in progress, -1 if it's done
         */
        public boolean nextPixel(Pixel target) {
            int percent = nextP(target);
            if (Render.this.print && percent > 0)
                synchronized (this) {
                    notifyAll();
                }
            if (percent >= 0)
                return true;
            if (Render.this.print)
                synchronized (this) {
                    notifyAll();
                }
            return false;
        }

        /**
         * Debug print of progress percentage - must be run from the main thread
         */
        public void print() {
            if (Render.this.print)
                while (this.percents < 100)
                    try {
                        synchronized (this) {
                            wait();
                        }
                        System.out.printf("\r %02d%%", this.percents);
                        System.out.flush();
                    } catch (Exception e) {
                    }
        }
    }

    /**
     * Camera setter
     *
     * @param camera to set
     * @return renderer itself - for chaining
     */
    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    /**
     * Image writer setter
     *
     * @param imgWriter the image writer to set
     * @return renderer itself - for chaining
     */
    public Render setImageWriter(ImageWriter imgWriter) {
        this.imageWriter = imgWriter;
        return this;
    }

    /**
     * Ray tracer setter
     *
     * @param tracer to use
     * @return renderer itself - for chaining
     */
    public Render setRayTracer(RayTracerBase tracer) {
        this.tracer = tracer;
        return this;
    }

    /**
     * Produce a rendered image file
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        imageWriter.writeToImage();
    }

    /**
     * Cast ray from camera in order to color a pixel
     * @param nX resolution on X axis (number of pixels in row)
     * @param nY resolution on Y axis (number of pixels in column)
     * @param col pixel's column number (pixel index in row)
     * @param row pixel's row number (pixel index in column)
     */
    private void castRay(int nX, int nY, int col, int row) {
        Ray ray = camera.constructRayThroughPixel(nX, nY, col, row);
        Color color = tracer.traceRay(ray);
        imageWriter.writePixel(col, row, color);
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object - with multi-threading
     */
    private void renderImageThreaded() {
        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        final Pixel thePixel = new Pixel(nY, nX);
        // Generate threads
        Thread[] threads = new Thread[threadsCount];
        for (int i = threadsCount - 1; i >= 0; --i) {
            threads[i] = new Thread(() -> {
                Pixel pixel = new Pixel();
                while (thePixel.nextPixel(pixel))
                    castRay(nX, nY, pixel.col, pixel.row);
            });
        }
        // Start threads
        for (Thread thread : threads)
            thread.start();

        // Print percents on the console
        thePixel.print();

        // Ensure all threads have finished
        for (Thread thread : threads)
            try {
                thread.join(); //wait until previous threads finish
            } catch (Exception e) {
            }

        if (print)
            System.out.print("\r100%");
    }

    /**
     * This function renders image's pixel color map from the scene included with
     * the Renderer object
     */
    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
        if (camera == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
        if (tracer == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

        final int nX = imageWriter.getNx();
        final int nY = imageWriter.getNy();
        if (threadsCount == 0)
            for (int i = 0; i < nY; ++i)
                for (int j = 0; j < nX; ++j)
                    castRay(nX, nY, j, i);
        else
            renderImageThreaded();
    }

    /**
     * Create a grid [over the picture] in the pixel color map. given the grid's
     * step and color.
     *
     * @param step  grid's step
     * @param color grid's color
     */
    public void printGrid(int step, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; ++i)
            for (int j = 0; j < nX; ++j)
                if (j % step == 0 || i % step == 0)
                    imageWriter.writePixel(j, i, color);
    }
}



//package renderer;
//
//import elements.Camera;
//import geometries.Intersectable;
//import primitives.Color;
//import primitives.Ray;
//import scene.Scene;
//
//public class Render {
//
//    private Camera camera;
//    private ImageWriter imageWriter;
//    private RayTracerBase rayTracer;
//    private Scene scene;
//    private int _threads = 1;
//    private final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
//    private boolean _print = false; // printing progress percentage
//    private static int count = 0;
//
//
//    public Render() {
//    }
//
//    boolean flag = true;
//
//    private class Pixel {
//        private long _maxRows = 0;
//        private long _maxCols = 0;
//        private long _pixels = 0;
//        public volatile int row = 0;
//        public volatile int col = -1;
//        private long _counter = 0;
//        private int _percents = 0;
//        private long _nextCounter = 0;
//
//        /**
//         * The constructor for initializing the main follow up Pixel object
//         *
//         * @param maxRows the amount of pixel rows
//         * @param maxCols the amount of pixel columns
//         */
//        public Pixel(int maxRows, int maxCols) {
//            _maxRows = maxRows;
//            _maxCols = maxCols;
//            _pixels = maxRows * maxCols;
//            _nextCounter = _pixels / 100;
//            if (Render.this._print) synchronized (System.out) {
//                System.out.printf("\r %02d%%", _percents);
//            }
//        }
//
//        /**
//         * Default constructor for secondary Pixel objects
//         */
//        public Pixel() {
//        }
//
//        /**
//         * Internal function for thread-safe manipulating of main follow up Pixel object - this function is
//         * critical section for all the threads, and main Pixel object data is the shared data of this critical
//         * section.<br/>
//         * The function provides next pixel number each call.
//         *
//         * @param target target secondary Pixel object to copy the row/column of the next pixel
//         * @return the progress percentage for follow up: if it is 0 - nothing to print, if it is -1 - the task is
//         * finished, any other value - the progress percentage (only when it changes)
//         */
//        private synchronized int nextP(Pixel target) {
//            ++col;
//            ++_counter;
//            if (col < _maxCols) {
//                target.row = this.row;
//                target.col = this.col;
//                if (_print && _counter == _nextCounter) {
//                    ++_percents;
//                    _nextCounter = _pixels * (_percents + 1) / 100;
//                    return _percents;
//                }
//                return 0;
//            }
//            ++row;
//            if (row < _maxRows) {
//                col = 0;
//                if (_print && _counter == _nextCounter) {
//                    ++_percents;
//                    _nextCounter = _pixels * (_percents + 1) / 100;
//                    return _percents;
//                }
//                return 0;
//            }
//            return -1;
//        }
//
//        /**
//         * Public function for getting next pixel number into secondary Pixel object.
//         * The function prints also progress percentage in the console window.
//         *
//         * @param target target secondary Pixel object to copy the row/column of the next pixel
//         * @return true if the work still in progress, -1 if it's done
//         */
//        public boolean nextPixel(Pixel target) {
//            int percents = nextP(target);
//            if (_print && percents > 0)
//                synchronized (System.out) {
//                    System.out.printf("\r %02d%%", percents);
//                }
//            if (percents >= 0)
//                return true;
//            if (_print) synchronized (System.out) {
//                System.out.printf("\r %02d%%", 100);
//            }
//            return false;
//        }
//    }
//
//    /**
//     * Set multithreading <br>
//     * - if the parameter is 0 - number of coress less 2 is taken
//     *
//     * @param threads number of threads
//     * @return the Render object itself
//     */
//    public Render setMultithreading(int threads) {
//        if (threads < 0)
//            throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
//        if (threads != 0)
//            _threads = threads;
//        else {
//            int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
//            _threads = cores <= 2 ? 1 : cores;
//        }
//        return this;
//    }
//
//    public void renderImage() throws UnsupportedOperationException, MissingResourcesException {
//        try {
//            if (imageWriter == null) {
//                throw new MissingResourcesException("missing resource", ImageWriter.class.getName(), "");
//            }
//            if (imageWriter == null) {
//                throw new MissingResourcesException("missing resource", Scene.class.getName(), "");
//            }
//            if (imageWriter == null) {
//                throw new MissingResourcesException("missing resource", Camera.class.getName(), "");
//            }
//            if (imageWriter == null) {
//                throw new MissingResourcesException("missing resource", RayTracerBase.class.getName(), "");
//            }
//            final Camera camera = getCamera();
//            final Intersectable geometries = scene.getGeometries();
//            final java.awt.Color background = scene.getBackground().getColor();
//
//            final int nX = imageWriter.getNx();
//            final int nY = imageWriter.getNy();
//
//            // Multi-threading
//            final Pixel thePixel = new Pixel(nY, nX);
//            // Generate threads
//            Thread[] threads = new Thread[_threads];
//            for (int i = _threads - 1; i >= 0; --i) {
//                threads[i] = new Thread(() -> {
//                    Pixel pixel = new Pixel();
//                    while (thePixel.nextPixel(pixel)) {
//                        if (count == 33) {
//                            count = 33;
//                        }
//                        Ray ray = camera.constructRayThroughPixel(nX, nY, pixel.col, pixel.row);
//                        //        Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
//
//                        Intersectable.GeoPoint closestPoint = findClosestIntersection(ray, improvementCheckIntersection);
//                        count++;
//
//
//                        imageWriter.writePixel(pixel.col, pixel.row, closestPoint == null ? background :
//                                calcColor(closestPoint, ray, ACTIVATE, improvementCheckIntersection).getColor());
//                        //              imageWriter.writePixel(j, i, pixelColor);
//
//                        System.out.println(count);
//                    }
//
//                });
//            }
//            for (Thread thread : threads) thread.start();
//            for (Thread thread : threads)
//                try {
//                    thread.join();
//                } catch (InterruptedException e) {
//                }
//            if (_print) synchronized (System.out) {
//                System.out.printf("\r100%%\n");
//            }
//            //rendering the image
//            //      int nX = imageWriter.getNx();
//            //      int nY = imageWriter.getNy();
//            //      for (int i = 0; i < nY; i++) {
//            //          for (int j = 0; j < nX; j++) {
//            //              Ray ray = camera.constructRayThroughPixel(nX, nY, j, i);
//            //              Color pixelColor = rayTracer.traceRay(ray);
//            //              imageWriter.writePixel(j, i, pixelColor);
//            //          }
//            //      }
//        } catch (MissingResourcesException e) {
//            throw new UnsupportedOperationException("Not implemented yet" + e.getClassName());
//        }
//    }
//
//
//    public void printGrid(int interval, Color color) {
//        int nX = imageWriter.getNx();
//        int nY = imageWriter.getNy();
//        for (int i = 0; i < nY; i++) {
//            for (int j = 0; j < nX; j++) {
//                if (i % interval == 0 || j % interval == 0) {
//                    imageWriter.writePixel(j, i, color);
//                }
//            }
//        }
//    }
//
//
//    public void writeToImage() {
//        imageWriter.writeToImage();
//    }
//
//
//    public Render setCamera(Camera camera) {
//        this.camera = camera;
//        return this;
//    }
//
//    public Render setImageWriter(ImageWriter imageWriter) {
//        this.imageWriter = imageWriter;
//        return this;
//    }
//
//    public Render setRayTracer(RayTracerBase rayTracer) {
//
//        this.rayTracer = rayTracer;
//        return this;
//    }
//
//    public Render setScene(Scene scene) {
//        this.scene = scene;
//        return this;
//    }
//
//    public Camera getCamera() {
//        return camera;
//    }
//}
