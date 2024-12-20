/**
 * The Hash class represents a hash table that stores key-handle pairs,
 * where the key is the unique seminar ID, and the value is a Handle
 * that indicates the record's location in the memory pool.
 * The hash table uses quadratic probing for collision resolution and resizes
 * when the load factor exceeds 50%.
 *
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.08
 */
public class Hash {
    private Record[] allRecords;
    private int numberOfRecords;
    private int capacity;
    private static final Record TOMBSTONE = new Record(-1, null);

    /**
     * Constructs a new Hash object with the specified initial capacity.
     *
     * @param initialCapacity
     *            The initial capacity of the hash table.
     */
    public Hash(int initialCapacity) {
        this.capacity = initialCapacity;
        this.allRecords = new Record[capacity];
        this.numberOfRecords = 0;
    }


    /**
     * Computes the hash value for the given ID.
     *
     * @param id
     *            The ID to be hashed.
     * @return The hash value of the ID.
     */
    public int hash(int id) {
        return id % capacity;
    }


    /**
     * Inserts an ID into the hash table with its associated memory handle.
     *
     * @param id
     *            The ID to insert into the table.
     * @param handle
     *            The associated memory handle in the memory pool.
     * @return The index where the ID was inserted, or -1 if insertion fails.
     */
    public int insert(int id, Handle handle) {
        if (numberOfRecords >= capacity / 2) {
            System.out.println("Hash table expanded to " + (capacity * 2)
                + " records");
            resize();
        }

        int home = hash(id);
        int i = 0;
        int pos;

        do {
            pos = probe(home, i);
            if (allRecords[pos] == null || allRecords[pos] == TOMBSTONE) {
                allRecords[pos] = new Record(id, handle);
                numberOfRecords++;
                return pos;
            }
            else if (allRecords[pos].getId() == id) {
                System.out.println(
                    "Insert FAILED - There is already a record with ID " + id);
                return -1; // failure due to duplicate ID
            }

            i++;
        }
        while (i < capacity);
        return -1;
    }


    /**
     * Resizes the hash table by doubling its capacity and reinserting all valid
     * records.
     */
    private void resize() {
        int oldCapacity = capacity;
        capacity *= 2;
        Record[] oldTable = allRecords;
        allRecords = new Record[capacity];
        numberOfRecords = 0;

        for (Record record : oldTable) {
            if (record != null && record != TOMBSTONE) {
                insert(record.getId(), record.getHandle());
            }
        }
    }


    /**
     * Finds the handle associated with the given ID.
     *
     * @param id
     *            The ID to find in the table.
     * @return The handle associated with the ID, or null if not found.
     */
    public Handle find(int id) {
        int home = hash(id);
        int i = 0;
        int pos;

        do {
            pos = probe(home, i);
            if (allRecords[pos] == null) {
                return null;
            }
            if (allRecords[pos] != TOMBSTONE && allRecords[pos].getId() == id) {
                return allRecords[pos].getHandle();
            }
            i++;
        }
        while (i < capacity);

        return null;
    }


    /**
     * Removes the specified ID from the hash table.
     *
     * @param id
     *            The ID to remove.
     * @return The handle associated with the removed ID, or null if the ID is
     *         not found.
     */
    public Handle remove(int id) {
        int home = hash(id);
        int i = 0;
        int pos;

        do {
            pos = probe(home, i);
            if (allRecords[pos] == null) {
                return null;
            }
            if (allRecords[pos] != TOMBSTONE && allRecords[pos].getId() == id) {
                Handle handle = allRecords[pos].getHandle();
                allRecords[pos] = TOMBSTONE; // Mark as tombstone
                numberOfRecords--;
                return handle;
            }
            i++;
        }
        while (i < capacity);

        return null;
    }


    /**
     * Probing function for collision resolution (quadratic probing).
     *
     * @param home
     *            The initial hash position.
     * @param i
     *            The current probe attempt number.
     * @return The new probe position after applying quadratic probing.
     */
    public int probe(int home, int i) {
        return (home + (i * i + i) / 2) % capacity;
    }


    /**
     * Returns the current capacity of the hash table.
     *
     * @return The capacity of the hash table.
     */
    public int getCapacity() {
        return capacity;
    }


    /**
     * Prints the contents of the hash table.
     *
     * @return A string representation of the hash table contents.
     */
    public String printToString() {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < capacity; i++) {
            if (allRecords[i] == TOMBSTONE) {
                result.append(i + ": TOMBSTONE\n");
            }
            else if (allRecords[i] != null) {
                result.append(i + ": " + allRecords[i].getId() + "\n");
            }
        }

        result.append("total records: " + numberOfRecords);
        return result.toString();
    }
}
