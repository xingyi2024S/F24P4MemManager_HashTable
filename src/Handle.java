/**
 * The Handle class represents a reference to a specific record's location in
 * the memory pool.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.08
 */
public class Handle {
    private final int position;
    private final int length;

    /**
     * Constructs a Handle with a specific position and length.
     *
     * @param position
     *            The starting position of the record in the memory pool.
     * @param length
     *            The length of the record in bytes.
     */
    public Handle(int position, int length) {
        this.position = position;
        this.length = length;
    }


    /**
     * Returns the starting position of the record in the memory pool.
     *
     * @return The position in the memory pool.
     */
    public int getPosition() {
        return position;
    }


    /**
     * Returns the length of the record.
     *
     * @return The length of the record in bytes.
     */
    public int getLength() {
        return length;
    }


    /**
     * Generates a string representation of the handle, primarily for debugging
     * purposes.
     *
     * @return A string representing the handle's position and length.
     */
    @Override
    public String toString() {
        return "Handle[position=" + position + ", length=" + length + "]";
    }

    /**
     * Returns the length (size) of the record.
     *
     * @return The length of the record in bytes.
     */
    public int getSize() {
        return length;
    }

    /**
     * Compares this Handle to another object for equality.
     * Two Handles are equal if they have the same position and length.
     *
     * @param obj
     *            The object to compare to.
     * @return true if the objects are equal; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Handle handle = (Handle)obj;
        return position == handle.position && length == handle.length;
    }

}
