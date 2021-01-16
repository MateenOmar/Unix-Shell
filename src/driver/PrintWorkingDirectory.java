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
/**
 * Prints to current node of the given
 * File system
 * 
 * @author Kevin Fernandes
 * @version 20/11/10
 */
public class PrintWorkingDirectory extends Command
{
  /**
   * File system to get current node from
   */
  private FileSystemShell sys;
  /**
   * calling name of command
   */
  private String commandName;
  /**
   * Error Handler to send errors to
   */
  private ErrorHandler err;

  /**
   * Construct PrintWorkingDirectory command object
   * 
   * @param sys FileSystem to interact with
   * @param err Error Handler to use
   */
  public PrintWorkingDirectory(FileSystemShell sys, ErrorHandler err)
  {
    this.sys = sys;
    this.err= err;
    this.commandName = "pwd";
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
   * pwd  
   * Prints the current working directory (including the whole path).  
   * 
   * @param str user given call to pwd
   * @param outFile is the substring of the input after first >
   */
  public void run(String[] str, String outFile)
  {
    if (str.length != 1)
    {
      err.tooManyArguments();
      return;
    }
    Output out = new Output();
    out.outFile(sys.getCurrentNode().getPath(), outFile, sys, err);
  }


  /**
   * Documentation for pwd
   * 
   * @return pwd Documentation
   */
  public String toString() 
  {
    String manual = "pwd\n"
        + "Prints the current working directory(including the full path)\n";

    return manual;
  }

}
