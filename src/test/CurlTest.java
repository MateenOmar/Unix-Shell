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
import driver.Curl;
import driver.ErrorHandler;
import driver.FileSystemShell;
import driver.Output;

/**
 * JUnit test class for testing Curl
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class CurlTest {

  FileSystemShell mockFileSystem;
  ErrorHandler errorHandler;
  Curl curl;

  @Before
  public void setUp() throws Exception {
    mockFileSystem = new MockFileSystem();
    errorHandler = new ErrorHandler(null);
    curl = new Curl(mockFileSystem, errorHandler); 

    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();

    System.out.println("======= TEST =======");
  }
  
  /**
   * Test when too many arguments are passed in
   */
  @Test
  public void testTooManyArgs() {
    String[] str = {"curl", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt", 
        "http://omarmateen.me/experience.html"};

    curl.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Test when too few arguments are passed in
   */
  @Test
  public void testTooFewArgs() {
    String[] str = {"curl"};

    curl.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Test a valid URL with a .txt extension
   */
  @Test
  public void testValidURLtxtFile() {
    String[] str = {"curl", "http://www.cs.cmu.edu/~spok/grimmtmp/073.txt"};

    curl.run(str);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertTrue(mockFileSystem.getCurrentNode().getFile("073txt") != null);
  }

  /**
   * Test a valid URL with a .html extension
   */
  @Test
  public void testValidURLhtmlFile() {
    String[] str = {"curl", "http://omarmateen.me/experience.html"};

    curl.run(str);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertTrue(mockFileSystem.getCurrentNode().getFile("experiencehtml") != null);
  }
  
  /**
   * Test an invalid URL
   */
  @Test
  public void testInvalidURL() {
    String[] str = {"curl", "http://www.ub.edu/gilcub/SIMPLE/simple.html"};

    curl.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("invalid URL\n", Output.getLastPrinted());
  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("curl", curl.getCommandName());
  }

}
