import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;

public class Interpreter {

  /**
   * Main.
   * @param args
   */
  public static void main(String[] args) {
    // Take file path
    String path = input("What file do you want to run?");
  
    // Read File as a list
    List<String> file = null;
    try {
      file = loadFile(path);
    } catch (IOException e) {
      System.err.println("Error caught with load-file");
      System.err.println(e);
      System.exit(0);
    }

    // Start recursive line reads from top
    readLines(file);
    System.out.println("Complete :D");
  }

  /**
   * loads file from directory.
   * @param path String.
   * @return List<String>.
   * @throws IOException
   */
  public static List<String> loadFile(String path) throws IOException {
    String text = Files.readString(Path.of(path));
    List<String> list = Arrays.asList(text.split(";", 0));
    return list;
  }

  public static void readLines(List<String> lines) {

    HashMap<String, Integer> heap = new HashMap<String, Integer>();

    for(int i=0; i < lines.size() - 1; i++) {

      String line = lines.get(i);
      String[] fields = abstractLine(line);

      switch (fields[0]) {
        case "clear":
          heap.put(fields[1], 0);
          break;

        case "incr":
          heap.put(fields[1], heap.get(fields[1]) + 1);
          break;

        case "decr":
          heap.put(fields[1], heap.get(fields[1]) - 1);
          break;

        case "while":
          List<String> loopList = new ArrayList<String>();
          int x = i;
          while (abstractLine(lines.get(x + 1))[0] != "break") {
            loopList.add(lines.get(x));
            x++;
          }
          // Add Condition
          while ( false ) readLines(loopList);
          break;

        case "print":
          System.out.println(heap.get(fields[1]) + "\n");
          break;
      }
    }
  }

  /**
   * Breaks down a line into all it's fields
   * @param line
   * @return
   */
  public static String[] abstractLine (String line) {
    // https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
    line = line.trim().replaceAll(" +", " ");
    return line.split(" ");
  }

  /**
   * Python input function.
   * @param msg
   * @return
   */
  public static String input(String msg) {
    System.out.println(msg);

    Scanner scan = new Scanner(System.in);
    String input = scan.nextLine();
    scan.close();

    return input;
  }
}
