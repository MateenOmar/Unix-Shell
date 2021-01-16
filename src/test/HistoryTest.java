// **********************************************************
// Assignment2:
// Student1:Omar Bin Mateen
// UTORID user_name:mateenom
// UT Student #:1006138840
// Author:Omar Bin Mateen
//
// Student2:Kevin Fernandes
// UTORID user_name:fern1133
// UT Student #:1006389784
// Author:Kevin Fernandes
//
// Student3:Jesse Yao
// UTORID user_name:yaojesse
// UT Student #:1006312269
// Author:Jesse Yao
//
// Student4:Kazi Shafin Shafayet
// UTORID user_name:shafaye1
// UT Student #:1006266746
// Author:Kazi Shafin Shafayet
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package test;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import driver.*;
import java.util.*;

public class HistoryTest {
  
  History history;
  FileSystemShell system;
  ErrorHandler errorHandler;
  
  @Before
  public void setup() {
    // this should be ok if only testing redirection once
    system = FileSystem.createFileSystemInstance();
    errorHandler = new ErrorHandler(system);
    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();
    ArrayList<String> userHistory = new ArrayList<String>();
    history = new History(userHistory, system, errorHandler);
    userHistory.add("command 1");
    userHistory.add("command 2");
    userHistory.add("command 3");
    userHistory.add("command 4");
    userHistory.add("command 5");
    System.out.println("======= TEST =======");
  }
  
  @Test
  public void testTooManyArgs() {
    String[] args = {"history", "1", "2"};
    history.run(args, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }
  
  @Test
  public void testListAll() {
    String[] args = {"history"};
    history.run(args, "");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    String out =
        "1. command 1\n" +
        "2. command 2\n" +
        "3. command 3\n" +
        "4. command 4\n" +
        "5. command 5\n\n";
    assertEquals(out, Output.getLastPrinted());
  }
  
  @Test
  public void testListSome() {
    String[] args = {"history", "4"};
    history.run(args, "");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    String out =
        "2. command 2\n" +
        "3. command 3\n" +
        "4. command 4\n" +
        "5. command 5\n\n";
    assertEquals(out, Output.getLastPrinted());
  }
  
  @Test
  public void testListExceeded() {
    String[] args = {"history", "7"};
    history.run(args, "");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    String out =
        "1. command 1\n" +
        "2. command 2\n" +
        "3. command 3\n" +
        "4. command 4\n" +
        "5. command 5\n\n";
    assertEquals(out, Output.getLastPrinted());
  }
  
  @Test
  public void testListNone() {
    String[] args = {"history", "0"};
    history.run(args, "");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    String out = "\n";
    assertEquals(out, Output.getLastPrinted());
  }
  
  @Test
  public void testNegative() {
    String[] args = {"history", "-2"};
    history.run(args, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    String out = "invalid argument: -2\n";
    assertEquals(out, Output.getLastPrinted());
  }
  
  @Test
  public void testFloat() {
    String[] args = {"history", "2.5"};
    history.run(args, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    String out = "invalid argument: 2.5\n";
    assertEquals(out, Output.getLastPrinted());
  }
  
  @Test
  public void testNaN() {
    String[] args = {"history", "wordswordswords"};
    history.run(args, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    String out = "invalid argument: wordswordswords\n";
    assertEquals(out, Output.getLastPrinted());
  }

  @Test
  public void testRedirection() {
    String[] args = {"history"};
    history.run(args, "> outFile");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    String out =
        "1. command 1\n" +
        "2. command 2\n" +
        "3. command 3\n" +
        "4. command 4\n" +
        "5. command 5\n";
    assertEquals(out, system.getCurrentNode().getFile("outFile").getData());
  }
}
