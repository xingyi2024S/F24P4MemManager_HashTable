public class FreeBlock {
    int position;
    int size;
    FreeBlock next;
    FreeBlock previous;

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



    public int getPosition() {
        return position;
    }


  
    public int getSize() {
        return size;
    }


    public FreeBlock getNext() {
        return next;
    }

    public void setNext(FreeBlock next) {
        this.next = next;
    }



    public FreeBlock getPrevious() {
        return previous;
    }



    public void setPrevious(FreeBlock previous) {
        this.previous = previous;
    }
}
