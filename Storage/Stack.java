package Storage;

import java.util.ArrayList;
import java.util.HashMap;

public class Stack {
  public ArrayList<HashMap<String, Integer>> store;

  /**
   * Store Integers under Keys in Scopes <lists>. Reads first from the newest
   * scope.
   */
  public Stack() {
    store = new ArrayList<HashMap<String, Integer>>();
    addScope();
  }

  /**
   * Read a value of key from stack. Starts from newest scope.
   * 
   * @param key
   * @return
   */
  public Integer read(String key) {
    Integer result;
    try {

      // Check if key is integer
      result = Integer.parseInt(key);
      return result;

    } catch (Exception e) {

      // Then search
      for (int i = 0; i < store.size(); i++) {
        int pos = store.size() - 1 - i;
        result = store.get(pos).get(key);
        if (result != null)
          return result;
      }
    }
    System.err.println("Loop condition " + key + " missing");
    System.exit(1);
    return null;
  }

  /**
   * Write over a key, starting from newest scope.
   * 
   * @param key
   * @param value
   * @return
   */
  public boolean write(String key, Integer value) {
    for (int i = 0; i < store.size(); i++) {
      int pos = store.size() - 1 - i;
      if (store.get(pos).get(key) != null) {
        store.get(pos).put(key, value);
        return true;
      }
    }
    store.get(store.size() - 1).put(key, value);
    return false;
  }

  /**
   * Initialise value in newest scope.
   * 
   * @param key
   * @param value
   */
  public void init(String key, Integer value) {
    store.get(store.size() - 1).put(key, value);
  }

  /**
   * Adds a new scope to the stack.
   */
  public void addScope() {
    HashMap<String, Integer> scope = new HashMap<String, Integer>();
    store.add(scope);
  }

  /**
   * Removes the newest scope from the stack.
   */
  public void removeScope() {
    store.remove(store.size() - 1);
  }
  /**
   * Print out contents of all scopes from the stack, in order of oldest to
   * newest.
   */
  public void print() {
    for (int i = 0; i < store.size(); i++) {
      int space = i;
      store.get(i).entrySet().forEach(entry -> {
        System.out.println((" ").repeat(space * 2) + entry.getKey() + " -> " + entry.getValue());
      });
    }
  };
}