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
 * JUnit test class for testing ErrorHandler
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class ErrorHandlerTest {

  ErrorHandler errorHandler;

  @Before
  public void setUp() throws Exception {
    errorHandler = new ErrorHandler(null);

    ErrorHandler.resetErrorOccurred();

    System.out.println("======= TEST =======");
  }

  /**
   * Test when command is valid
   */
  @Test
  public void testCheckIfLegalCommand1() {
    String[] str = {"exit", "pickles"};

    assertTrue(errorHandler.checkIfLegalCommand(str));
  }
  
  /**
   * Test when command is valid
   */
  @Test
  public void testCheckIfLegalCommand4() {
    String[] str = {"pushd"};

    assertTrue(errorHandler.checkIfLegalCommand(str));
  }

  /**
   * Test when command is invalid
   */
  @Test
  public void testCheckIfLegalCommand2() {
    String[] str = {"penny", "pickles"};    

    assertFalse(errorHandler.checkIfLegalCommand(str));
  }

  /**
   * Test when command is invalid
   */
  @Test
  public void testCheckIfLegalCommand3() {
    String[] str = {"push"};

    assertFalse(errorHandler.checkIfLegalCommand(str));
  }

  /**
   * Test when command is invalid
   */
  @Test
  public void testCheckIfLegalCommand5() {
    String[] str = {"", "pickles"};

    assertFalse(errorHandler.checkIfLegalCommand(str));
  }

}
