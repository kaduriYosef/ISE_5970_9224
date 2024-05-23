package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@link primitives.Vector} claas
 *
 * @author Harel Amar
 */
class VectorTest {

    /**
     * Test method for {@link primitives.Vector#equals(Object)} func}
     */
    @Test
    void testEquals() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        //Checks equality of terms between two positive vectors
        assertTrue(v1.equals(new Vector(1, 2, 3)), "ERROR: the func Vector.equals not work correctly in positive vectors");
        // Checks equality of terms between two negative vectors
        assertTrue(v2.equals(new Vector(-1, -2, -3)), "ERROR: the func Vector.equals not work correctly in negative vectors");
    }


    /**
     * Test method for {@link primitives.Vector#add(Vector)} func}
     */
    @Test
    void add() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // Tests an add function for two positive numbers
        assertEquals(new Vector(2, 4, 6), v1.add(v1), "ERROR: the func Vector.add does not work on two positive numbers");
        // Tests an add function for two negative numbers
        assertEquals(new Vector(-2, -4, -6), v2.add(v2), "ERROR: the func Vector.add does not work on two negative numbers");

        // =============== Boundary Values Tests ==================
        // Tests an add function checks for the zero vector result
        assertThrows(IllegalArgumentException.class, () -> v1.add(v2), "ERROR: the func Vector.add does not work on two numbers whose result is the zero vector");
    }

    /**
     * Test method for {@link Vector#lengthSquared()} func}
     */
    @Test
    void lengthSquared() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // Tests a lengthSquared function for positive vectors
        assertEquals(14.0, v1.lengthSquared(), "ERROR: the func Vector.lengthSquared does not work on positive vectors");
        // Tests a lengthSquared function for negative vectors
        assertEquals(14.0, v2.lengthSquared(), "ERROR: the func Vector.lengthSquared does not work on negative vectors");

    }

    /**
     * Test method for {@link Vector#length()} func}
     */
    @Test
    void length() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // Tests a length function for positive vectors
        assertEquals(Math.sqrt(14), v1.length(), "ERROR: the func Vector.length does not work on positive vectors");
        // Tests a length function for negative vectors
        assertEquals(Math.sqrt(14), v2.length(), "ERROR: the func Vector.length does not work on negative vectors");
    }

    /**
     * Test method for {@link Vector#dotProduct(Vector)} func}
     */
    @Test
    void dotProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(2, 0, 7);
        Vector v3 = new Vector(-5, -1.2, -2);

        // ============ Equivalence Partitions Tests ==============
        // Tests a dotProduct function for two positive Vectors
        assertEquals(23, v1.dotProduct(v2), "ERROR: the func Vector.dotProduct does not work on positive vectors");
        // Tests the dotProduct function for two vectors one positive and one negative
        assertEquals(-13.4, v1.dotProduct(v3), "ERROR: the func Vector.dotProduct does not work on two vectors one positive and one negative");

    }

    /**
     * Test method for {@link primitives.Vector#scale(double) func}.
     */
    @Test
    void scale() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        // Tests a scale function for positive scalar
        assertEquals(new Vector(2, 4, 6), v1.scale(2), "ERROR: the func Vector.scale does not work on positive scalar");
        // Tests a scale function for  positive scalar
        assertEquals(new Vector(-2, -4, -6), v1.scale(-2), "ERROR: the func Vector.scale does not work on positive scalar");
        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from dot-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0), "ERROR: the func Vector.scale does not work on zero number");
    }

    /**
     * Test method for {@link Vector#crossProduct(Vector)} func}
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector v3 = new Vector(2, 4, 6);
        Vector v4 = v1.crossProduct(v2);

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals(v1.length() * v2.length(), v4.length(), 0.00001, "crossProduct() wrong result length");
        // TC02: Test cross-product result orthogonality to its operands
        assertEquals(0, v4.dotProduct(v1), "crossProduct() result is not orthogonal to 1st operand");
        assertEquals(0, v4.dotProduct(v2), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-product of parallel vectors
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v3),
                "crossProduct() for parallel vectors does not throw an exception");
    }

    /**
     * Test method for {@link Vector#normalize()} func}
     */
    @Test
    void testNormalize() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // Tests a normalize function for two positive Vectors
        assertEquals(new Vector(1 / 3.7416573867739413, 2 / 3.7416573867739413, 3 / 3.7416573867739413), v1.normalize(), "ERROR: the func Vector.normalize does not work on negative vectors");
        // Tests a normalize function for two negative Vectors
        assertEquals(new Vector(-1 / 3.7416573867739413, -2 / 3.7416573867739413, -3 / 3.7416573867739413), v2.normalize(), "ERROR: the func Vector.normalize does not work on negative vectors");
    }
    /**
     * Test method for {@link Vector#subtract(Point)} ()} func}
     */
    @Test
    void testSubtract() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-1, -2, -3);

        // ============ Equivalence Partitions Tests ==============
        // Tests an subtract function for two positive numbers
        assertEquals(new Vector(2, 4, 6), v1.subtract(v2), "ERROR: the func Vector.subtract does not work on argument negative number");
        // Tests an add function for two negative numbers
        assertEquals(new Vector(-2, -4, -6), v2.subtract(v1), "ERROR: the func Vector.subtract does not work on argument positive numbers");

        // =============== Boundary Values Tests ==================
        // Tests an add function checks for the zero vector result
        assertThrows(IllegalArgumentException.class, () -> v1.subtract(v1), "ERROR: the func Vector.subtract does not work on two numbers whose result is the zero vector");

    }

    @Test
    void testConstructor() {

        // =============== Boundary Values Tests ==================
        // Tests a constructor Vector(x,y,z) for the zero vector result
        assertThrows(IllegalArgumentException.class, () -> new Vector(0, 0, 0), "ERROR: the constructor Vector(x,y,z) does not work on zero vector");
        // Tests a constructor Vector(double3) for the zero vector result
        assertThrows(IllegalArgumentException.class, () -> new Vector(new Double3(0)), "ERROR: the constructor Vector(double3) does not work on zero vector");

    }
}