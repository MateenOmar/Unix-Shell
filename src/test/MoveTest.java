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

public class MoveTest {
  
  Move mv;
  FileSystemShell system;
  ErrorHandler errorHandler;
  
  @Before
  public void setup() {
    system = new MockFileSystemForCopy();
    errorHandler = new ErrorHandler(system);
    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();
    mv = new Move(system, errorHandler);
    
    // create initial file hierarchy
    system.createDirectory("/dir1");
    system.createDirectory("/dir1/dir1");
    system.createDirectory("/dir1/dir1/a");
    system.createFile("/dir1/dir1/foofile", "foo data");
    system.createFile("/dir1/hellofile", "hello hello");
    
    system.createDirectory("/dir2");
    system.createDirectory("/dir2/dir1");
    system.createFile("/dir2/dir1/hey", "hey hey");
    system.createDirectory("/dir2/dir1/dir1");
    system.createDirectory("/dir2/dir1/dir1/wow");
    system.createFile("/dir2/dir1/dir1/foofile", "bad foo data");
    system.createFile("/dir2/zzz", "data in zzz");
    
    system.createDirectory("/zzz");
    system.createDirectory("/zzz/dir1");
    system.createDirectory("/zzz/dir1/foofile");
    system.createFile("/zzz/dir1/a", "aaa");
    system.createFile("/zzz/dir1/b", "bbb");
    system.createFile("/zzz/hi", "hi hi");
    
    system.createDirectory("/a");
    system.createFile("/a/fi", "fifi");
    
    system.createDirectory("/b");
    system.createDirectory("/b/b");
    
    system.createFile("/file1", "data in file1");
    
    System.out.println("======= TEST =======");
  }
  
