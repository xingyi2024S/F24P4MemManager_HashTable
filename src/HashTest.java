import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for the Hash class.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * 
 * @version 2024.11.08
 */
public class HashTest {
    private Hash hashTable;
    private Handle handle1;
    private Handle handle2;
    private Handle handle3;
    private Handle handle4;

    /**
     * Setup method initializes the hash table and Handle objects for testing.
     */
    @Before
    public void setUp() {
        hashTable = new Hash(4);
        handle1 = new Handle(10, 50);
        handle2 = new Handle(60, 100);
        handle3 = new Handle(110, 150);
        handle4 = new Handle(160, 200);
    }


    /**
     * Tests inserting records into the hash table
     */
    @Test
    public void testInsertAndFind() {
        assertEquals(1, hashTable.insert(12345, handle1));
        assertEquals(2, hashTable.insert(67890, handle2));

        assertEquals(handle1, hashTable.find(12345));
        assertEquals(handle2, hashTable.find(67890));
        //testDuplicateInsertion
        hashTable.insert(12345, handle1);
        assertEquals(-1, hashTable.insert(12345, handle2));
    }




    /**
     * Tests removing records from the hash table
     */
    @Test
    public void testRemoveAndTombstoneHandling() {
        hashTable.insert(12345, handle1);
        hashTable.insert(67890, handle2);

        assertEquals(handle1, hashTable.remove(12345));
        assertNull(hashTable.find(12345));

        hashTable.insert(12345, handle3);
        assertEquals(handle3, hashTable.find(12345));
        
    }


    /**
     * Tests that the hash table resizes correctly when the load exceed 50%.
     */
    @Test
    public void testResize() {
        hashTable.insert(12345, handle1);
        hashTable.insert(67890, handle2);
        hashTable.insert(13579, handle3);
        hashTable.insert(24680, handle4); // Should trigger resize

        assertEquals(8, hashTable.getCapacity());
        assertEquals(handle1, hashTable.find(12345));
        assertEquals(handle2, hashTable.find(67890));
        assertEquals(handle3, hashTable.find(13579));
        assertEquals(handle4, hashTable.find(24680));
    }


    /**
     * Tests handling of collisions using quadratic probing
     */
    @Test
    public void probe() {
        Hash hashTable = new Hash(10);
        assertEquals(3, hashTable.probe(3, 0));
        assertEquals(4, hashTable.probe(3, 1));
        assertEquals(6, hashTable.probe(3, 2));
        assertEquals(9, hashTable.probe(3, 3));
        assertEquals(3, hashTable.probe(3, 4));
    }


    /**
     * Tests behavior when attempting to find or remove a non-existent key
     */
    @Test
    public void testNonExistentKeys() {
        assertNull(hashTable.find(99999));
        assertNull(hashTable.remove(99999));
    }


    /**
     * Tests multiple removals and ensures tombstones do not interfere with
     * future insertions.
     */
    @Test
    public void testMultipleRemovals() {
        hashTable.insert(12345, handle1);
        hashTable.insert(67890, handle2);

        hashTable.remove(12345);
        hashTable.remove(67890);

        assertNull(hashTable.find(12345));
        assertNull(hashTable.find(67890));

        hashTable.insert(12345, handle3);
        assertEquals(handle3, hashTable.find(12345));
    }



    /**
     * Tests printToString method to verify correct output format and tombstone
     * handling.
     */
    @Test
    public void testPrintToString() {
        hashTable.insert(1, handle1);
        hashTable.insert(2, handle2);
        hashTable.remove(2);

        String output = hashTable.printToString();
        assertTrue(output.contains("total records 1"));
        assertTrue(output.contains("1 1"));
        assertTrue(output.contains("TOMBSTONE"));
    }


    /**
     * Tests inserting records that will cause collisions and verifying probe
     * behavior.
     */
    @Test
    public void testInsertAndProbeCollision() {
        assertEquals(1, hashTable.insert(1, handle1));
        assertEquals(3, hashTable.insert(3, handle2));
        assertEquals(5, hashTable.insert(5, handle3));
        assertEquals(2, hashTable.insert(9, handle4));

        assertEquals(handle1, hashTable.find(1));
        assertEquals(handle2, hashTable.find(3));
        assertEquals(handle3, hashTable.find(5));
        assertEquals(handle4, hashTable.find(9));

        // test the tombstone
        hashTable.remove(1);
        assertEquals(1, hashTable.insert(17, handle1));

    }


    @Test
    public void testhashInsertIntoTombstone() {
        assertEquals(1, hashTable.insert(1, handle1));

        assertEquals(-1, hashTable.insert(1, handle2));

        assertNotNull(hashTable.remove(1));
       
        assertEquals(1, hashTable.insert(1, handle3));

        assertNotNull(hashTable.find(1));

    }
    
}
