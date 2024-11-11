public class MemManager {
    
    public MemManager(int poolSize, int blockSize) {
        // Constructor does nothing for now
    }


    public Handle insert(byte[] data, int size) {
        // Dummy insert method; returns a fixed Handle for testing purposes
        return new Handle(0, size); // Using 0 as a placeholder position
    }


    public int length(Handle handle) {
        // Dummy length method; return a fixed size
        return handle.getLength();
    }


    public void remove(Handle handle) {
       
    }


    public int get(byte[] space, Handle handle, int size) {
        // Dummy get method; does nothing
        return 0; // Returns zero bytes for testing purposes
    }


    public void dump() {
        // Dummy dump method; does nothing for now
        System.out.println("Memory dump: Dummy implementation.");
    }
}
