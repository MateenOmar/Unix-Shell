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
 * JUnit test class for testing Exit
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class ExitTest {

  Exit exit;
  ErrorHandler errorHandler;

  @Before
  public void setUp() throws Exception {
    exit = new Exit();
    errorHandler = new ErrorHandler(null);

    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();

    System.out.println("======= TEST =======");
  }

  @Test
  public void testIsShellOpen() {
    assertTrue(exit.isShellOpen());
  }

  /**
   * Test when too many arguments are passed in
   */
  @Test
  public void testTooManyArgs() {
    String[] str = {"exit", "pickles"};

    exit.run(str);
    assertEquals("too many arguments\n", Output.getLastPrinted());
    assertTrue(ErrorHandler.checkIfErrorOccurred());
  }

  /**
   * Test when exit is called correctly
   */
  @Test
  public void testValid() {
    String[] str = {"exit"};

    exit.run(str);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertFalse(exit.isShellOpen());
  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("exit", exit.getCommandName());
  }

}
