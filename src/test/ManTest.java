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
 * JUnit test class for testing Man
 * 
 * @author Omar
 * @version Nov 30, 2020
 */
public class ManTest {

  Man man;
  ErrorHandler errorHandler;
  FileSystemShell system;
  String outFile;

  Command[] cmds = {new Exit(), new Concatenate(null, this.errorHandler), 
      new MakeDirectory(null, this.errorHandler),
      new ChangeDirectory(null, this.errorHandler), new Man(null, this.errorHandler),
      new Echo(null, this.errorHandler),new Push(null, this.errorHandler), 
      new Pop(null, this.errorHandler), new List(null, this.errorHandler),
      new PrintWorkingDirectory(null, this.errorHandler),
      new History(null, null, this.errorHandler), 
      new SaveJShell(null, this.errorHandler, null),
      new LoadJShell(null, this.errorHandler, null),
      new Remove(null, this.errorHandler), new Copy(null, this.errorHandler), 
      new Tree(null, this.errorHandler), new Search(null, this.errorHandler),
      new Curl(null, this.errorHandler)};

  @Before
  public void setUp() throws Exception {
    system = FileSystem.createFileSystemInstance();
    errorHandler = new ErrorHandler(system);
    man = new Man(system, errorHandler);
    outFile = "";
    
    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();
    
    System.out.println("======= TEST =======");
  }

  /**
   * Test when too many arguments are passed in
   */
  @Test
  public void testTooManyArgs() {
    String[] str = {"man", "ls", "cd"};
    
    man.run(str, cmds, outFile);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Test when too few arguments are passed in
   */
  @Test
  public void testTooFewArgs() {
    String[] str = {"man"};
    
    man.run(str, cmds, outFile);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Test a valid command
   */
  @Test
  public void testValidCommand1() {
    String[] str = {"man", "ls"};
    List ls = new List(null, null);
    
    man.run(str, cmds, outFile);
    assertEquals(ls.toString() + "\n", Output.getLastPrinted());
    assertFalse(ErrorHandler.checkIfErrorOccurred());
  }

  /**
   * Test an invalid command
   */
  @Test
  public void testInvalidCommand() {
    String[] str = {"man", "pop"};
    
    man.run(str, cmds, outFile);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("command not found\n", Output.getLastPrinted());  
  }
  
  /**
   * Test a valid command
   */
  @Test
  public void testValidCommand2() {
    String[] str = {"man", "popd"};
    Pop popd = new Pop(null, null);
    
    man.run(str, cmds, outFile);
    assertEquals(popd.toString() + "\n", Output.getLastPrinted());
    assertFalse(ErrorHandler.checkIfErrorOccurred());

  }

  /**
   * Test getting the command name
   */
  @Test
  public void testGetCommandName() {
    assertEquals("man", man.getCommandName());
  }
  
  @Test
  public void testRedirection() {
    String[] str = {"man", "ls"};
    man.run(str, cmds, "> outFile");
    assertEquals(system.getCurrentNode().getFile("outFile").getData(), "ls [path ...]\n"
        + "If no paths are given, prints the contents (file and directory) of\n" 
        + "the current directory.\n"
        + "Otherwise, for each path p\n"
        + "   If p specifies a file, prints p\n"
        + "   If p specifies a directory, prints the contents of the directory\n");
    assertFalse(ErrorHandler.checkIfErrorOccurred());
  }
}
