import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * {Project Description Here}
 */

/**
 * The class containing the main method.
 *
 * @author {Your Name Here}
 * @version {Put Something Here}
 */

// On my honor:
// - I have not used source code obtained from another current or
// former student, or any other unauthorized source, either
// modified or unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

public class SemManager {

    /**
     * Main method to initialize and execute the program.
     * 
     * @param args
     *            Command line parameters:
     *            args[0] = initial memory pool size,
     *            args[1] = initial hash table size,
     *            args[2] = path to command file.
     */
    public static void main(String[] args) {
        // Ensure correct number of arguments
        if (args.length < 3) {
            System.out.println(
                "Usage: java SemManager <memory pool size> <hash table size> <command file>");
            return;
        }

        try {
            int memoryPoolSize = Integer.parseInt(args[0]);
            int hashTableSize = Integer.parseInt(args[1]);
            String commandFilePath = args[2];

            Controller controller = new Controller(hashTableSize,
                memoryPoolSize, 128); 
            CommandParser commandParser = new CommandParser(controller);

            try (BufferedReader reader = new BufferedReader(new FileReader(
                commandFilePath))) {
                commandParser.parseCommands(reader);
            }

        }
        catch (NumberFormatException e) {
            System.out.println(
                "Error: Invalid memory pool or hash table size. Must be integers.");
        }
        catch (IOException e) {
            System.out.println("Error: Could not read command file " + args[2]);
        }
    }
}
