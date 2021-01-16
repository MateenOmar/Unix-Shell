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
 * Echo Junit Tests
 * 
 * @author Kazi Shafin Shafayet
 * @version Nov 30, 2020
 *
 */
public class EchoTest {

  FileSystemShell system;
  ErrorHandler errorHandler;
  Echo echo;
  
  @Before
  public void setUp() throws Exception{
    system = new MockFileSystemForEcho();
    errorHandler = new ErrorHandler(system);
    echo = new Echo(system, errorHandler);
    System.out.println("====Test====");
  }
  
  @Test
  public void testString1() {
    echo.run("echo \"Test string\"", false);
    assertEquals(Output.getLastPrinted(), "Test string\n");
  }
  
  @Test
  public void testString2() {
    echo.run("echo \"Test \"String\"", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  
  @Test
  public void testString3() {
    echo.run("echo \"Test> String\"", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  
  @Test
  public void testRedirection1() {
    echo.run("echo \"Test String\"> file1", false);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(system.getCurrentNode().getFile("file1").getData(), "Test String");
  }
  
  @Test
  public void testRedirection2() {
    echo.run("echo \"Test String\"> file1", false);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(system.getCurrentNode().getFile("file1").getData(), "Test String");
  }

  @Test
  public void testRedirection3() {
    echo.run("echo \"Test String\" > file1", false);
    echo.run("echo \" 3\">> file1", false);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(system.getCurrentNode().getFile("file1").getData(), "Test String 3");
  }

  @Test
  public void testRedirection4() {
    echo.run("echo \"Test String\" > file1", false);
    echo.run("echo \" 3\" >> file1", false);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertEquals(system.getCurrentNode().getFile("file1").getData(), "Test String 3");
  }

  @Test
  public void testRedirection5() {
    echo.run("echo \"Test String\"> >file1", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }

  @Test
  public void testRedirection6() {
    echo.run("echo \"Test String\">>>file1", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }

  @Test
  public void testRedirection7() {
    echo.run("echo \"Test String\" file1", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  

  @Test
  public void testRedirection8() {
    echo.run("echo > \"Test String\" > file1", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
  
  @Test
  public void testRedirection9() {
    echo.run("echo \"Test String\" > ", false);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }
}
