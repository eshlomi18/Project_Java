package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

public class TubeTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Tube pl = new Tube(new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(1, 0, 0))), 2.0);
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to tube", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
}