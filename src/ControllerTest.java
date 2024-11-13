import org.junit.Test;
import student.TestCase;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test class for the Controller class using sample input.
 * 
 * @author Xingyi Wang
 * @author Zhengyang Lu
 * @version 2024.11.12
 */
public class ControllerTest extends TestCase {

    private Controller controller;
    private ByteArrayOutputStream outputStream;

    /**
     * Setup to initialize the controller and output stream.
     */
    public void setUp() {
        controller = new Controller(64, 512); // Example sizes
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }


    /**
     * Helper method to assert output and clear history.
     * 
     * @param expected
     *            The expected output as a string.
     */
    private void assertOutput(String expected) {
        String actual = outputStream.toString().trim();
        assertEquals(expected, actual);
        outputStream.reset();
    }


    /**
     * Test inserting seminars based on the sample input.
     */
    @Test
    public void testSampleInserts() {
        // Insert 1
        controller.insert(1, "Overview of HCI Research at VT", "0610051600", 90,
            (short)10, (short)10, 45, new String[] { "HCI", "Computer_Science",
                "VT", "Virginia_Tech" },
            "This seminar will present an overview of HCI research at VT");
        assertOutput("Successfully inserted record with ID 1\n"
            + "ID: 1, Title: Overview of HCI Research at VT\n"
            + "Date: 0610051600, Length: 90, X: 10, Y: 10, Cost: 45\n"
            + "Description: This seminar will present an overview of HCI "
            + "research at VT\n"
            + "Keywords: HCI, Computer_Science, VT, Virginia_Tech\n"
            + "size: 173");

        // Insert 2
        controller.insert(2,
            "Computational Biology and Bioinformatics in CS at Virginia Tech",
            "0610071600", 60, (short)20, (short)10, 30, new String[] {
                "Bioinformatics", "computation_biology", "Biology",
                "Computer_Science", "VT", "Virginia_Tech" },
            "Introduction to bioinformatics and computation biology");
        assertOutput("Successfully inserted record with ID 2\n"
            + "ID: 2, Title: Computational Biology and Bioinformatics in "
            + "CS at Virginia Tech\n"
            + "Date: 0610071600, Length: 60, X: 20, Y: 10, Cost: 30\n"
            + "Description: Introduction to bioinformatics "
            + "and computation biology\n"
            + "Keywords: Bioinformatics, computation_biology, Biology, "
            + "Computer_Science, VT, Virginia_Tech\n"
            + "size: 242");

        // Insert 10
        controller.insert(10, "Overview of HPC and CSE Research at VT",
            "0703301125", 35, (short)0, (short)0, 25, new String[] { "HPC",
                "CSE", "computer_science" },
            "Learn what kind of research is done on HPC and CSE at VT");
        assertOutput("Memory pool expanded to 1024 bytes\n"
            + "Successfully inserted record with ID 10\n"
            + "ID: 10, Title: Overview of HPC and CSE Research at VT\n"
            + "Date: 0703301125, Length: 35, X: 0, Y: 0, Cost: 25\n"
            + "Description: Learn what kind of research is done on HPC "
            + "and CSE at VT\n"
            + "Keywords: HPC, CSE, computer_science\n" + "size: 164");
    }


    /**
     * Test searching for seminar records based on their ID.
     */
    @Test
    public void testSampleSearch() throws Exception {

        // Insert 3
        controller.insert(3, "Computing Systems Research at VT", "0701250830",
            30, (short)30, (short)10, 17, new String[] {
                "high_performance_computing", "grids", "VT",
                "computer science" },
            "Seminar about the Computing systems research at VT");

        outputStream.reset();
        controller.search(3);
        assertOutput("Found record with ID 3:\n"
            + "ID: 3, Title: Computing Systems Research at VT\n"
            + "Date: 0701250830, Length: 30, X: 30, Y: 10, Cost: 17\n"
            + "Description: Seminar about the Computing systems "
            + "research at VT\n"
            + "Keywords: high_performance_computing, grids, VT,"
            + " computer science");

        // Attempt to search for a non-existing record
        controller.search(99);
        assertOutput("Search FAILED -- There is no record with ID 99");
    }
}
