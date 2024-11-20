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
        FreeBlock block01 = new FreeBlock(0, 1);
        FreeBlock block02 = new FreeBlock(3, 5);
        block01.setNext(block02);
        block02.setPrevious(block01);
        FreeBlock.addFreeBlock(block01, 2, 1);
        FreeBlock.printFreeBlocks(block01);
        assertEquals(systemOut().getHistory(), "(0,1) -> (2,6)\n");
        systemOut().clearHistory();
    }

    /**
     * Test removing free blocks.
     */
    @Test
    public void removeFreeBlockTest() {
        FreeBlock block01 = new FreeBlock(0, 1);
        FreeBlock block02 = new FreeBlock(2, 2);
        FreeBlock block03 = new FreeBlock(5, 3);
        block01.setNext(block02);
        block02.setPrevious(block01);
        block02.setNext(block03);
        block03.setPrevious(block02);
        FreeBlock.printFreeBlocks(block01);
        assertEquals(systemOut().getHistory(), "(0,1) -> (2,2) -> (5,3)\n");
        systemOut().clearHistory();
        FreeBlock.remove(block01, block02);
        FreeBlock.printFreeBlocks(block01);
        assertEquals(systemOut().getHistory(), "(0,1) -> (5,3)\n");
        systemOut().clearHistory();
    }

}