  /**
   * Check behavior for too few arguments (< 3)
   * Expected behavior: give appropriate error
   */
  @Test
  public void testTooFewArgs() {
    String[] args = {"mv", "/dir1"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Check behavior for too many arguments (> 3)
   * Expected behavior: give appropriate error
   */
  @Test
  public void testTooManyArgs() {
    String[] args = {"mv", "/dir1", "/dir2", "/dir3"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Check behavior of moving from an invalid path
   * Expected behavior: give appropriate error
   */
  @Test
  public void testInvalidSource() {
    String[] args = {"mv", "/dir3", "/dir4"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory or file not found\n", Output.getLastPrinted());
    assertNull(system.getDirectory("/dir4"));
  }
  
  /**
   * Check behavior of moving an existing file to an existing file location
   * Expected behavior: overwrites destination file
   */
  @Test
  public void testFileToFile() {
    String[] args = {"mv", "/dir1/hellofile", "/file1"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNull(system.getFile("/dir1/hellofile"));
    assertNotNull(system.getFile("/file1"));
    assertTrue(system.getFile("/file1").getData() == "hello hello");
  }
  
  /**
   * Check behavior of moving an existing file to an existing directory
   * Expected behavior: move file into the directory
   */
  @Test
  public void testFileToDir() {
    String[] args = {"mv", "/dir1/dir1/foofile", "/dir1/"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNull(system.getFile("/dir1/dir1/foofile"));
    assertNotNull(system.getFile("/dir1/foofile"));
    assertTrue(system.getFile("/dir1/foofile").getData() == "foo data");
  }
  
  /**
   * Check behavior of moving an existing file to a new file location
   * Expected behavior: new file is created with the given name and
   * the same contents as the original
   */
  @Test
  public void testFileToNewFile() {
    String[] args = {"mv", "/dir1/hellofile", "/dir2/hellofile2"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNull(system.getFile("/dir1/hellofile"));
    assertNotNull(system.getFile("/dir2/hellofile2"));
    assertTrue(system.getFile("/dir2/hellofile2").getData() 
        == "hello hello");
  }
  
  /**
   * Check behavior of moving an existing file to a new file location
   * with an invalid name
   * Expected behavior: give appropriate error
   */
  @Test
  public void testFileToNewFileWithInvalidName() {
    String[] args = {"mv", "/dir1/hellofile", "/dir2/!invalid!"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("file name not valid\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNull(system.getFile("/dir2/!invalid!"));
  }
  
  /**
   * Check behavior of moving an existing file to a new folder
   * (path that doesn't exist yet but unambiguously leads to a directory
   * since it has a / at the end)
   * Expected behavior: return appropriate error
   */
  @Test
  public void testFileToNewDir() {
    String[] args = {"mv", "/dir1/hellofile", "/dir3/"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNull(system.getFile("/dir3"));
    assertNull(system.getDirectory("/dir3"));
  }
  
  /**
   * Check behavior of moving an existing file to a new folder with the
   * same name as an existing file
   * Expected behavior: give appropriate error
   */
  @Test
  public void testFileToNewDir2() {
    String[] args = {"mv", "/dir1/hellofile", "/file1/"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNotNull(system.getFile("/file1"));
    assertNull(system.getDirectory("/file1"));
  }
  
  /**
   * Check behavior of move a file to its own location
   * Expected behavior: effectively nothing
   */
  @Test
  public void testFileToItself() {
    String[] args = {"mv", "/dir1/hellofile", "/dir1/hellofile"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertTrue(system.getFile("/dir1/hellofile").getData() 
        == "hello hello");
  }
  
  /**
   * Check behavior of moving a file to its own location
   * (via copying to its directory)
   * Expected behavior: effectively nothing
   */
  @Test
  public void testFileToItself2() {
    String[] args = {"mv", "/dir1/hellofile", "/dir1"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertTrue(system.getFile("/dir1/hellofile").getData() 
        == "hello hello");
  }
  
  /**
   * Check behavior of moving a file to an invalid path
   * Expected behavior: give appropriate error
   */
  @Test
  public void testFileToInvalidPath() {
    String[] args = {"mv", "/dir1/hellofile", "/dir3/dir2"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNull(system.getDirectory("/dir3"));
  }
  
  /**
   * Check behavior of moving a directory to a file
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToFile() {
    String[] args = {"mv", "/zzz", "/dir2/zzz"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to file\n", 
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/zzz"));
    assertNotNull(system.getFile("/dir2/zzz"));
    assertNull(system.getDirectory("/dir2/zzz"));
  }
  
  /**
   * Check behavior of moving a directory to an existing directory which has
   * a non-empty subdirectory of the same name as the source
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToDirNonEmpty() {
    String[] args = {"mv", "/dir1/", "/dir2"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move directory contents into non-empty location\n", 
        Output.getLastPrinted());
    
    assertNotNull(system.getDirectory("/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
    assertNotNull(system.getFile("/dir1/hellofile"));
    
    assertNull(system.getDirectory("/dir2/dir1/dir1/a"));
    assertEquals("bad foo data", 
        system.getFile("/dir2/dir1/dir1/foofile").getData());
    assertNull(system.getFile("/dir2/dir1/hellofile"));
  }
  
  /**
   * Check behavior of moving a directory to an existing directory which has
   * an empty subdirectory of the same name as the source
   * Expected behavior: move directory to that location, replacing the empty
   * directory
   */
  @Test
  public void testDirToDirEmpty() {
    String[] args = {"mv", "/a", "/dir1/dir1"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    
    assertNull(system.getDirectory("/a"));
    
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/a/fi"));
    assertEquals("fifi", 
        system.getFile("/dir1/dir1/a/fi").getData());
  }
  
  /**
   * Check behavior of moving a directory to an existing directory which has
   * no subdirectory with the same name as the source
   * Expected behavior: move directory to that location
   */
  @Test
  public void testDirToDir() {
    String[] args = {"mv", "/a", "/dir1/"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    
    assertNull(system.getDirectory("/a"));
    
    assertNotNull(system.getDirectory("/dir1/a"));
    assertNotNull(system.getFile("/dir1/a/fi"));
    assertEquals("fifi", 
        system.getFile("/dir1/a/fi").getData());
  }
  
  /**
   * Check behavior of moving a directory to a location where a file with the
   * same name already exists
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToDirWithExistingFileName() {
    String[] args = {"mv", "/zzz", "/dir2/zzz/"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("file already exists\n", Output.getLastPrinted());
    assertNotNull(system.getDirectory("/zzz"));
    assertNotNull(system.getFile("/dir2/zzz"));
    assertNull(system.getDirectory("/dir2/zzz"));
  }
  
  /**
   * Check behavior of moving a directory to a location where a file with the
   * same name already exists (via copying to its parent directory)
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToDirWithExistingFileName2() {
    String[] args = {"mv", "/zzz", "/dir2"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("file already exists\n", Output.getLastPrinted());
    assertNotNull(system.getDirectory("/zzz"));
    assertNotNull(system.getFile("/dir2/zzz"));
    assertNull(system.getDirectory("/dir2/zzz"));
  }
  
  /**
   * Check behavior of moving a directory to a child of itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToChild() {
    String[] args = {"mv", "/dir2", "/dir2/dir1"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n",
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/dir2"));
    assertNull(system.getDirectory("/dir2/dir1/dir2"));
  }
  
  /**
   * Check behavior of moving a directory to itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToItself() {
    String[] args = {"mv", "/zzz", "/zzz"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n", 
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/dir2"));
  }
  
  /**
   * Check behavior of moving the root to itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testRootToItself() {
    System.out.println("root to itself:");
    String[] args = {"mv", "/", "/"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    String err = "cannot move current working directory/"
        + "parent of current working directory\n";
    assertEquals(err, Output.getLastPrinted());
  }
  
  /**
   * Check behavior of moving a directory to a new directory
   * Expected behavior: create new directory at the given path
   * with the same contents as the original
   */
  @Test
  public void testDirToNewDir() {
    String[] args = {"mv", "/dir1/dir1/", "/yyy/"};
    mv.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNull(system.getDirectory("/dir1/dir1"));
    
    assertNotNull(system.getDirectory("/yyy"));
    assertNotNull(system.getDirectory("/yyy/a"));
    assertNotNull(system.getFile("/yyy/foofile"));
    assertEquals("foo data", system.getFile("/yyy/foofile").getData());
  }
  
  /**
   * Check behavior of moving a directory to a new directory with an
   * invalid name
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToNewDirInvalidName() {
    String[] args = {"mv", "/dir1/dir1/", "/!invalid!/"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory name '!invalid!' not valid\n",
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/dir1/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
    assertNull(system.getDirectory("/!invalid!"));
  }
  
  /**
   * Check behavior of moving a directory to a new child of itself
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToNewChild() {
    String[] args = {"mv", "/zzz", "/zzz/dir1/help"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n",
        Output.getLastPrinted());
    assertNull(system.getDirectory("/zzz/dir1/help"));
  }
  
  /**
   * Check behavior of moving a directory to an invalid path
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToInvalidPath() {
    String[] args = {"mv", "/dir1", "/yyy/hi"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getDirectory("/dir1"));
  }
  
  /**
   * Check behavior of moving a directory to its direct parent
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToParent() {
    String[] args = {"mv", "/b/b", "/b"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to same directory\n",
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/b/b"));
  }
  
  /**
   * Check behavior of moving the current working directory
   * Expected behavior: return appropriate error
   */
  @Test
  public void testWD() {
    system.setCurrentNode(system.getDirectory("/zzz"));
    String[] args = {"mv", "/zzz", "/dir1"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    String err = "cannot move current working directory/"
        + "parent of current working directory\n";
    assertEquals(err, Output.getLastPrinted());
    assertNull(system.getDirectory("/dir1/zzz"));
  }
  
  /**
   * Check behavior of moving a parent of the current working directory
   * Expected behavior: return appropriate error
   */
  @Test
  public void testWDParent() {
    system.setCurrentNode(system.getDirectory("/zzz/dir1"));
    String[] args = {"mv", "/zzz", "/dir1"};
    mv.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    String err = "cannot move current working directory/"
        + "parent of current working directory\n";
    assertEquals(err, Output.getLastPrinted());
    assertNull(system.getDirectory("/dir1/zzz"));
  }

}
