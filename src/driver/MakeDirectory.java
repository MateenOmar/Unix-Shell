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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Create new directories within a
 * File system
 * 
 * @author Kevin Fernandes
 * @version 20/11/10
 */
public class MakeDirectory extends Command
{
  /**
   * calling name of command
   */
  private String commandName;
  /**
   * File system to make directories within
   */
  private FileSystemShell system;
  /**
   * Error Handler to send errors to
   */
  private ErrorHandler errorHandler;

  /**
   * Construct MakeDirectory command object
   * 
   * @param sys FileSystem to interact with
   * @param err Error Handler to use
   */
  public MakeDirectory(FileSystemShell system, ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
    this.system = system;
    commandName = "mkdir";
  }

  /**
   * Get usable name of command
   * 
   * @return name of command
   */
  public String getCommandName()
  {
    return commandName;
  }

  /**
   * mkdir DIR1 DIR2 
   * This command takes in two arguments only. Creates directories, each of which
   * may be relative to the current directory or may be a full path. If creating DIR1 
   * results in any kind of error, does not proceed with creating DIR 2. However, if 
   * DIR1 was created successfully, and DIR2 creation results in an error, then gives
   * error specific to DIR2. 
   * 
   * @param str user given call to mkdir
   */
  public void run(String[] str)
  {
    String dir;
    Directory dirPlace;
    if (str.length < 2)
    {
      errorHandler.notEnoughArguments();
    }

    for (int i = 1; i < str.length; i++)
    {

      String[] path = system.getProcessedPath(str[i]);
      dir = String.valueOf(path[path.length -1]);
      if(containsLegalCharacters(dir) && !(dir.equals("")) &&
          !(dir.equals("..")) && !(dir.equals("."))) 
      {       
        path[path.length - 1] = ".";
        dirPlace = system.getDirectory(path);
        if (dirPlace == null)
        {
          errorHandler.dirNotFound();
          return;
        }
        if (alreadyExists(dirPlace,dir))
        {
          errorHandler.dirAlreadyExists();
          return;
        }
        dirPlace.addDirectory(dir);

      }
      else {
        errorHandler.invalidDirectoryName(str[i]);
        return;
      }
    }
    return;
  }

  /**
   * Checks if a file or directory of the same name
   * already exists within the directory
   * 
   * @param name name of file/directory to check
   * @return true iff a file or directory of the same name exists
   */
  private boolean alreadyExists(Directory dir, String name)
  {
    String[] dirNames = dir.getDirectoryNames();
    String[] fileNames = dir.getFileNames();
    for (String d: dirNames)
    {
      if(d.equals(name))
      {
        return true;
      }
    }
    for (String f: fileNames)
    {
      if(f.equals(name))
      {
        return true;
      }
    }
    return false;
  }
  /**
   * Checks for illegal characters within str
   * 
   * @param str string to be checked
   * @return True iff str contains only legal characters
   */
  private boolean containsLegalCharacters(String str) {

    //Allowed characters
    Pattern p = Pattern.compile("[a-zA-Z0-9-+:;,=_]*");
    Matcher m = p.matcher(str);
    boolean b = m.matches();

    //True if there is not any non-allowed character in dir name
    return b;
  }

  /**
   * Documentation for mkdir
   * 
   * @return mkdir Documentation
   */
  public String toString() {
    //mkdir's documentation
    String manual = "mkdir DIR1 DIR2\n" +
        "Create directories, each of which may be relative to the current \n" +
        "directory or may be a full path. If creating DIR1 results in any \n" +
        "kind of error, do not proceed with creating DIR 2. However, if DIR1\n" +
        "was created successfully, and DIR2 creation results in an error, \n" +
        "then give back an error specific to DIR2.\n";

    return manual; 
  } 

}