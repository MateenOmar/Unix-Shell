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

import java.util.Stack;
import driver.Directory;
import driver.File;
import driver.FileSystemShell;

/**
 * Mock file system for testing of ChangeDirectory
 * @author Jesse Yao
 */
public class MockFileSystemForCd implements FileSystemShell {

  private Directory root;
  private Directory currentNode;

  public MockFileSystemForCd() {
    this.root = new Directory("root");
    this.setCurrentNode(this.root);
  }

  @Override
  public Directory getRoot() {
    return this.root;
  }

  @Override
  public Directory getCurrentNode() {
    return this.currentNode;
  }

  @Override
  public void setCurrentNode(Directory currentNode) {
    this.currentNode = currentNode;
  }

  @Override
  public void createDirectory(String path) {}

  @Override
  public void createFile(String path, String data) {}

  @Override
  public void removeFile(String path) {}

  /**
   * Return a directory with name "dir1" if path is "/dir1".
   * Otherwise, return null.
   */
  @Override
  public Directory getDirectory(String path) {
    if (path.equals("/dir1")) {
      return new Directory("dir1");
    }
    return null;
  }

  @Override
  public Directory getDirectory(String[] paths) {
    return null;
  }

  @Override
  public File getFile(String path) {
    return null;
  }

  @Override
  public File getFile(String[] paths) {
    return null;
  }

  @Override
  public Stack<String> getDirectoryStack() {
    return null;
  }

  @Override
  public void setDirectoryStack(Stack<String> directoryStack) {}

  @Override
  public String[] getProcessedPath(String str) {
    return null;
  }

}
