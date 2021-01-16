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
package driver;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Holds and Navigate a System of Directories
 * and Files
 * 
 * @author Kevin Fernandes
 * @version 20/11/18
 */
public class FileSystem implements FileSystemShell{

  private static FileSystemShell system = null;
  /**
   * root Directory of system
   */
  private Directory root;
  /**
   * Current Directory of System
   */
  private Directory currentNode;
  /**
   * Stack of directories stored by user
   */
  private Stack<String> directoryStack;

  private ErrorHandler errorHandler;

  /**
   * Construct a new File system with empty root
   */
  private FileSystem()
  {
    root = new Directory("");
    setCurrentNode(root);
    directoryStack = new Stack<String>();
    errorHandler = new ErrorHandler(this);
  }

  /**
   * Create instance of File system
   */
  public static FileSystemShell createFileSystemInstance() {
    if(system == null) {
      system = new FileSystem();
    }
    return system;
  }

  /**
   * Get root of File System
   * 
   * @return Directory at root of system
   */
  public Directory getRoot()
  {
    return root;
  }

  /**
   * Get current directory
   * 
   * @return current directory node
   */
  public Directory getCurrentNode() {
    return currentNode;
  }

  /**
   * Change current directory to given directory
   * 
   * @param currentNode directory to change to
   */
  public void setCurrentNode(Directory currentNode) 
  {
    this.currentNode = currentNode;
  }

  /**
   * Create and return an empty directory at the given path, unless the
   * directory name contains illegal characters, or the path is invalid,
   * or a directory or file with the same name already exists at the
   * same location.
   * @param path path to the directory to be created
   */
  public void createDirectory(String path) {
    Directory parent = getParentIfValid(path, false);
    if (parent != null) {
      String[] splitPath = getProcessedPath(path);
      parent.addDirectory(splitPath[splitPath.length - 1]);
    }
  }

  /**
   * Create a file with the given data at the given path, unless the
   * file name contains illegal characters, or the path is invalid, or
   * a directory or file with the same name already exists at the
   * same location. 
   * @param path path to the file to be created
   * @param data data to store in the new file
   */
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

  /**
   * Given path, a path referring to a possible directory or file to be
   * created, return the parent directory of the directory/file if
   * - the directory/file name is valid, and
   * - the path to the parent directory of the directory/file exists
   * Otherwise, give an error and return null.
   * @param path a path referring to a directory or file to be possibly created
   * @param fileMode true if path is supposed to refer to a file,
   *                 false if supposed to refer to a directory
   * @return the parent directory to the directory/file referred to in path,
   *         or null if path does not fulfill the criteria above
   */
  private Directory getParentIfValid(String path, boolean fileMode) {
    String[] splitPath = getProcessedPath(path);
    String name = splitPath[splitPath.length - 1];
    if (!containsLegalCharacters(name) || name.equals("") ||
        name.equals(".") || name.equals("..")) {
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

  /**
   * Checks for illegal characters within str
   * 
   * @param str string to be checked
   * @return True iff str contains only legal characters
   */
  static boolean containsLegalCharacters(String str) {

    //Allowed characters
    Pattern p = Pattern.compile("[a-zA-Z0-9-+:;,=_]*");
    Matcher m = p.matcher(str);
    boolean b = m.matches();

    //True if there is not any non-allowed character in dir name
    return b;
  }

  /**
   * Removes the file specified by the given path from the file system,
   * if the file exists.
   * @param path the path to the file to be removed
   */
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

  /**
   * Get directory with given path
   * path can be absolute of relative to current directory
   * 
   * @param path path of directory to get
   * @return Directory at given path, or null if dir not found
   */
  public Directory getDirectory(String path)
  {
    String[] paths = getProcessedPath(path);
    return getDirectory(paths);
  }

  /**
   * Get directory with given processed path
   * 
   * @param paths processed path of directory to get
   * @return Directory at given path, or null if dir not found
   */
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
    if (temp == null) {
      return null;
    }
    return temp;
  }

  /**
   * Get directory with given processed path
   * 
   * @param paths processed path of directory to get
   * @param type 0 for relative path, 1 for absolute path
   * @return Directory at given path, or null if dir not found
   */
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

  /**
   * Get file with given path
   * 
   * @param paths path of file to get
   * @return File at given path, or null if file not found
   */
  public File getFile(String path)
  {
    if (path.endsWith("/")) {
      return null;
    }
    String[] paths = getProcessedPath(path);
    return getFile(paths);
  }

  /**
   * Get file with given processed path
   * 
   * @param paths processed path of file to get
   * @return File at given path, or null if file not found
   */
  public File getFile(String[] paths) 
  {
    String fileName = paths[paths.length - 1];
    paths[paths.length-1] = ".";
    Directory temp = getDirectory(paths);
    if (temp == null) {
      return null;
    }
    return temp.getFile(fileName); //if null, throw File not found 
  }

  /**
   * @return the directoryStack
   */
  public Stack<String> getDirectoryStack(){
    return directoryStack;
  }

  /**
   * 
   * @param directoryStack is a stack of directories 
   */
  public void setDirectoryStack(Stack<String> directoryStack) {
    this.directoryStack = directoryStack;
  }

  /**
   * Process a path into list of directories and files
   * 
   * @param str path to be processed
   * @return Processed version of path
   */
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
