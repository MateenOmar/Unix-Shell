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

/**
 * JUnit test class for testing Pop
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.ErrorHandler;
import driver.FileSystemShell;
import driver.Output;
import driver.Pop;
import driver.Push;

public class PopTest {

  FileSystemShell mockFileSystem;
  ErrorHandler errorHandler;
  Pop popd;
  Push pushd;

  @Before
  public void setUp() throws Exception {
    mockFileSystem = new MockFileSystem();
    errorHandler = new ErrorHandler(null);
    pushd = new Push(mockFileSystem, errorHandler);
    popd = new Pop(mockFileSystem, errorHandler);

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
    String[] str1 = {"pushd", "/dir1"};
    String[] str2 = {"popd", "pickles"};

    pushd.run(str1);

    popd.run(str2);  
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }

  /**
   * Popping after a valid pushd itteration
   */
  @Test
  public void testValid() {
    mockFileSystem.createDirectory("dir1");
    String[] str1 = {"pushd", "/dir1"};
    String[] str2 = {"popd"};

    pushd.run(str1);
    assertEquals("dir1", mockFileSystem.getCurrentNode().getName());
    assertFalse(ErrorHandler.checkIfErrorOccurred());

    ErrorHandler.resetErrorOccurred();
    popd.run(str2);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals("", mockFileSystem.getCurrentNode().getName());
  }

  /**
   * Popping after an invalid pushd itteration
   */
  @Test
  public void testInvalid() {
    mockFileSystem.createDirectory("dir1");
    String[] str1 = {"pushd", "/dir"};
    String[] str2 = {"popd"};

    pushd.run(str1);
    assertEquals("", mockFileSystem.getCurrentNode().getName());
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory or file not found\n", Output.getLastPrinted());

    Output.resetLastPrinted();
    ErrorHandler.resetErrorOccurred();
    popd.run(str2);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("", mockFileSystem.getCurrentNode().getName());
    assertEquals("directory stack is empty\n", Output.getLastPrinted());
  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("popd", popd.getCommandName());
  }

}
