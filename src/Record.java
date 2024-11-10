/**
 * The Record class represents a key-handle pair in a hash table.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.08
 */
public class Record {
    private int id;
    private Handle handle;

    /**
     * Constructs a Record with the specified ID and handle.
     * 
     * @param id
     *            The unique ID for the seminar record. Used as a key in the
     *            hash table.
     * @param handle
     *            The memory handle associated with the seminar data, providing
     *            access to the data's location in the memory pool.
     */
    public Record(int id, Handle handle) {
        this.id = id;
        this.handle = handle;
    }


    /**
     * Returns the unique ID associated with this Record.
     * 
     * @return The ID of the record.
     */
    public int getId() {
        return id;
    }


    /**
     * Returns the memory handle associated with this Record.
     * 
     * @return The Handle object that provides access to the record's data.
     */
    public Handle getHandle() {
        return handle;
    }


    /**
     * Compares this Record to another object for equality.
     * 
     * @param obj
     *            The object to compare to.
     * @return true if the specified object is a Record with the same ID; false
     *         otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Record record = (Record)obj;
        return id == record.id;
    }


    /**
     * Returns the hash code for this Record based on its ID.
     * 
     * @return The hash code derived from the ID.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
