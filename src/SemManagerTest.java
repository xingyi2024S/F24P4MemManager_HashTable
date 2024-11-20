import java.io.IOException;
import org.junit.Test;
import student.TestCase;

/**
 * SemManagerTest
 */
public class SemManagerTest extends TestCase {

    /**
     * Test illegal arguments for SemManager.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArguments() throws IOException {
        String[] args = new String[2];
        args[0] = "16";
        args[1] = "2";

        SemManager.main(args);
    }

}
