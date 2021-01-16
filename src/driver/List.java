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

import java.util.Stack;

/**
 * Lists directories and files from given directories
 * 
 * @author Kevin Fernandes
 * @version 20/11/10
 */
public class List extends Command{
  /**
   * File system to read directories from
   */
  private FileSystemShell sys;
  /**
   * Error Handler to send errors too 
   */
  private ErrorHandler err;
  /**
   * Name of command
   */
  private String commandName;

  /**
   * Construct List command object
   * 
   * @param sys FileSystem to interact with
   * @param err Error Handler to use
   */
  public List(FileSystemShell sys, ErrorHandler err)
  {
    this.sys = sys;
    this.err= err;
    this.commandName = "ls";
  }

  /**
   * Get usable name of command
   * 
   * @return name of command
   */
  public String getCommandName() {
    return commandName;
  }

  /**
   * ls [PATH ...]
   * If no paths are given, prints the contents (file or directory) of the current
   * directory, with a new line following each of the content (file or directory).
   * Otherwise, for each path p, the order listed:
   *    •If p specifies a file, print p
   *    •If p specifies a directory, print p, a colon, then the contents of that
   *     directory, then an extra new line.  
   *    •If p does not exist, throws an error.  
   * 
   * @param str user given call to ls command
   * @param outFile is the substring of the input after first >
   */
  public void run(String[] str, String outFile)
  {
    String[] directories, files;
    Directory dir;
    Stack<String> toCheck = new Stack<String>();
    boolean recursive = false;
    if (str.length > 1 && str[1].equals("-R"))
    {
      recursive = true;
      for (int i = 2; i < str.length; i++) toCheck.push(str[i]);
    }
    else for (int i = 1; i < str.length; i++) toCheck.push(str[i]);
    String string = "";
    if (str.length == 1 || (str.length == 2 && recursive))
    {
      dir = sys.getCurrentNode();
      files = dir.getFileNames();
      directories = dir.getDirectoryNames();
      for (String f: files) string += f+"\n";
      for (String d: directories) string += d+"\n";
      if (recursive) {
        for (Directory d: dir.getDirectories()) toCheck.push(d.getPath());
      }
    }
    handleMultiplePaths(toCheck, outFile, recursive, string);
  }

  /**
   * Sends outputs for multiple paths
   * 
   * @param toCheck stack of paths to check
   * @param outFile output destination
   * @param recursive -R is enabled
   * @param string output string
   */
  private void handleMultiplePaths(Stack<String> toCheck, String outFile,
      boolean recursive, String string)
  {
    Output output = new Output();
    Concatenate cat = new Concatenate(sys, err);
    String[] temp, directories, files;
    Directory dir;
    String dirName;
    while (toCheck.size() > 0)
    {
      String rawPath = toCheck.pop();
      String[] path = sys.getProcessedPath(rawPath);
      dir = sys.getDirectory(path);
      dirName = String.valueOf(path[path.length -1]);
      if (dir == null)
      {
        path[path.length - 1] = ".";
        dir = sys.getDirectory(path);
        if (dir == null)
        {
          err.invalidPath(rawPath);
          continue;
        }
        files = dir.getFileNames();
        for (String f: files)
        {
          if (f.equals(dirName))
          {
            temp = new String[] {"cat", rawPath};
            cat.run(temp);
            continue;
          }
        }
        err.invalidPath(rawPath);
        continue;
      }
      else
      {
        string += dirName + ":\n";
        files = dir.getFileNames();
        directories = dir.getDirectoryNames();
        for (String f: files) string += f+"\n";
        for (String d: directories) string += d+"\n";
        if (recursive) {
          for (Directory d: dir.getDirectories()) toCheck.push(d.getPath());
        }
        string += "\n";
      }
    }
    output.outFile(string, outFile, sys, err);
  }
  /**
   * Documentation for ls command
   * 
   *  @return ls documentation
   */
  public String toString() {
    //ls's documentation
    String manual = "ls [path ...]\n"
        + "If no paths are given, prints the contents (file and directory) of\n" 
        + "the current directory.\n"
        + "Otherwise, for each path p\n"
        + "   If p specifies a file, prints p\n"
        + "   If p specifies a directory, prints the contents of the directory\n";

    return manual; 
  } 

}
