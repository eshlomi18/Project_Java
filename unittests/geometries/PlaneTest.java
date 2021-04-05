package geometries;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

public class PlaneTest {

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: build correct plane
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0));
            fail("Failed constructing a correct plane");
        } catch (IllegalArgumentException e) {
        }

        // =============== Boundary Values Tests ==================

        // TC10: plane with first and second point coalesces
        try {
            new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0),
                    new Point3D(0, 1, 0));
            fail("constructed a plane with first and second point coalesces");
        } catch (IllegalArgumentException e) {
        }
        //TC11: plane with points on the same straight
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 2),
                    new Point3D(0, 0, 3));
            fail("constructed a plane with points on the same straight");
        } catch (IllegalArgumentException e) {
        }

    }

    @Test
    public void getNormal() {
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals("Bad normal to plane", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
    }
}
