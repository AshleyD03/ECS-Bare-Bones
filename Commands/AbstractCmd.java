package Commands;

import java.util.ArrayList;
import Storage.Stack;

public abstract class AbstractCmd {
  public ArrayList<Field> fields = new ArrayList<Field>();
  public boolean fieldLimit = true;
  Stack stack;
  public String name;

  public AbstractCmd(String name, Stack stack) {
    this.name = name;
    this.stack = stack;
  }

  public void addFieldValues (String[] values) {
    if (fieldLimit && values.length > fields.size()) {
      System.err.println("Error adding too many values to command fields");
      System.exit(1);
    }
    if (values.length < fields.size()) {
      System.err.println("Error adding too few values to command fields");
      System.exit(1);
    }

    // Ignore first
    for (int i=0; i<values.length - 1; i++) {
      Field field;
      try {
        field = fields.get(i);
      } catch (Exception e) {
        field = new Field( fields.get(i-1).type );
        fields.add(field);
      }
      field.val = values[i + 1];
    }
  }

  public boolean checkField () {
    fields.forEach(field -> {
      String val = field.val;
      boolean error = false;
      switch (field.type) {

        case "int":
          try {
            stack.read(val);
          } catch (Exception e) {
            error = true;
          }
          break;

        case "operator": 
          if (val != "not" && val != "is") error = true;
          break;

        default: 
          error = true;

      }

      if (error) {
        System.err.println("Command param error");
        System.exit(1);
      }
    });
    return true;
  }
}

class Field {
  public String type, val;
  public Field (String type) {
    this.type = type;
  }
}