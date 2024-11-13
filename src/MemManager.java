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
    private int initialMemorySize;
    private int usedMemory = 0;

    /**
     * Constructs a MemManager with an initial memory pool size.
     * 
     * @param poolSize
     *            The initial size of the memory pool in bytes.
     */
    public MemManager(int poolSize) {
        memoryPool = new byte[poolSize];
        freeBlockList = new FreeBlock(0, poolSize);
        this.initialMemorySize = poolSize;
    }


    /**
     * Insert data into the memory pool.
     * 
     * @param data
     *            The data to insert.
     * @param size
     *            The size of the data.
     * @return The Handle for the data we just inserted.
     */
    public Handle insert(byte[] data, int size) {
        if (size > memoryPool.length - usedMemory) {
            growMemoryPool(size);
        }

        FreeBlock current = freeBlockList;
        while (current != null) {

            if (current.getSize() >= size) {
                int position = current.getPosition();
                // Copy the data into memory pool
                System.arraycopy(data, 0, memoryPool, position, size);
                usedMemory += size;

                if (current.getSize() > size) {
                    // Split the free block
                    FreeBlock newFreeBlock = new FreeBlock(position + size,
                        current.getSize() - size);
                    newFreeBlock.setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPrevious(newFreeBlock);
                    }
                    current.setNext(newFreeBlock);
                    newFreeBlock.setPrevious(current);
                    current.setSize(size);
                }
                else {
                    if (current.getPrevious() != null) {
                        current.getPrevious().setNext(current.getNext());
                    }
                    if (current.getNext() != null) {
                        current.getNext().setPrevious(current.getPrevious());
                    }
                }

                return new Handle(position, size);
            }
            current = current.getNext();
        }

        growMemoryPool(size);
        return insert(data, size);
    }


    /**
     * Grows the memory pool when there is not enough space to store a new
     * record.
     * Expands by a fixed block size (the initial memory size).
     * 
     * @param requiredSize
     *            Required size for the new data to insert.
     */
    private void growMemoryPool(int requiredSize) {
        int currentSize = memoryPool.length;
        int availableMemory = currentSize - usedMemory;

        // Check if there is enough space available, if not, expand
        if (availableMemory < requiredSize) {
            int newSize = currentSize + this.initialMemorySize;
            System.out.println("Memory pool expanded to " + newSize + " bytes");

            // Create a new memory pool with the expanded size
            byte[] newMemoryPool = new byte[newSize];
            System.arraycopy(memoryPool, 0, newMemoryPool, 0, currentSize);
            memoryPool = newMemoryPool;

            // Add new free block at the end of the pool
            FreeBlock newFreeBlock = new FreeBlock(currentSize, newSize
                - currentSize);
            FreeBlock lastFreeBlock = freeBlockList;
            while (lastFreeBlock.getNext() != null) {
                lastFreeBlock = lastFreeBlock.getNext();
            }
            lastFreeBlock.setNext(newFreeBlock);
            newFreeBlock.setPrevious(lastFreeBlock);
        }
    }


    /**
     * Removes a block from the memory pool, freeing the space it occupied.
     * 
     * @param handle
     *            The handle representing the block to be removed.
     */
    public void remove(Handle handle) {
        FreeBlock current = freeBlockList;

        while (current != null) {
            if (current.getPosition() == handle.getPosition()) {

                // Merge adjacent free blocks if possible

                // Check if the next block is adjacent
                if (current.getNext() != null && current.getPosition() + current
                    .getSize() == current.getNext().getPosition()) {
                    current.setSize(current.getSize() + current.getNext()
                        .getSize());
                    current.setNext(current.getNext().getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPrevious(current);
                    }
                }

                // Check if the previous block is adjacent
                if (current.getPrevious() != null && current.getPosition()
                    + current.getSize() == current.getPrevious()
                        .getPosition()) {
                    current.getPrevious().setSize(current.getPrevious()
                        .getSize() + current.getSize());
                    current.getPrevious().setNext(current.getNext());
                    if (current.getNext() != null) {
                        current.getNext().setPrevious(current.getPrevious());
                    }
                }

                // Remove the current block from the list (after merging)
                if (current.getPrevious() != null) {
                    current.getPrevious().setNext(current.getNext());
                }
                if (current.getNext() != null) {
                    current.getNext().setPrevious(current.getPrevious());
                }
                usedMemory -= handle.getLength();
                return;
            }
            current = current.getNext();
        }
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
     * Get used memory.
     *
     * @return usedMemory.
     */
    public int getUsedMemory() {
        return usedMemory;
    }


    /**
     * Get memory pool size.
     * 
     * @return memoryPoolSize.
     */
    public int getMemoryPoolSize() {
        return memoryPool.length;
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
     * Print the free block list.
     */
    public void printFreeBlockList() {
        FreeBlock current = freeBlockList;

        if (current == null) {
            System.out.println("FreeBlock List is empty.");
            return;
        }
        while (current != null) {
            System.out.print("(" + current.getPosition() + "," + current
                .getSize() + ")");

            if (current.getNext() != null) {
                System.out.print(" -> ");
            }
            current = current.getNext();
        }
        System.out.println();
    }

}
