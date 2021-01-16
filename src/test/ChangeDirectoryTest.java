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
import org.junit.Before;
import org.junit.Test;
import driver.*;

/**
 * JUnit test for ChangeDirectory
 * @author Jesse Yao
 */
public class ChangeDirectoryTest {

  ChangeDirectory cd;
  FileSystemShell system;
  ErrorHandler errorHandler;

  @Before
  public void setup() {
    system = new MockFileSystemForCd();
    errorHandler = new ErrorHandler(system);
    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();
    cd = new ChangeDirectory(system, errorHandler);
    System.out.println("======= TEST =======");
  }

  /**
   * Check behavior for too few arguments (< 2)
   * Expected behavior: give appropriate error
   */
  @Test
  public void testTooFewArgs() {
    String[] args = {"cd"};
    cd.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals(Output.getLastPrinted(), "not enough arguments\n");
  }

  /**
   * Check behavior for too many arguments (> 2)
   * Expected behavior: give appropriate error
   */
  @Test
  public void testTooManyArgs() {
    String[] args = {"cd", "/dir1", "/dir2"};
    cd.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals(Output.getLastPrinted(), "too many arguments\n");
  }

  /**
   * Check behavior for changing to a valid path
   * Expected behavior: 
   * - no errors
   * - file system's currentNode is set to a directory with name dir1
   */
  @Test
  public void testValidPath() {
    String[] args = {"cd", "/dir1"};
    cd.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(system.getCurrentNode().getName(), "dir1");
  }

  /**
   * Check behavior for changing to an invalid path
   * Expected behavior:
   * - appropriate error is given
   * - currentNode is not changed
   */
  @Test
  public void testInvalidPath() {
    String[] args = {"cd", "//3@!9)/>>>#@!#/"};
    cd.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertTrue(system.getCurrentNode() == system.getRoot());
    assertEquals(Output.getLastPrinted(), "directory not found\n");
  }

}
