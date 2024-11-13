import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests for MemManager.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.10
 */
public class MemManagerTest {

    private MemManager memManager;
    private int initialMemorySize = 1024; // Set initial memory size for the
                                          // pool

    /**
     * Set up tests.
     */
    @Before
    public void setUp() {
        // Initialize MemManager with a pool size of 1024 bytes
        memManager = new MemManager(initialMemorySize);
    }


    /**
     * Test insert into memory pool.
     */
    @Test
    public void testInsertDataIntoMemoryPool() {
        byte[] data = new byte[200]; // 200 bytes data to insert
        Handle handle = memManager.insert(data, 200);

        // Verify the data is inserted correctly by checking the handle position
        // and size
        assertNotNull(handle);
        assertEquals(200, handle.getLength());

        // Verify that the memory pool usage has increased
        assertEquals(200, memManager.getUsedMemory());
    }


    /**
     * Test remove from memory pool.
     */
    @Test
    public void testRemoveDataAndMergeBlocks() {
        byte[] data1 = new byte[200];
        byte[] data2 = new byte[300];
        byte[] data3 = new byte[100];

        Handle handle1 = memManager.insert(data1, 200);
        Handle handle2 = memManager.insert(data2, 300);
        Handle handle3 = memManager.insert(data3, 100);

        // Remove a block and check if it gets freed and adjacent blocks are
        // merged
        memManager.remove(handle2);

        // Check the free block list to ensure blocks are merged correctly
        FreeBlock firstFreeBlock = memManager.getFreeBlockList();
        assertNotNull(firstFreeBlock);
        assertTrue(firstFreeBlock.getSize() > 0); // This checks if memory is
                                                  // merged correctly
    }


    /**
     * Test free block merging.
     */
    @Test
    public void testFreeBlockMerging() {
        // Insert data blocks
        byte[] data1 = new byte[300];
        byte[] data2 = new byte[200];
        Handle handle1 = memManager.insert(data1, 300);
        Handle handle2 = memManager.insert(data2, 200);

        memManager.remove(handle2);

        FreeBlock mergedFreeBlock = memManager.getFreeBlockList();
        assertNotNull(mergedFreeBlock);
        assertEquals(mergedFreeBlock.getSize(), 300);
    }


    /**
     * Test multiple insertions.
     */
    @Test
    public void testMultipleInsertions() {
        byte[] data1 = new byte[500];
        byte[] data2 = new byte[300];
        byte[] data3 = new byte[100];

        Handle handle1 = memManager.insert(data1, 500);
        Handle handle2 = memManager.insert(data2, 300);
        Handle handle3 = memManager.insert(data3, 100);

        // Verify correct memory usage after multiple insertions
        assertEquals(500 + 300 + 100, memManager.getUsedMemory());
        assertEquals(memManager.getMemoryPoolSize(), initialMemorySize);
    }


    /**
     * Test memory pool growth.
     */
    @Test
    public void testGrowMemoryPool() {
        byte[] data1 = new byte[500];
        byte[] data2 = new byte[600]; // This will exceed initial pool size
        Handle handle1 = memManager.insert(data1, 500);
        Handle handle2 = memManager.insert(data2, 600);

        // Ensure the memory pool expanded after the second insertion
        assertTrue(memManager.getMemoryPoolSize() > initialMemorySize);
        assertEquals(500 + 600, memManager.getUsedMemory());
    }


    /**
     * Test memory pool growth after removal.
     */
    @Test
    public void testRemoveAfterExpand() {
        byte[] data1 = new byte[500];
        byte[] data2 = new byte[600]; // This will exceed initial pool size
        Handle handle1 = memManager.insert(data1, 500);
        Handle handle2 = memManager.insert(data2, 600);

        // Remove the first block
        memManager.remove(handle1);

        // Verify that memory usage decreases and the remaining free space is
        // correct
        assertEquals(600, memManager.getUsedMemory());
    }


    /**
     * Test memory pool initial size.
     */
    @Test
    public void testMemoryPoolInitialSize() {
        // Insert data that fits within the initial pool size
        byte[] data = new byte[512];
        Handle handle = memManager.insert(data, 512);

        // Verify that memory pool size remains unchanged for this case
        assertEquals(initialMemorySize, memManager.getMemoryPoolSize());
    }
}
