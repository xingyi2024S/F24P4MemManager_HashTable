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

}
