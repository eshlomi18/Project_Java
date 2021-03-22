package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import static org.junit.Assert.*;

public class CylinderTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Cylinder pl = new Cylinder(new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(1, 0, 0))), 5.0f, 2.0f);
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to cylinder", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
}