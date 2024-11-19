import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Handle class.
 *
 * Focuses on construction, equality, and representation of Handle instances.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.10
 */
public class HandleTest {
    private Handle handle1;
    private Handle handle2;
    private Handle handle3;
    private Handle handle4;

    /**
     * Setup method initializes Handle instances for testing.
     */
    @Before
    public void setUp() {
        handle1 = new Handle(10, 50); // Position 10, length 50
        handle2 = new Handle(20, 75); // Position 20, length 75
        handle3 = new Handle(10, 50); // Same values as handle1 for equality
                                      // test
        handle4 = new Handle(10, 60); // Same position as handle1 but different
                                      // length
    }


    /**
     * Tests the getPosition and getLength methods.
     */
    @Test
    public void testGetters() {
        assertEquals(10, handle1.getPosition());
        assertEquals(50, handle1.getLength());

        assertEquals(20, handle2.getPosition());
        assertEquals(75, handle2.getLength());
    }


    /**
     * Tests the equals method for handles with same and different values,
     * including edge cases and boundary conditions.
     */
    @Test
    public void testEquals() {
        // Test equality with identical position and length
        assertTrue(handle1.equals(handle3));
        assertTrue(handle3.equals(handle1));

        // Test equality with the same object reference
        assertTrue(handle1.equals(handle1));

        // Test inequality with different positions
        Handle differentPosition = new Handle(15, handle1.getLength());
        assertFalse(handle1.equals(differentPosition));

        // Test inequality with different lengths
        Handle differentLength = new Handle(handle1.getPosition(), 40);
        assertFalse(handle1.equals(differentLength));

        // Test inequality when comparing with null
        assertFalse(handle1.equals(null)); // Null check

        // Test inequality when comparing with an object of a different type
        assertFalse(handle1.equals("Some String"));

        // Test consistency: multiple invocations should return the same result
        assertTrue(handle1.equals(handle3));
        assertTrue(handle1.equals(handle3));
    }


    /**
     * Tests the toString method for correct representation.
     */
    @Test
    public void testToString() {
        String expectedString1 = "Handle[position=10, length=50]";
        String expectedString2 = "Handle[position=20, length=75]";

        assertEquals(expectedString1, handle1.toString());
        assertEquals(expectedString2, handle2.toString());
    }

}
