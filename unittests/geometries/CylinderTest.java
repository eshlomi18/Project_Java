package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;


public class CylinderTest {

    @Test
    public void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: side
        Cylinder pl = new Cylinder(new Ray(new Point3D(0, 0, 1), new Vector(new Point3D(1, 0, 0))), 5.0f, 2.0f);
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3),
                pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to cylinder");
        // TC02:first base
        // TC03:second base

        // =============== Boundary Values Tests ==================

        // TC10: first center base
        // TC11: second center base

    }
}