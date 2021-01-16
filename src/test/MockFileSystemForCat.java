package test;

import java.util.Stack;
import driver.Directory;
import driver.File;
import driver.FileSystemShell;

/**
 * Mock file system for testing of Concatanete
 * @author Kazi Shafin Shafayet
 */
public class MockFileSystemForCat implements FileSystemShell {

  private Directory root;
  private Directory currentNode;
  
  public MockFileSystemForCat() {
    this.root = new Directory("root");
    this.setCurrentNode(this.root);
    File file1 = new File("This is file1.", "file1");
    File file2 = new File("This is file2.", "file2");
    File file3 = new File("This is file3.", "file3");
    root.addFile(file1, "file1");
    root.addFile(file2, "file2");
    root.addFile(file3, "file3");
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
  
  @Override
  public Directory getDirectory(String path) {
    Directory dir1 = new Directory("dir1");
    File file1 = new File("This is file1.", "file1");
    File file2 = new File("This is file2.", "file2");
    File file3 = new File("This is file3.", "file3");
    dir1.addFile(file1, "file1");
    dir1.addFile(file2, "file2");
    dir1.addFile(file3, "file3");
    return dir1;
  }

  @Override
  public Directory getDirectory(String[] paths) {
    return null;
  }

  @Override
  public File getFile(String path) {
    if(path.equals("/dir1/file1")) {
      File file1 = new File("This is file1.", "file1");
      return file1;
    }
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
