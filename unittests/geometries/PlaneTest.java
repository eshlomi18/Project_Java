package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;


public class PlaneTest {

    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: build correct plane
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // =============== Boundary Values Tests ==================

        // TC10: plane with first and second point coalesces
        try {
            new Plane(new Point3D(1, 0, 0), new Point3D(1, 1, 0),
                    new Point3D(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("constructed a plane with first and second point coalesces");
        }
        //TC11: plane with points on the same straight
        try {
            new Plane(new Point3D(0, 0, 1), new Point3D(0, 0, 2),
                    new Point3D(0, 0, 3));
        } catch (IllegalArgumentException e) {
            fail("constructed a plane with points on the same straight");
        }

    }

    @Test
    public void getNormal() {
        Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals( new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)),"Bad normal to plane");
    }
}
