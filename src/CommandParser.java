import java.io.BufferedReader;
import java.io.IOException;

public class CommandParser {
    private Controller controller;

    public CommandParser(Controller controller) {
        this.controller = controller;
    }


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


    private void handleDelete(String line) {
        int id = Integer.parseInt(line.split("\\s+")[1].trim());
        controller.delete(id);
    }


    private void handleSearch(String line) {
        int id = Integer.parseInt(line.split("\\s+")[1].trim());
        try {
            controller.search(id);
        }
        catch (Exception e) {
            // Exception is caught
        }
    }


    private void handlePrint(String line) {

    }
}
