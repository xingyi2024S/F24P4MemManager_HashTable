import org.junit.Test;
import student.TestCase;

/**
 * FreeBlock test.
 * 
 * @author Zhengyang Lu
 * @author Xingyi Wang
 * @version 2024.11.10
 */
public class FreeBlockTest extends TestCase {
    
    /**
     * Test adding free blocks.
     */
    @Test
    public void addFreeBlockTest() {
        FreeBlock freeBlock01 = new FreeBlock(0, 1);
        FreeBlock freeBlock02 = new FreeBlock(3, 5);
        freeBlock01.setNext(freeBlock02);
        freeBlock02.setPrevious(freeBlock01);
        FreeBlock.addFreeBlock(freeBlock01, 2, 1);
        FreeBlock.printFreeBlocks(freeBlock01);
        assertEquals(systemOut().getHistory(), "(0,1) -> (2,6)\n");
        systemOut().clearHistory();
    }

}
