public class LoopCondition {
  String a, b, rule;
  int lineNum;

  /**
   * Wrapper to store conditions of if a loop should occur.
   * 
   * @param a
   * @param b
   * @param rule
   * @param lineNum
   */
  public LoopCondition(String a, String b, String rule, int lineNum) {
    this.a = a;
    this.b = b;
    this.rule = rule;
    this.lineNum = lineNum;
  }

  /**
   * Check if a string can act as an integer or is actualy a key in a stack.
   * 
   * @param val
   * @param stack
   * @return
   */
  private int checkVal(String val, Stack stack) {
    Integer result;
    try {
      result = Integer.parseInt(val);
    } catch (Exception e) {
      // If not int, try stack key
      result = stack.read(val);
    }
    if (result == null) {
      System.err.println("Loop condition " + val + " missing");
      System.exit(1);
    }
    return result;
  }

  /**
   * Check if the loop condition is currently true or false;
   * 
   * @param stack
   * @return
   */
  public boolean isTrue(Stack stack) {
    int a = checkVal(this.a, stack);
    int b = checkVal(this.b, stack);
    boolean result = false;

    switch (this.rule) {
    case "not":
      result = (a != b);
      break;
    case "is":
      result = (a == b);
      break;
    }

    return result;
  }
}