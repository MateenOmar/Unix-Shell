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
import driver.Output;
import driver.SaveJShell;

/**
 * JUnit test class for testing SaveJShell
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class SaveJShellTest {

  FileSystemShell mockFileSystem;
  ErrorHandler errorHandler;
  SaveJShell saveJShell;
  ArrayList<String> userHistory;

  @Before
  public void setUp() throws Exception {
    mockFileSystem = new MockFileSystem();
    errorHandler = new ErrorHandler(null);
    userHistory = new ArrayList<String>();

    saveJShell = new SaveJShell(this.mockFileSystem, this.errorHandler, userHistory);
    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();

    System.out.println("======= TEST =======");
  }

  /**
   * Test when too many arguments are passed in
   */
  @Test
  public void testTooManyArgs() {
    String[] str = {"saveJShell", "savePass.txt", "save.txt"};

    mockFileSystem.getRoot().addDirectory("dir1");
    userHistory.add("mkdir dir1");
    mockFileSystem.getRoot().addDirectory("dir2");
    userHistory.add("mkdir dir2");
    userHistory.add("cd dir1");
    mockFileSystem.getRoot().getDirectory("dir1").addDirectory("d1");
    userHistory.add("mkdir d1");
    userHistory.add("cd d1");
    mockFileSystem.getRoot().getDirectory("dir1").getDirectory("d1").addDirectory("d2");
    userHistory.add("mkdir d2");
    userHistory.add("cd /");
    mockFileSystem.getRoot().addFile("pickles", "file1");

    saveJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }

  /**
   * Test when too few arguments are passed in
   */
  @Test
  public void testTooFewArgs() {
    String[] str = {"saveJShell"};

    mockFileSystem.getRoot().addDirectory("dir1");
    userHistory.add("mkdir dir1");
    mockFileSystem.getRoot().addDirectory("dir2");
    userHistory.add("mkdir dir2");
    userHistory.add("cd dir1");
    mockFileSystem.getRoot().getDirectory("dir1").addDirectory("d1");
    userHistory.add("mkdir d1");
    userHistory.add("cd d1");
    mockFileSystem.getRoot().getDirectory("dir1").getDirectory("d1").addDirectory("d2");
    userHistory.add("mkdir d2");
    userHistory.add("cd /");
    mockFileSystem.getRoot().addFile("pickles", "file1");

    saveJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }

  /**
   * Test when a valid file name is given
   */
  @Test
  public void testValid() {
    String[] str = {"saveJShell", "savePass.txt"};

    mockFileSystem.getRoot().addDirectory("dir1");
    userHistory.add("mkdir dir1");
    mockFileSystem.getRoot().addDirectory("dir2");
    userHistory.add("mkdir dir2");
    userHistory.add("cd dir1");
    mockFileSystem.getRoot().getDirectory("dir1").addDirectory("d1");
    userHistory.add("mkdir d1");
    userHistory.add("cd d1");
    mockFileSystem.getRoot().getDirectory("dir1").getDirectory("d1").addDirectory("d2");
    userHistory.add("mkdir d2");
    userHistory.add("cd /");
    mockFileSystem.getRoot().addFile("pickles", "file1");

    saveJShell.run(str);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
  }
  
  /**
   * Test when only a path is given and no save file
   */
  @Test
  public void testInvalid1() {
    String[] str = {"saveJShell", "/Users/Pickles/Desktop"};

    mockFileSystem.getRoot().addDirectory("dir1");
    userHistory.add("mkdir dir1");
    mockFileSystem.getRoot().addDirectory("dir2");
    userHistory.add("mkdir dir2");
    userHistory.add("cd dir1");
    mockFileSystem.getRoot().getDirectory("dir1").addDirectory("d1");
    userHistory.add("mkdir d1");
    userHistory.add("cd d1");
    mockFileSystem.getRoot().getDirectory("dir1").getDirectory("d1").addDirectory("d2");
    userHistory.add("mkdir d2");
    userHistory.add("cd /");
    mockFileSystem.getRoot().addFile("pickles", "file1");

    saveJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found and/or file name not valid\n", Output.getLastPrinted());
  }
  
  /**
   * Test when a valid file name is given but an invalid path is given
   */
  @Test
  public void testInvalid2() {
    String[] str = {"saveJShell", "/Users/Pickles/Desktop/save2.txt"};

    mockFileSystem.getRoot().addDirectory("dir1");
    userHistory.add("mkdir dir1");
    mockFileSystem.getRoot().addDirectory("dir2");
    userHistory.add("mkdir dir2");
    userHistory.add("cd dir1");
    mockFileSystem.getRoot().getDirectory("dir1").addDirectory("d1");
    userHistory.add("mkdir d1");
    userHistory.add("cd d1");
    mockFileSystem.getRoot().getDirectory("dir1").getDirectory("d1").addDirectory("d2");
    userHistory.add("mkdir d2");
    userHistory.add("cd /");
    mockFileSystem.getRoot().addFile("pickles", "file1");

    saveJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found and/or file name not valid\n", Output.getLastPrinted());
  }
  
  /**
   * Test when an invalid file name is given
   */
  @Test
  public void testInvalid3() {
    String[] str = {"saveJShell", "save>.txt"};

    mockFileSystem.getRoot().addDirectory("dir1");
    userHistory.add("mkdir dir1");
    mockFileSystem.getRoot().addDirectory("dir2");
    userHistory.add("mkdir dir2");
    userHistory.add("cd dir1");
    mockFileSystem.getRoot().getDirectory("dir1").addDirectory("d1");
    userHistory.add("mkdir d1");
    userHistory.add("cd d1");
    mockFileSystem.getRoot().getDirectory("dir1").getDirectory("d1").addDirectory("d2");
    userHistory.add("mkdir d2");
    userHistory.add("cd /");
    mockFileSystem.getRoot().addFile("pickles", "file1");

    saveJShell.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found and/or file name not valid\n", Output.getLastPrinted());
  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("saveJShell", saveJShell.getCommandName());
  }


}
