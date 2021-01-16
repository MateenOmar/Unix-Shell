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
 * Concatenate Junit Tests
 * 
 * @author Kazi Shafin Shafayet
 * @version Nov 30, 2020
 *
 */
public class ConcatenateTest {

  Concatenate cat;
  FileSystemShell system;
  ErrorHandler errorHandler;
  
  @Before
  public void setup() {
    system = new MockFileSystemForCat();
    errorHandler = new ErrorHandler(system);
    cat = new Concatenate(system, errorHandler);
    ErrorHandler.resetErrorOccurred();
    System.out.println("====Test====");
  }
  
  @Test
  public void testGetCommandName() {
    assertEquals(cat.getCommandName(), "cat");
  }
  
  @Test
  public void testTooFewArgs() {
    String[] str = {"cat"};
    cat.run(str, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  
  @Test
  public void testPassUsePath() {
    String[] str = {"cat","/dir1/file1"};
    cat.run(str, "");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(Output.getLastPrinted(), "This is file1.\n");
  }
  
  @Test
  public void testFailUsePath() {
    String[] str = {"cat","/dir1/file4"};
    cat.run(str, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  
  @Test
  public void testPassMultipleFiles() {
    String[] str = {"cat", "file1", "file2"};
    cat.run(str, "");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(Output.getLastPrinted(), "This is file1.\n\n\n\nThis is file2.\n");
  }
  

  @Test
  public void testFailMultipleFiles() {
    String[] str = {"cat", "file1", "file4", "file2"};
    cat.run(str, "");
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  

  @Test
  public void testRedirection() {
    String[] str = {"cat", "file1"};
    cat.run(str, "> outFile");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(system.getCurrentNode().getFile("outFile").getData(),"This is file1.\n");
  }

  

}
