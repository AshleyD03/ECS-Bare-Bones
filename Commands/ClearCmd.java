package Commands;

import Storage.Stack;

public class ClearCmd extends AbstractCmd {
  public ClearCmd (Stack stack) {
    super("Clear", stack);
    fieldLimit = false;
  }

  public void run (String[] values) {
    addFieldValues(values);
    fields.forEach(field -> stack.init(field.val, 0));
  }
}
