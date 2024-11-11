import java.util.Iterator;

import student.TestCase;

/**
 * @author CS staff
 * @author Xingyi Wang
 * @version from project 1
 */
public class DLListTest extends TestCase {
    /**
     * the list we will use
     */
    private DLList<String> list;

    /**
     * run before every test case
     */
    @Override
    public void setUp() {
        list = new DLList<String>();
    }


    /**
     * Tests that an IndexOutOfBounds exception is thrown when the index is
     * greater than or equal to size and less than zero
     */
    public void testRemoveException() {
        list.add("A");
        Exception e = null;
        try {
            list.remove(2);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try {
            list.remove(-1);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests that objects can be removed at the beginning and end and that the
     * size is changed
     */
    public void testRemoveIndex() {
        list.add("A");
        list.add("B");
        assertTrue(list.remove(1));
        assertEquals(1, list.size());
        list.add("B");
        assertTrue(list.remove(0));
        assertEquals(1, list.size());
    }


    /**
     * Tests the add method. Ensures that it adds the object is added at the end
     * and the size is increased
     */
    public void testAdd() {
        assertEquals(0, list.size());
        list.add("A");
        assertEquals(1, list.size());
        list.add("B");
        assertEquals(2, list.size());
        assertEquals("B", list.get(1));

    }


    /**
     * Tests that objects can be added at the beginning and end and that they
     * are placed correctly
     */
    public void testAddIndex() {
        list.add("B");
        list.add(0, "A");
        assertEquals("A", list.get(0));
        assertEquals(2, list.size());
        list.add(2, "D");
        assertEquals("D", list.get(2));
        list.add(2, "C");
        assertEquals("C", list.get(2));
    }


    /**
     * This tests that the add method throws a null pointer exception when
     * adding null data to the list
     */
    public void testAddNullException() {
        Exception e = null;
        try {
            list.add(null);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }


    /**
     * This tests that the add method throws a Invalid argument when adding null
     * data to the list
     */
    public void testAddIndexNullException() {
        Exception e = null;
        try {
            list.add(0, null);
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IllegalArgumentException);
    }


    /**
     * This tests when the add method is called and the index is greater than
     * size or less than zero
     */
    public void testAddException() {
        list.add("A");
        Exception e = null;
        try {
            list.add(2, "B");
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
        e = null;
        try {
            list.add(-1, "B");
        }
        catch (Exception exception) {
            e = exception;
        }
        assertTrue(e instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests removing a object changes the size appropiately and that you can
     * remove the first and last elements
     */
    public void testRemoveObj() {
        assertFalse(list.remove(null));
        list.add("A");
        list.add("B");
        assertEquals(2, list.size());
        assertTrue(list.remove("A"));
        assertEquals("B", list.get(0));
        assertEquals(1, list.size());
        list.add("C");
        assertTrue(list.remove("C"));
        assertEquals("B", list.get(0));
    }


    /**
     * Tests the removal of the last element in the list.
     * 
     */
    public void testRemoveLastElement() {
        list.add("X");
        assertEquals(1, list.size());

        // Remove the only element
        assertTrue(list.remove(0));
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

        // Ensure the list behaves correctly after being emptied
        list.add("Y");
        assertEquals(1, list.size());
        assertEquals("Y", list.get(0));
    }


    /**
     * Tests removing elements from the list and ensures size decreases
     * correctly.
     */
    public void testRemoveByIndex() {
        // Add some elements to the list
        list.add("A");
        list.add("B");
        list.add("C");

        // Check initial size
        assertEquals(3, list.size());

        // Remove element at index 1 (middle element "B")
        assertTrue(list.remove(1));
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("C", list.get(1));

        // Remove element at index 0 (first element "A")
        assertTrue(list.remove(0));
        assertEquals(1, list.size());
        assertEquals("C", list.get(0));

        // Remove the last remaining element (index 0, "C")
        assertTrue(list.remove(0));
        assertEquals(0, list.size());

        // Attempt to remove from an empty list (should throw exception)
        Exception exception = null;
        try {
            list.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertNotNull(exception);
    }


    /**
     * Tests get when the index is greater than or equal to size and when the
     * index is less than zero
     */
    public void testGetException() {
        Exception exception = null;
        try {
            list.get(-1);
        }
        catch (Exception e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
        exception = null;
        list.add("A");
        try {
            list.get(1);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Test contains when it does and does not contain the object
     */
    public void testContains() {
        assertFalse(list.contains("A"));
        list.add("A");
        assertTrue(list.contains("A"));
        assertFalse(list.contains("B"));
        list.add("B");
        assertTrue(list.contains("B"));
    }


    /**
     * Test lastIndexOf when the list is empty, when the object is not in the
     * list, and when it is at the beginning or end
     */
    public void testLastIndexOf() {
        assertEquals(-1, list.lastIndexOf("A"));
        list.add("A");
        assertEquals(0, list.lastIndexOf("A"));
        list.add("A");
        assertEquals(1, list.lastIndexOf("A"));
        list.add("B");
        assertEquals(1, list.lastIndexOf("A"));
        assertEquals(2, list.lastIndexOf("B"));
        list.add("A");
        assertEquals(3, list.lastIndexOf("A"));
    }


    /**
     * Tests isEmpty when empty and full
     */
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.add("A");
        assertFalse(list.isEmpty());
    }


    /**
     * Ensures that all of the objects are cleared and the size is changed
     */
    public void testClear() {
        list.add("A");
        list.clear();
        assertEquals(0, list.size());
        assertFalse(list.contains("A"));
    }


    /**
     * Tests the toString method for both empty and non-empty lists.
     */
    public void testToString() {
        // Test when the list is empty
        assertTrue(list.isEmpty());
        assertEquals("{}", list.toString());

        // Add elements to the list and check the string representation
        list.add("A");
        list.add("B");
        list.add("C");
        assertEquals("{A, B, C}", list.toString());

        // Clear the list and test again
        list.clear();
        assertTrue(list.isEmpty());
        assertEquals("{}", list.toString());
    }


    /**
     * Tests removing from an empty list
     */
    public void testRemoveFromEmpty() {
        list.add("dance");
        list.add(0, "safety");
        list.clear();
        assertFalse(list.remove("safety"));
        Exception exception;
        exception = null;
        try {
            list.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);

        DLList<String> emptyList = new DLList<String>();
        exception = null;
        try {
            emptyList.remove(0);
        }
        catch (IndexOutOfBoundsException e) {
            exception = e;
        }
        assertTrue(exception instanceof IndexOutOfBoundsException);
    }


    /**
     * Tests the iterator
     */
    public void testIterator() {
        DLList<String> dlList = new DLList<>();

        Iterator<String> iter1 = list.iterator();

        // throw an exception if list is empty
        Exception e1 = null;
        try {
            iter1.next();
        }
        catch (Exception exception) {
            e1 = exception;
        }
        assertNotNull(e1);

        // Test hasNext method
        list.add("1");
        list.add("2");
        list.add("3");
        Iterator<String> iter2 = list.iterator();
        assertTrue(iter2.hasNext());
        assertEquals("1", iter2.next());
        assertTrue(iter2.hasNext());
        assertEquals("2", iter2.next());
        assertTrue(iter2.hasNext());
        assertEquals("3", iter2.next());
        assertFalse(iter2.hasNext());

    }

}
