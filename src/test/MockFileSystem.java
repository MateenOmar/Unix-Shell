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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;
import driver.Directory;
import driver.File;

public class MockFileSystem implements driver.FileSystemShell{

  private Directory root;

  private Directory currentNode;

  private Stack<String> directoryStack;

  public MockFileSystem() {
    root = new Directory("");
    setCurrentNode(root);
    directoryStack = new Stack<String>();
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
  public void createDirectory(String path) {
    root.addDirectory(path);
  }

  @Override
  public void createFile(String path, String data) {
    // TODO Auto-generated method stub

  }

  @Override
  public void removeFile(String path) {
    // TODO method stub
  }

  @Override
  public void setCurrentNode(Directory currentNode) {
    this.currentNode = currentNode;
  }

  public Directory getDirectory(String path)
  {
    String[] paths = getProcessedPath(path);
    return getDirectory(paths);
  }

  @Override
  public Directory getDirectory(String[] paths)
  {
    if (paths.length == 1 && paths[0].equals("")) return root;
    Directory temp;
    if (paths.length > 1 && paths[0].equals("")) {
      temp = getDirectory(Arrays.copyOfRange(paths, 1, paths.length), 1);
    }
    else {
      temp = getDirectory(paths, 0);
    }  
    if (temp == null) return null;
    return temp;
  }

  private Directory getDirectory(String[] path, int type)
  {
    Directory temp = currentNode;
    String parent;
    String[] parentPath;
    if (type == 1) temp = root;
    boolean found;
    for (String p: path)
    {
      if (p.equals("."))
      {
        continue;
      }
      if (p.equals(".."))
      {
        parent = temp.getParent();
        if (parent == null) 
        {
          return null;//throw Dir not found
        }
        parentPath = getProcessedPath(parent);
        temp = getDirectory(parentPath);
        continue;
      }
      found = false;
      for (String d: temp.getDirectoryNames())
      {
        if (d.equals(p))
        {
          temp = temp.getDirectory(d);
          found = true;
          break;
        }
      }
      if (!found)
      {
        return null;
      }
    }
    return temp;
  }

  @Override
  public File getFile(String path) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public File getFile(String[] paths) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Stack<String> getDirectoryStack() {
    return directoryStack;
  }

  @Override
  public void setDirectoryStack(Stack<String> directoryStack) {
    this.directoryStack = directoryStack; 
  }

  @Override
  public String[] getProcessedPath(String str)
  {
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
