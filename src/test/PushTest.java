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
import driver.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for testing Push
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class PushTest {

  FileSystemShell mockFileSystem;
  ErrorHandler errorHandler;
  Push pushd;

  @Before
  public void setUp() throws Exception {
    mockFileSystem = new MockFileSystem();
    errorHandler = new ErrorHandler(null);
    pushd = new Push(mockFileSystem, errorHandler);

    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();

    System.out.println("======= TEST =======");
  }

  /**
   * Test when too many arguments are passed in
   */
  @Test
  public void testTooManyArgs() {
    mockFileSystem.createDirectory("dir1");
    String[] str = {"pushd", "/dir1", "/dir2"};

    pushd.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }

  /**
   * Test when too few arguments are passed in
   */
  @Test
  public void testTooFewArgs() {
    mockFileSystem.createDirectory("dir1");
    String[] str = {"pushd"};

    pushd.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }

  /**
   * Test when a valid directory is tried to be pushd to
   */
  @Test
  public void testValid() {
    mockFileSystem.createDirectory("dir1");
    String[] str = {"pushd", "/dir1"};
    pushd.run(str);

    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals("/", mockFileSystem.getDirectoryStack().get(0));
    assertEquals("dir1", mockFileSystem.getCurrentNode().getName());
  }

  /**
   * Test when a invalid directory is tried to be pushd to
   */
  @Test
  public void testInvalid() {
    String[] str = {"pushd", "/dir"};
    pushd.run(str);

    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertTrue(mockFileSystem.getDirectoryStack().isEmpty());
  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("pushd", pushd.getCommandName());
  }

}
