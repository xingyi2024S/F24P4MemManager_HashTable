import java.io.BufferedReader;
import java.io.IOException;

/**
 * CommandParser class for reading in commands and calling the appropriate
 * functions.
 * 
 * @author Xingyi Wang
 * @author Zhengyang Lu
 * @version 2024.11.12
 */
public class CommandParser {
    private Controller controller;

    /**
     * Constructor for the CommandParser class.
     * 
     * @param controller
     *            The controller managing the hash table and memory pool.
     */
    public CommandParser(Controller controller) {
        this.controller = controller;
    }


    /**
     * Parses the the "verb" of the command and calls the appropriate function.
     * 
     * @param reader
     *            The BufferedReader with the commands.
     */
    public void parseCommands(BufferedReader reader) {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("insert")) {
                    handleInsert(reader, line);
                }
                else if (line.startsWith("delete")) {
                    handleDelete(line);
                }
                else if (line.startsWith("search")) {
                    handleSearch(line);
                }
                else if (line.startsWith("print")) {
                    handlePrint(line);
                }

            }

        }
        catch (IOException e) {
            System.out.println("Error reading commands: " + e.getMessage());
        }
    }


    /**
     * For dealing with the "insert" command.
     * 
     * @param reader
     *            The BufferedReader with the commands.
     * @param firstLine
     *            The first line of the command.
     * @throws IOException
     */
    private void handleInsert(BufferedReader reader, String firstLine)
        throws IOException {
        int id = Integer.parseInt(firstLine.split("\\s+")[1].trim());

        String title = reader.readLine().trim();
        String[] details = reader.readLine().trim().split("\\s+");
        String keywordsLine = reader.readLine().trim();
        String description = reader.readLine().trim().replaceAll("\\s+", " ");

        String date = details[0];
        int length = Integer.parseInt(details[1]);
        short x = Short.parseShort(details[2]);
        short y = Short.parseShort(details[3]);
        int cost = Integer.parseInt(details[4]);
        String[] keywords = keywordsLine.split("\\s+");

        controller.insert(id, title, date, length, x, y, cost, keywords,
            description);
    }


    /**
     * For dealing with the "delete" command.
     * 
     * @param line
     *            The string after the "delete" keyword.
     */
    private void handleDelete(String line) {
        int id = Integer.parseInt(line.split("\\s+")[1].trim());
        controller.delete(id);
    }


    /**
     * For dealing with the "search" command.
     * 
     * @param line
     *            The string after the "search" keyword.
     */
    private void handleSearch(String line) {
        int id = Integer.parseInt(line.split("\\s+")[1].trim());
        try {
            controller.search(id);
        }
        catch (Exception e) {
            // Exception is caught
        }
    }


    /**
     * For dealing with the "print" command.
     * 
     * @param line
     *            The string after the "print" keyword.
     */
    private void handlePrint(String line) {
        if (line.contains("hashtable")) {
            controller.printHashTable();
        }
        else if (line.contains("blocks")) {
            controller.printFreeBlocks();
        }
        else {
            System.out.println(
                "Invalid print command." +
                    "Please specify 'hashtable' or 'freeblock'.");
        }
    }
}
