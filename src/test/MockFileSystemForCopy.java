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

import java.util.*;
import driver.*;

public class MockFileSystemForCopy implements FileSystemShell {
  
  private Directory root;
  private Directory currentNode;
  private ErrorHandler errorHandler;

  public MockFileSystemForCopy() {
    root = new Directory("");
    currentNode = root;
    errorHandler = new ErrorHandler(this);
  }

  @Override
  public Directory getRoot() {
    return root;
  }

  @Override
  public Directory getCurrentNode() {
    return currentNode;
  }

  @Override
  public void setCurrentNode(Directory currentNode) {
    this.currentNode = currentNode;
  }

  @Override
  public void createDirectory(String path) {
    Directory parent = getParentIfValid(path, false);
    if (parent != null) {
      String[] splitPath = getProcessedPath(path);
      parent.addDirectory(splitPath[splitPath.length - 1]);
    }
  }
  
  private Directory getParentIfValid(String path, boolean fileMode) {
    String[] splitPath = getProcessedPath(path);
    String name = splitPath[splitPath.length - 1];
    if (name.equals("!invalid!")) {
      if (fileMode) {
        errorHandler.invalidFileName();
      } else {
        errorHandler.invalidDirectoryName(name);
      }
      return null;
    }
    splitPath[splitPath.length - 1] = ".";
    Directory dirPlace = getDirectory(splitPath);
    if (dirPlace == null) {
      errorHandler.dirNotFound();
      return null;
    }
    return dirPlace;
  }

  @Override
  public void createFile(String path, String data) {
    if (path.endsWith("/")) {
      errorHandler.notADirectory();
      return;
    }
    Directory parent = getParentIfValid(path, true);
    if (parent != null) {
      String[] splitPath = getProcessedPath(path);
      parent.addFile(data, splitPath[splitPath.length - 1]);
    }
  }

  @Override
  public void removeFile(String path) {
    String[] splitPath = getProcessedPath(path);
    String fileName = splitPath[splitPath.length - 1];
    splitPath[splitPath.length - 1] = ".";
    Directory parent = getDirectory(splitPath);
    if (parent == null) {
      errorHandler.fileNotFound();
      return;
    }
    parent.removeFile(fileName); // note: gives an error if file is not found
  }

  @Override
  public Directory getDirectory(String path) {
    String[] splitPath = getProcessedPath(path);
    return getDirectory(splitPath);
  }

  @Override
  public Directory getDirectory(String[] paths) {
    // only supports absolute paths, with .
    Directory temp = root;
    for (int i = 1; i < paths.length; i++) {
      String p = paths[i];
      if (p.equals(".")) {
        continue;
      }
      Directory child = temp.getDirectory(p);
      if (child == null) {
        return null;
      }
      temp = child;
    }
    return temp;
  }

  @Override
  public File getFile(String path) {
    if (path.endsWith("/")) {
      return null;
    }
    String[] paths = getProcessedPath(path);
    return getFile(paths);
  }

  @Override
  public File getFile(String[] paths) {
    String fileName = paths[paths.length - 1];
    paths[paths.length-1] = ".";
    Directory temp = getDirectory(paths);
    if (temp == null) {
      return null;
    }
    return temp.getFile(fileName);
  }

  @Override
  public Stack<String> getDirectoryStack() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setDirectoryStack(Stack<String> directoryStack) {
    // TODO Auto-generated method stub

  }

  @Override
  public String[] getProcessedPath(String str) {
    String[] splitPath = str.split("/",-1);
    ArrayList<String> cleanPath =
        new ArrayList<String>(Arrays.asList(splitPath));
    // remove empty string at the end (path ends with a /)
    int last = cleanPath.size() - 1;
    if (last > 0 && cleanPath.get(last).equals("")) {
      cleanPath.remove(last);
    }
    return cleanPath.toArray(new String[cleanPath.size()]);
  }

}
