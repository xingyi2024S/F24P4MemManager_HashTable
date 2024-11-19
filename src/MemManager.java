/**
 * The MemManager class is responsible for managing memory by allocating and
 * deallocating memory blocks from a pool. It uses a doubly linked list to track
 * free blocks and applies the "first fit" rule for allocating memory.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.10
 */
public class MemManager {
    private byte[] memoryPool;
    private FreeBlock freeBlockList;
    private int initialPoolSize;

    /**
     * Constructs a MemManager with an initial memory pool size.
     * 
     * @param poolSize
     *            The initial size of the memory pool in bytes.
     */
    public MemManager(int poolSize) {
        memoryPool = new byte[poolSize];
        freeBlockList = new FreeBlock(0, poolSize);
        this.initialPoolSize = poolSize;
    }


    /**
     * Insert data into the memory pool.
     * 
     * @param data
     *            The data to insert.
     * @return The Handle for the data we just inserted.
     */
    public Handle insert(byte[] data) {
        int dataSize = data.length;

        FreeBlock block = freeBlockList.findFirstFit(dataSize);

        while (block == null) {
            expandMemoryPool();
            System.out.println("Memory pool expanded to " + memoryPool.length
                + " bytes");
            block = freeBlockList.findFirstFit(dataSize);
        }

        System.arraycopy(data, 0, memoryPool, block.getPosition(), dataSize);
        Handle handle = new Handle(block.getPosition(), dataSize);

        if (block.getSize() == dataSize) {
            freeBlockList = FreeBlock.remove(freeBlockList, block);
        }
        else {
            block.setPosition(block.getPosition() + dataSize);
            block.setSize(block.getSize() - dataSize);
        }

        return handle;
    }


    /**
     * Grows the memory pool when there is not enough space to store a new
     * record.
     * Expands by a fixed block size (the initial memory size).
     * 
     * @param requiredSize
     *            Required size for the new data to insert.
     */
    private void expandMemoryPool() {
        int oldSize = memoryPool.length;
        int newSize = memoryPool.length + initialPoolSize;
        byte[] newMemoryPool = new byte[newSize];
        System.arraycopy(memoryPool, 0, newMemoryPool, 0, oldSize);
        memoryPool = newMemoryPool;
        freeBlockList = FreeBlock.addFreeBlock(freeBlockList, oldSize,
            initialPoolSize);
    }


    /**
     * Removes a block from the memory pool, freeing the space it occupied.
     * 
     * @param handle
     *            The handle representing the block to be removed.
     */
    public void remove(Handle handle) {
        int position = handle.getPosition();
        int size = handle.getSize();
        freeBlockList = FreeBlock.addFreeBlock(freeBlockList, position, size);
    }


    /**
     * Retrieves data from the memory pool using the specified handle and copies
     * it into the provided space.
     * 
     * @param space
     *            The array to copy the data into.
     * @param handle
     *            The handle representing the block of data to retrieve.
     * @param size
     *            The number of bytes to copy from the memory pool.
     * @return The number of bytes actually copied into the space.
     */
    public int get(byte[] space, Handle handle, int size) {
        if (handle.getPosition() + size <= memoryPool.length) {
            System.arraycopy(memoryPool, handle.getPosition(), space, 0, size);
            return size;
        }
        return 0;
    }


    /**
     * Get free block list.
     * 
     * @return freeBlockList.
     */
    public FreeBlock getFreeBlockList() {
        return freeBlockList;
    }


    /**
     * Return the byte array data that the handle is holding in mem pool.
     * 
     * @param handle
     *            The handle with the data to get.
     * @return The data in byte array form.
     */
    public byte[] read(Handle handle) {
        int position = handle.getPosition();
        int size = handle.getSize();

        if (position < 0 || position + size > memoryPool.length) {
            System.err.println("Error: Invalid handle.");
            return null;
        }

        byte[] data = new byte[size];
        System.arraycopy(memoryPool, position, data, 0, size);
        return data;
    }


    /**
     * Prints the free block list by calling the static print method in
     * FreeBlock.
     * This allows external classes to see the current state of the free blocks
     * in memory.
     */
    public void printFreeBlockList() {
        System.out.print("Freeblock List:\n");
        FreeBlock.printFreeBlocks(freeBlockList);
    }


    /**
     * Returns the current size of the memory pool.
     * 
     * @return The size of the memory pool in bytes.
     */
    public int getMemoryPoolSize() {
        return memoryPool.length;
    }

}
