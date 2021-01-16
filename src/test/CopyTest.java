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

public class CopyTest {
  
  Copy cp;
  FileSystemShell system;
  ErrorHandler errorHandler;
  
  @Before
  public void setup() {
    system = new MockFileSystemForCopy();
    errorHandler = new ErrorHandler(system);
    ErrorHandler.resetErrorOccurred();
    Output.resetLastPrinted();
    cp = new Copy(system, errorHandler);
    
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
    
    system.createFile("/file1", "data in file1");
    
    System.out.println("======= TEST =======");
  }
  
  /**
   * Check behavior for too few arguments (< 3)
   * Expected behavior: give appropriate error
   */
  @Test
  public void testTooFewArgs() {
    String[] args = {"cp", "/dir1"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("not enough arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Check behavior for too many arguments (> 3)
   * Expected behavior: give appropriate error
   */
  @Test
  public void testTooManyArgs() {
    String[] args = {"cp", "/dir1", "/dir2", "/dir3"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("too many arguments\n", Output.getLastPrinted());
  }
  
  /**
   * Check behavior of copying from an invalid path
   * Expected behavior: give appropriate error
   */
  @Test
  public void testInvalidSource() {
    String[] args = {"cp", "/dir3", "/dir4"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory or file not found\n", Output.getLastPrinted());
    assertNull(system.getDirectory("/dir4"));
  }
  
  /**
   * Check behavior of copying an existing file to an existing file
   * Expected behavior: copy file contents to the specified location,
   * overwriting anything already there.
   */
  @Test
  public void testFileToFile() {
    String[] args = {"cp", "/dir1/hellofile", "/file1"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNotNull(system.getFile("/file1"));
    assertTrue(system.getFile("/file1").getData() == "hello hello");
  }
  
  /**
   * Check behavior of copying from an existing file to an existing directory
   * Expected behavior: copy file into the directory
   */
  @Test
  public void testFileToDir() {
    String[] args = {"cp", "/dir1/dir1/foofile", "/dir1/"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
    assertNotNull(system.getFile("/dir1/foofile"));
    assertTrue(system.getFile("/dir1/foofile").getData() == "foo data");
  }
  
  /**
   * Check behavior of copying from an existing file to a new file
   * Expected behavior: new file is created with the given name and
   * the same contents as the original
   */
  @Test
  public void testFileToNewFile() {
    String[] args = {"cp", "/dir1/hellofile", "/dir2/hellofile2"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNotNull(system.getFile("/dir2/hellofile2"));
    assertTrue(system.getFile("/dir2/hellofile2").getData() 
        == "hello hello");
  }
  
  /**
   * Check behavior of copying from an existing file to a new file
   * with an invalid name
   * Expected behavior: return appropriate error
   */
  @Test
  public void testFileToNewFileWithInvalidName() {
    String[] args = {"cp", "/dir1/hellofile", "/dir2/!invalid!"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("file name not valid\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNull(system.getFile("/dir2/!invalid!"));
  }
  
  /**
   * Check behavior of copying from an existing file to a new folder
   * (path that doesn't exist yet but unambiguously leads to a directory
   * since it has a / at the end)
   * Expected behavior: return appropriate error
   */
  @Test
  public void testFileToNewDir() {
    String[] args = {"cp", "/dir1/hellofile", "/dir3/"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNull(system.getFile("/dir3"));
    assertNull(system.getDirectory("/dir3"));
  }
  
  /**
   * Check behavior of copying from an existing file to a new folder with the
   * same name as an existing file
   * Expected behavior: return appropriate error
   */
  @Test
  public void testFileToNewDir2() {
    String[] args = {"cp", "/dir1/hellofile", "/file1/"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNotNull(system.getFile("/file1"));
    assertNull(system.getDirectory("/file1"));
  }
  
  /**
   * Check behavior of copying a file to the same file
   * Expected behavior: effectively nothing
   */
  @Test
  public void testFileToItself() {
    String[] args = {"cp", "/dir1/hellofile", "/dir1/hellofile"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertTrue(system.getFile("/dir1/hellofile").getData() 
        == "hello hello");
  }
  
  /**
   * Check behavior of copying a file to the same file
   * (via copying to its directory)
   * Expected behavior: effectively nothing
   */
  @Test
  public void testFileToItself2() {
    String[] args = {"cp", "/dir1/hellofile", "/dir1"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertTrue(system.getFile("/dir1/hellofile").getData() 
        == "hello hello");
  }
  
  /**
   * Check behavior of copying a file to an invalid path
   * Expected behavior: return appropriate error
   */
  @Test
  public void testFileToInvalidPath() {
    String[] args = {"cp", "/dir1/hellofile", "/dir3/dir2"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
    assertNotNull(system.getFile("/dir1/hellofile"));
    assertNull(system.getDirectory("/dir3"));
  }
  
  /**
   * Check behavior of copying a directory to a file
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToFile() {
    String[] args = {"cp", "/zzz", "/dir2/zzz"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to file\n", 
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/zzz"));
    assertNotNull(system.getFile("/dir2/zzz"));
    assertNull(system.getDirectory("/dir2/zzz"));
  }
  
  /**
   * Check behavior of copying from a directory to an existing directory
   * (not a child of the source)
   * Expected behavior: entire source directory, as a unit, is recursively
   * copied into the destination directory
   * - files of same name are overwritten
   * - directories of same name are merged with the copied directories
   */
  @Test
  public void testDirToDir() {
    String[] args = {"cp", "/dir1/", "/dir2"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    
    assertNotNull(system.getDirectory("/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
    assertNotNull(system.getFile("/dir1/hellofile"));
    
    assertNotNull(system.getDirectory("/dir2/dir1"));
    assertNotNull(system.getFile("/dir2/dir1/hey"));
    assertEquals("hey hey", system.getFile("/dir2/dir1/hey").getData());
    assertNotNull(system.getDirectory("/dir2/dir1/dir1"));
    assertNotNull(system.getDirectory("/dir2/dir1/dir1/a"));
    assertNotNull(system.getDirectory("/dir2/dir1/dir1/wow"));
    assertNotNull(system.getFile("/dir2/dir1/dir1/foofile"));
    assertEquals("foo data", 
        system.getFile("/dir2/dir1/dir1/foofile").getData());
    assertNotNull(system.getFile("/dir2/dir1/hellofile"));
    assertEquals("hello hello",
        system.getFile("/dir2/dir1/hellofile").getData());
  }
  
  /**
   * Check behavior of copying a directory to a location where a file with the
   * same name already exists
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToDirWithExistingFileName() {
    String[] args = {"cp", "/zzz", "/dir2/zzz/"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("file already exists\n", Output.getLastPrinted());
    assertNotNull(system.getDirectory("/zzz"));
    assertNotNull(system.getFile("/dir2/zzz"));
    assertNull(system.getDirectory("/dir2/zzz"));
  }
  
  /**
   * Check behavior of copying a directory to a location where a file with the
   * same name already exists (via copying to its parent directory)
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToDirWithExistingFileName2() {
    String[] args = {"cp", "/zzz", "/dir2"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("file already exists\n", Output.getLastPrinted());
    assertNotNull(system.getDirectory("/zzz"));
    assertNotNull(system.getFile("/dir2/zzz"));
    assertNull(system.getDirectory("/dir2/zzz"));
  }
  
  /**
   * Check behavior of copying a directory recursively, resulting in situations where
   * the program tries to create a directory with the same name as an existing file
   * in the same location, or the program tries to create a file with the same name
   * as an existing directory in the same location.
   * Expected behavior: stop the creation of conflicting files/directories and give an
   * error for each of those, but copy everything else.
   */
  @Test
  public void testDirToDirWithConflicts() {
    String[] args = {"cp", "/zzz/dir1", "/dir1"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    //assertEquals(Output.getLastPrinted(), "directory already exists\n");
    assertNotNull(system.getDirectory("/zzz/dir1"));
    assertNotNull(system.getDirectory("/zzz/dir1/foofile"));
    assertNotNull(system.getFile("/zzz/dir1/a"));
    assertNotNull(system.getFile("/zzz/dir1/b"));
    
    assertNotNull(system.getFile("/dir1/dir1/b"));
    assertEquals("bbb", system.getFile("/dir1/dir1/b").getData());
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNull(system.getFile("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
    assertEquals("foo data", system.getFile("/dir1/dir1/foofile").getData());
    assertNull(system.getDirectory("/dir1/dir1/foofile"));
  }
  
  /**
   * Check behavior of copying a directory to a child of itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToChild() {
    String[] args = {"cp", "/dir2", "/dir2/dir1"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n",
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/dir2"));
    assertNull(system.getDirectory("/dir2/dir1/dir2"));
  }
  
  /**
   * Check behavior of copying the root to a child of itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testRootToChild() {
    System.out.println("root to child:");
    String[] args = {"cp", "/", "/dir1"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n",
        Output.getLastPrinted());
  }
  
  /**
   * Check behavior of copying a directory to itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testDirToItself() {
    String[] args = {"cp", "/zzz", "/zzz"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n", 
        Output.getLastPrinted());
  }
  
  /**
   * Check behavior of copying the root to itself
   * Expected behavior: give appropriate error
   */
  @Test
  public void testRootToItself() {
    System.out.println("root to itself:");
    String[] args = {"cp", "/", "/"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n", 
        Output.getLastPrinted());
  }
  
  /**
   * Check behavior of copying a directory to a new directory
   * Expected behavior: create new directory at the given path
   * with the same contents as the original
   */
  @Test
  public void testDirToNewDir() {
    String[] args = {"cp", "/dir1/dir1/", "/yyy/"};
    cp.run(args);
    assertFalse(ErrorHandler.checkIfErrorOccurred());
    assertNotNull(system.getDirectory("/dir1/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
    
    assertNotNull(system.getDirectory("/yyy"));
    assertNotNull(system.getDirectory("/yyy/a"));
    assertNotNull(system.getFile("/yyy/foofile"));
    assertEquals("foo data", system.getFile("/yyy/foofile").getData());
  }
  
  /**
   * Check behavior of copying a directory to a new directory with an
   * invalid name
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToNewDirInvalidName() {
    String[] args = {"cp", "/dir1/dir1/", "/!invalid!/"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory name '!invalid!' not valid\n",
        Output.getLastPrinted());
    assertNotNull(system.getDirectory("/dir1/dir1"));
    assertNotNull(system.getDirectory("/dir1/dir1/a"));
    assertNotNull(system.getFile("/dir1/dir1/foofile"));
  }
  
  /**
   * Check behavior of copying a directory to a new child of itself
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToNewChild() {
    String[] args = {"cp", "/", "/yyy"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to child of itself\n",
        Output.getLastPrinted());
    assertNull(system.getDirectory("/yyy"));
  }
  
  /**
   * Check behavior of copying a directory to an invalid path
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToInvalidPath() {
    String[] args = {"cp", "/dir1", "/yyy/hi"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("directory not found\n", Output.getLastPrinted());
  }
  
  /**
   * Check behavior of copying a directory to its direct parent
   * Expected behavior: return appropriate error
   */
  @Test
  public void testDirToParent() {
    String[] args = {"cp", "/dir1/dir1", "/dir1"};
    cp.run(args);
    assertTrue(ErrorHandler.checkIfErrorOccurred());
    assertEquals("cannot move/copy directory to same directory\n",
        Output.getLastPrinted());
  }

}
