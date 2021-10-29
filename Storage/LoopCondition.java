package Storage;

public class LoopCondition {
  String a, b, rule;
  public int lineNum;
  private Stack stack;

  /**
   * Wrapper to store conditions of if a loop should occur.
   * 
   * @param a
   * @param b
   * @param rule
   * @param lineNum
   */
  public LoopCondition(String a, String b, String rule, int lineNum, Stack stack) {
    this.a = a;
    this.b = b;
    this.rule = rule;
    this.lineNum = lineNum;
    this.stack = stack;
  }

  /**
   * Check if the loop condition is currently true or false;
   * 
   * @param stack
   * @return
   */
  public boolean isTrue() {
    int a = stack.read(this.a);
    int b = stack.read(this.b);
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