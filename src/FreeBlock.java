/**
 * FreeBlock class.
 * 
 * @author Xingyi Wang
 * @author Zhengyang Lu
 * @version 2024.11.12
 */
public class FreeBlock {
    private int position;
    private int size;
    private FreeBlock next;
    private FreeBlock previous;
    private FreeBlock head;

    /**
     * Constructor to create a FreeBlock.
     * 
     * @param position
     *            The position of the free block in memory.
     * @param size
     *            The size of the free block.
     */
    public FreeBlock(int position, int size) {
        this.position = position;
        this.size = size;
        this.next = null;
        this.previous = null;
    }


    /**
     * Get position.
     * 
     * @return Position.
     */
    public int getPosition() {
        return position;
    }


    /**
     * Set position.
     * 
     * @return Position.
     */
    public void setPosition(int position) {
        this.position = position;
    }


    /**
     * Get size.
     * 
     * @return Size.
     */
    public int getSize() {
        return size;
    }


    /**
     * Set size.
     * 
     * @param size
     *            Size to set.
     */
    public void setSize(int size) {
        this.size = size;
    }


    /**
     * Get the next FreeBlock.
     * 
     * @return The next FreeBlock.
     */
    public FreeBlock getNext() {
        return next;
    }


    /**
     * Set the next FreeBlock.
     * 
     * @param next
     *            The FreeBlock to set as next.
     */
    public void setNext(FreeBlock next) {
        this.next = next;
    }


    /**
     * Get the previous FreeBlock.
     * 
     * @return The previous FreeBlock.
     */
    public FreeBlock getPrevious() {
        return previous;
    }


    /**
     * Set the next FreeBlock.
     * 
     * @param previous
     *            The FreeBlock to set as previous.
     */
    public void setPrevious(FreeBlock previous) {
        this.previous = previous;
    }


    /**
     * Finds the first free block that can accommodate the requested size.
     *
     * @param dataSize
     *            The size of the data to insert.
     * @return The first free block that can fit, or null if none found.
     */
    public FreeBlock findFirstFit(int dataSize) {
        FreeBlock current = this;
        while (current != null) {
            if (current.getSize() >= dataSize) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }


    /**
     * Adds a free block to the list in sorted order by position and merges if
     * adjacent blocks exist.
     *
     * @param head
     *            The current head of the free block list.
     * @param position
     *            The starting position of the new free block in the memory
     *            pool.
     * @param size
     *            The size of the new free block.
     * @return The head of the updated free block list.
     */
    public static FreeBlock addFreeBlock(
        FreeBlock head,
        int position,
        int size) {
        FreeBlock newBlock = new FreeBlock(position, size);

        // If the list is empty, the new block becomes the head.
        if (head == null) {
            return newBlock;
        }

        FreeBlock current = head;

        // If the new block should be inserted before the head.
        if (position < current.position) {
            newBlock.next = current;
            current.previous = newBlock;
            head = newBlock;
        }
        else {
            // Traverse to find the correct insertion point in sorted order.
            while (current.next != null && current.next.position < position) {
                current = current.next;
            }

            // Insert the new block after 'current'.
            newBlock.next = current.next;
            if (current.next != null) {
                current.next.previous = newBlock;
            }
            current.next = newBlock;
            newBlock.previous = current;
        }

        // Merge with the next block if adjacent.
        if (newBlock.next != null && newBlock.position
            + newBlock.size == newBlock.next.position) {
            newBlock.size += newBlock.next.size;
            newBlock.next = newBlock.next.next;
            if (newBlock.next != null) {
                newBlock.next.previous = newBlock;
            }
        }

        // Merge with the previous block if adjacent.
        if (newBlock.previous != null && newBlock.previous.position
            + newBlock.previous.size == newBlock.position) {
            newBlock.previous.size += newBlock.size;
            newBlock.previous.next = newBlock.next;
            if (newBlock.next != null) {
                newBlock.next.previous = newBlock.previous;
            }
        }

        return head;
    }


    /**
     * Removes the specified block from the list.
     */
    public static FreeBlock remove(FreeBlock head, FreeBlock block) {
        if (block == null)
            return head;

        if (block.previous != null) {
            block.previous.next = block.next;
        }
        else {
            head = block.next;
        }

        if (block.next != null) {
            block.next.previous = block.previous;
        }

        block.next = null;
        block.previous = null;
        return head;
    }


    /**
     * Print the free block list.
     */
    public static void printFreeBlocks(FreeBlock head) {
        if (head == null) {
            System.out.println("There are no freeblocks in the memory pool");
            return;
        }
        FreeBlock current = head;
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
