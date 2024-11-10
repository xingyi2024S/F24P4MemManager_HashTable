import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Record class.
 *
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.08
 */
public class RecordTest {
    private Record record1;
    private Record record2;
    private Record record3;
    private Handle handle1;
    private Handle handle2;

    /**
     * Setup method initializes the Record objects for testing.
     */
    @Before
    public void setUp() {
        handle1 = new Handle(10, 50);
        handle2 = new Handle(60, 100);
        record1 = new Record(12345, handle1);
        record2 = new Record(12345, handle2);
        record3 = new Record(67890, handle1);
    }


    /**
     * Tests the constructor and getter methods.
     */
    @Test
    public void testConstructorAndGetters() {
        assertEquals(12345, record1.getId());
        assertEquals(handle1, record1.getHandle());

        assertEquals(12345, record2.getId());
        assertEquals(handle2, record2.getHandle());

        assertEquals(67890, record3.getId());
        assertEquals(handle1, record3.getHandle());
    }


    /**
     * Tests the equals method to verify comparison by ID.
     */
    @Test
    public void testEquals() {
        // Test equality for records with the same ID
        assertTrue(record1.equals(record2));

        // Test inequality for records with different IDs
        assertFalse(record1.equals(record3));

        // Test self-equality
        assertTrue(record1.equals(record1));

        // Test equality with null and different object types
        assertFalse(record1.equals(null));
        assertFalse(record1.equals("NotARecord"));
    }
}
