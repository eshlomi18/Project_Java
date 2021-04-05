package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {
    Point3D p1 = new Point3D(1, 2, 3);

    @Test
    void testAdd() {

        assertEquals(Point3D.ZERO,
                (p1.add(new Vector(-1, -2, -3))),
                "ERROR: Point + Vector does not work correctly");


    }

    @Test
    void testSubtract() {
        assertEquals(new Vector(1, 1, 1),
                new Point3D(2, 3, 4).subtract(p1),
                "ERROR: Point - Point does not work correctly");
    }
}