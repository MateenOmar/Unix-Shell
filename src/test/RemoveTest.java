package test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import driver.ErrorHandler;
import driver.FileSystemShell;
import driver.Output;
import driver.Remove;

public class RemoveTest {

  FileSystemShell mockFileSystem;
  ErrorHandler errorHandler;
  Remove remove;

  
  @Before
  public void setUp() throws Exception {
    mockFileSystem = new MockFileSystem();
    errorHandler = new ErrorHandler(null);
    remove = new Remove(mockFileSystem, errorHandler); 

    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();

    System.out.println("======= TEST ======="); 
  }

  @Test
  public void testTooManyArgs() {
    String[] str = {"rm", "/dir1", "/dir2"};
    
    remove.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }
  
  @Test
  public void testTooFewArgs() {
    String[] str = {"rm"};
    
    remove.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }
  
  @Test
  public void testValid1() {
    
  }
  
  @Test
  public void testValid2() {
    mockFileSystem.createDirectory("dir1");
    mockFileSystem.createDirectory("dir2");
    String[] str = {"rm", "/dir1"};
    
    assertTrue(mockFileSystem.getRoot().getDirectory("dir1") != null);
    assertTrue(mockFileSystem.getRoot().getDirectory("dir2") != null);
    
    remove.run(str);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertTrue(mockFileSystem.getRoot().getDirectory("dir1") == null);
    
    ErrorHandler.resetErrorOccurred();
    
    String[] str2 = {"rm", "/dir2"};
    assertTrue(mockFileSystem.getRoot().getDirectory("dir1") == null);
    assertTrue(mockFileSystem.getRoot().getDirectory("dir2") != null);
    
    remove.run(str2);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertTrue(mockFileSystem.getRoot().getDirectory("dir1") == null);
  }
  
  @Test
  public void testInvalid1() {
    mockFileSystem.createDirectory("dir1");
    String[] str = {"rm", "/"};
    
    remove.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("Can't delete root\n", Output.getLastPrinted());
    assertTrue(mockFileSystem.getRoot() != null);
  }
  
  @Test
  public void testInvalid2() {
    mockFileSystem.createDirectory("dir1");
    String[] str = {"rm", "/dir2"};
    
    assertTrue(mockFileSystem.getRoot().getDirectory("dir2") == null);
    
    remove.run(str);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertTrue(mockFileSystem.getRoot().getDirectory("dir1") != null);
  }

  @Test
  public void testGetCommandName() {
    assertEquals("rm", remove.getCommandName());
  }
  
}
