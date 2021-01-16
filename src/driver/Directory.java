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
/**
 * Directory object that can store other directories
 * and files within it
 * 
 * @author Kevin Fernandes
 * @version 20/11/10
 */
public class Directory {
  /**
   * List of directories in this directory
   */
  private ArrayList<Directory> directories;
  /**
   * Names of directories
   */
  private ArrayList<String> directoryNames;
  /**
   * List of Files in this directory
   */
  private ArrayList<File> files;
  /**
   * Names of files
   */
  private ArrayList<String> fileNames;
  /**
   * path to Directory
   */
  private String path;
  /**
   * name of Directory
   */
  private String name;
  /**
   * Absolute path to parent
   */
  private String parent;
  /** Error handler used to show errors */
  private ErrorHandler errorHandler;

  /**
   * Constructs a directory with the given name
   * This directory is a root
   * 
   * @param name name of directory to be made
   */
  public Directory(String name)
  {
    parent = null;
    path = name + "/";
    this.name = name;
    directories = new ArrayList<Directory>();
    directoryNames = new ArrayList<String>();
    files = new ArrayList<File>();
    fileNames = new ArrayList<String>();
    errorHandler = new ErrorHandler(null);
  }

  /**
   * Construct a directory within a given directory
   * 
   * @param parent path to parent directory
   * @param name name of new directory
   */
  public Directory(String parent, String name)
  {
    this.parent = parent;
    path = parent  + name + "/";
    this.name = name;
    directories = new ArrayList<Directory>();
    directoryNames = new ArrayList<String>();
    files = new ArrayList<File>();
    fileNames = new ArrayList<String>();
    errorHandler = new ErrorHandler(null);
  }

  /**
   * Creates an array of string of all names of
   * files within (immediately) this directory
   * 
   * @return String array of file names
   */
  public String[] getFileNames() {
    String[] names = new String[fileNames.size()];
    for (int i = 0; i < fileNames.size(); i++)
    {
      names[i] = fileNames.get(i);
    }
    return names;
  }

  /**
   * Creates an array of string of all names of
   * directories within (immediately) this directory
   * 
   * @return String array of directory names
   */
  public String[] getDirectoryNames() {
    String[] names = new String[directoryNames.size()];
    for (int i = 0; i < directoryNames.size(); i++)
    {
      names[i] = directoryNames.get(i);
    }
    return names;
  }

  /**
   * Get path to directory from root
   * 
   * @return path to directory
   */
  public String getPath() {
    return path;
  }

  /**
   * Add a given file to the directory
   * 
   * @param newFile File to be added
   * @param fileName name of file being added
   */
  public void addFile(File newFile,String fileName)
  {
    if (!alreadyExists(fileName))
    {
      getFiles().add(newFile);
      fileNames.add(fileName);
    }
  }

  /**
   * Add a a file to the directory
   * 
   * @param data the data of the file being added
   * @param fileName name of file being added
   */
  public void addFile(String data, String fileName)
  {
    if (!alreadyExists(fileName))
    {
      File newFile = new File(data, fileName);
      getFiles().add(newFile);
      fileNames.add(fileName);
    }
  }

  /**
   * Add a given directory to the directory
   * 
   * @param newDir Directory to be added
   * @param dirName name of directory to be added
   */
  public void addDirectory(Directory newDir,String dirName)
  {
    if (!alreadyExists(dirName))
    {
      getDirectories().add(newDir);
      directoryNames.add(dirName);
    }
  }

  /**
   * Add a new directory with a given name
   * 
   * @param dirName name of directory to be added
   */
  public void addDirectory(String dirName)
  {
    if (!alreadyExists(dirName))
    {
      Directory newDir = new Directory(this.path,dirName);
      getDirectories().add(newDir);
      directoryNames.add(dirName);
    }
  }

  /**
   * Checks if a file or directory of the same name
   * already exists within the directory
   * 
   * @param name name of file/directory to check
   * @return true iff a file or directory of the same name exists
   */
  private boolean alreadyExists(String name)
  {
    for (String f:fileNames)
    {
      if (f.equals(name))
      {
        errorHandler.fileAlreadyExists();

        return true;
      }
    }
    for (String d:directoryNames)
    {
      if (d.equals(name))
      {
        errorHandler.dirAlreadyExists();
        return true;
      }
    }
    return false;
  }
  /**
   * Get a specific file from directory based off file num
   * 
   * @param fileNum number of file to get
   * @return File at fileNum, or null if no file found
   */
  public File getFile(int fileNum)
  {
    if (fileNum < getFiles().size())
    {
      return getFiles().get(fileNum);
    }
    return null;
  }

  /**
   * Get a specific file from directory based off file name
   * 
   * @param fileName name of file to get
   * @return File with name fileName, or null if no file found
   */
  public File getFile(String fileName)
  {
    for (File f: getFiles())
    {
      if (f.getName().equals(fileName))
      {
        return f;
      }
    }
    return null;
  }

  /**
   * Get a specific directory from directory based off directory num
   * 
   * @param dirNum number of directory to get
   * @return Directory at dirNum, or null if no directory found
   */
  public Directory getDirectory(int dirNum)
  {
    if (dirNum < getDirectories().size())
    {
      return getDirectories().get(dirNum);
    }
    return null;
  }

  /**
   * Get a specific directory from directory based off directory name
   * 
   * @param dirName name of directory to get
   * @return Directory with name dirName, or null if no directory found
   */
  public Directory getDirectory(String dirName)
  {
    for (Directory d: getDirectories())
    {
      if (d.getName().equals(dirName))
      {
        return d;
      }
    }
    return null;
  }

  /**
   * Get name of directory
   * 
   * @return name of directory
   */
  public String getName() {
    return name;
  }

  public String getParent() {
    return parent;
  }

  public String toString() {
    return path;
  }
  /**
   * removes a directory from this directory
   * @param dirName directory to be removed
   */
  public void removeDirectory(String dirName) {
    for (int i = 0; i < directoryNames.size(); i++)
    {
      if (directoryNames.get(i).equals(dirName))
      {
        directoryNames.remove(i);
        getDirectories().remove(i);
        return;
      }
    }
    errorHandler.dirNotFound();
  }

  /**
   * Removes the file with the name fileName from this directory,
   * if it exists. If it does not exist, gives an error. 
   * @param fileName name of file to be removed
   */
  public void removeFile(String fileName) {
    for (int i = 0; i < fileNames.size(); i++) {
      if (fileNames.get(i).equals(fileName)) {
        fileNames.remove(i);
        files.remove(i);
        return;
      }
    }
    // if file can't be found, show an error
    errorHandler.fileNotFound();
  }

  /**
   * @return the directories
   */
  public ArrayList<Directory> getDirectories() {
    return directories;
  }

  /**
   * @return the files
   */
  public ArrayList<File> getFiles() {
    return files;
  }
}
