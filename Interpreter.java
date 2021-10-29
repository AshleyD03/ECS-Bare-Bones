import java.util.Scanner;

import Storage.LoopCondition;
import Storage.Stack;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;

public class Interpreter {

  /**
   * Main.
   * 
   * @param args
   */
  public static void main(String[] args) {

    // Take file path
    String path = input("What file do you want to run?");

    // Read file as list of strings
    ArrayList<String> lines = null;
    try {
      lines = loadFile(path);
    } catch (IOException e) {
      System.err.println("Error caught with load-file" + e);
      System.exit(0);
    }

    // Init lineNum, loopList and stack
    Integer lineNum = 0;
    Stack stack = new Stack();
    ArrayList<LoopCondition> loops = new ArrayList<LoopCondition>();

    // Begin loop through lines
    while (lineNum < lines.size()) {

      // Interp line
      String line = lines.get(lineNum);
      String[] fields = interpLine(line);

      // Perform lines command from first field
      switch (fields[0]) {
      case "clear":
        stack.init(fields[1], 0);
        break;

      case "incr":
        Integer inc = stack.read(fields[1]) + 1;
        stack.write(fields[1], inc);
        break;

      case "decr":
        Integer dec = stack.read(fields[1]) - 1;
        stack.write(fields[1], dec);
        break;

      case "while":
        LoopCondition newLoop = new LoopCondition(fields[1], fields[3], fields[2], lineNum, stack);
        if (newLoop.isTrue()) {
          loops.add(newLoop);
          stack.addScope();
        }
        break;

      case "end":
        LoopCondition curLoop = loops.get(loops.size() - 1);
        if (curLoop.isTrue()) {
          lineNum = curLoop.lineNum;
        } else {
          loops.remove(loops.size() - 1);
          stack.removeScope();
        }
        break;

      case "print":
        System.out.println(stack.read(fields[1]) + "\n");
        break;
      }
      ;
      lineNum++;
    }
    ;
  };

  /**
   * Breaks down a line into all it's fields Method from :
   * https://stackoverflow.com/questions/2932392/java-how-to-replace-2-or-more-spaces-with-single-space-in-string-and-delete-lead
   * 
   * @param line
   * @return
   */
  public static String[] interpLine(String line) {
    line = line.trim().replaceAll(" +", " ");
    return line.split(" ");
  }

  /**
   * loads file from directory.
   * 
   * @param path String.
   * @return List<String>.
   * @throws IOException
   */
  public static ArrayList<String> loadFile(String path) throws IOException {
    String text = Files.readString(Path.of(path));
    ArrayList<String> list = new ArrayList<String>(Arrays.asList(text.split(";", 0)));
    return list;
  }

  /**
   * Python input function.
   * 
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
