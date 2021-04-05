package primitives;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * tests for vector
 *
 * @author evyatar shlomi and samuel verse
 */
class VectorTest {
    Vector v = new Vector(1, 2, 3);
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);


    @Test
    void testVectorZero() {
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "vector zero is not allowed");

    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(),
                vr.length(), 0.00001,
                "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class,
                () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
        // try {
        //     v1.crossProduct(v2);
        //     fail("crossProduct() for parallel vectors does not throw an exception");
        // } catch (Exception e) {}
    }

    @Test
    void testAdd() {
        Vector addvector = v1.add(v2);
        Vector result = new Vector(-1, -2, -3);
        assertEquals(addvector, result, "wrong value");
    }

    @Test
    void testSubtract() {
        Vector sub = v1.subtract(v2);
        Vector result = new Vector(3, 6, 9);
        assertEquals(sub, result, "wrong value");

    }

    @Test
    void testScale() {
        Vector scale = v1.scale(-1);
        assertEquals(new Vector(-1, -2, -3), scale, "delta too small");
    }


    @Test
    void testDotProduct3() {
        Vector v3 = new Vector(0, 3, -2);
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");


    }


    @Test
    void testLengthSquared() {
        assertTrue(isZero(v1.lengthSquared() - 14), "ERROR: lengthSquared() wrong value");


    }

    @Test
    void testLength() {
        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertTrue(isZero(vCopyNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");

    }

    @Test
    void testNormalize() {
        // test vector normalization vs vector length and cross-product

        Vector vCopy = new Vector(v.getHead());
        Vector vCopyNormalize = vCopy.normalize();
        assertEquals(vCopy, vCopyNormalize, "ERROR: normalize() function creates a new vector");


    }

    @Test
    void testNormalized() {
        Vector u = v.normalized();
        assertNotEquals(u, v, "ERROR: normalizated() function does not create a new vector");


    }
}