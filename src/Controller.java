/**
 * The Controller class for managing the hash table and memory pool.
 *
 * @author Xingyi Wang
 * @author Zhengyang Lu
 * @version 2024.11.12
 */
public class Controller {

    private Hash hashTable;
    private MemManager memManager;

    /**
     * Constructs a Controller with a hash table and memory manager.
     *
     * @param initialHashSize
     *            The initial size of the hash table.
     * @param memPoolSize
     *            The initial size of the memory pool in bytes.
     */
    public Controller(int initialHashSize, int memPoolSize) {
        this.hashTable = new Hash(initialHashSize);
        this.memManager = new MemManager(memPoolSize);
    }


    /**
     * Inserts a new Seminar record into the hash table and memory manager.
     *
     * @param id
     *            The ID of the seminar
     * @param title
     *            The title of the seminar
     * @param date
     *            The date of the seminar
     * @param length
     *            The length of the seminar in hours
     * @param x
     *            The x-coordinate of the seminar's location
     * @param y
     *            The y-coordinate of the seminar's location
     * @param cost
     *            The cost of attending the seminar
     * @param keywords
     *            An array of keywords describing the seminar
     * @param description
     *            The description of the seminar
     */
    public void insert(
        Integer id,
        String title,
        String date,
        Integer length,
        Short x,
        Short y,
        Integer cost,
        String[] keywords,
        String description) {

        if (hashTable.find(id) != null) {
            System.out.println(
                "Insert FAILED - There is already a record with ID " + id);
            return;
        }

        String trimmedTitle = title.trim();
        String trimmedDescription = description.trim();

        Seminar newSeminar = new Seminar(id, trimmedTitle, date, length, x, y,
            cost, keywords, trimmedDescription);

        try {
            byte[] serializedSeminar = newSeminar.serialize();
            int seminarSize = serializedSeminar.length;

            Handle handle = memManager.insert(serializedSeminar, seminarSize);

            if (handle != null) {
                hashTable.insert(id, handle);
                System.out.println("Successfully inserted record with ID "
                    + id);
                System.out.println(newSeminar.toString());
                System.out.println("Size: " + seminarSize);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Searches for a seminar record by ID, retrieves and prints it if found.
     *
     * @param id
     *            The ID of the seminar to search for
     * @return The Seminar object if found, or null if not found
     */
    public Seminar search(Integer id) throws Exception {
        Handle handle = hashTable.find(id);

        if (handle != null) {
            byte[] serializedData = new byte[handle.getLength()];
            memManager.get(serializedData, handle, handle.getLength());

            Seminar seminar = Seminar.deserialize(serializedData);
            System.out.println("Found record with ID " + id + ":");
            System.out.println(seminar.toString());
            return seminar;
        }
        else {
            System.out.println("Search FAILED -- There is no record with ID "
                + id);
            return null;
        }
    }


    /**
     * Deletes a seminar record by ID, removing it from the hash table and
     * memory.
     *
     * @param id
     *            The ID of the seminar to delete
     */
    public void delete(Integer id) {
        Handle handle = hashTable.remove(id);

        if (handle != null) {
            memManager.remove(handle);
            System.out.println("Record with ID " + id
                + " successfully deleted from the database");
        }
        else {
            System.out.println("Delete FAILED -- There is no record with ID "
                + id);
        }
    }


    /**
     * Prints the contents of the hash table.
     */
    public void printHashTable() {
        System.out.println("Hashtable:");
        System.out.println(hashTable.printToString());
    }


    /**
     * Prints the free block list in the memory manager.
     */
    public void printFreeBlocks() {
        System.out.println("Freeblock List:");
        memManager.printFreeBlockList();
    }

}
