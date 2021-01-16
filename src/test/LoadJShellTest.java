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
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import driver.ErrorHandler;
import driver.FileSystemShell;
import driver.LoadJShell;
import driver.Output;

/**
 * JUnit test class for testing LoadJShell
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class LoadJShellTest {

  FileSystemShell mockFileSystem;
  ErrorHandler errorHandler;
  LoadJShell loadJShell;
  ArrayList<String> userHistory;

  @Before
  public void setUp() throws Exception {
    mockFileSystem = new MockFileSystem();
    errorHandler = new ErrorHandler(null);
    userHistory = new ArrayList<String>();
    loadJShell = new LoadJShell(this.mockFileSystem, this.errorHandler, userHistory);

    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();

    System.out.println("======= TEST =======");
  }

  /**
   * Test when too many arguments are passed in
   */
  @Test
  public void testTooManyArgs() {
    String[] str = {"loadJShell", "savePass.txt", "saveFail.txt"};
    userHistory.add(str[1]);

    loadJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }

  /**
   * Test when too few arguments are passed in
   */
  @Test
  public void testTooFewArgs() {
    String[] str = {"loadJShell"};

    loadJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }

  /**
   * Test when a valid JShell .txt file is passed in
   */
  @Test
  public void testValid() {
    String[] str = {"loadJShell", "savePass.txt"};
    userHistory.add(str[1]);

    loadJShell.run(str);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
  }

  /**
   * Test when a invalid JShell .txt file is passed in
   */
  @Test
  public void testInvalid() {
    String[] str = {"loadJShell", "saveFail.txt"};
    userHistory.add(str[1]);

    loadJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("loadJShell", loadJShell.getCommandName());
  }

}
