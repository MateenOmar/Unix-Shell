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
 * Represents the pushd command which pushes current path onto a directory stack
 * 
 * @author Omar Bin Mateen
 * @version Nov 9, 2020
 */
public class Push extends Command{

  /** Stores the name of the command */
  private String commandName;
  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** Used to handler errors */
  private ErrorHandler errorHandler;

  /**
   * Construct a Push object to handle pushing onto the directory stack
   * 
   * @param system is the current FileSystem being interacted with
   * @param errorHandler is used to handle errors
   */
  public Push(FileSystemShell system, ErrorHandler errorHandler) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.commandName = "pushd";
  }

  /**
   * Pushes the current working directory onto the directoryStack, then changes
   * current working directory to the full or relative path provided with command
   * 
   * @param str is the user input trimmed and split into a string array 
   */
  @Override
  public void run(String[] str) {

    if(str.length != 2) {

      if(str.length < 2) {
        errorHandler.notEnoughArguments();
      }
      else {
        errorHandler.tooManyArguments();
      }

    }
    else {

      if(system.getDirectory(str[1]) != null) {

        //Pushes the current path on to the stack stored in system
        system.getDirectoryStack().add(system.getCurrentNode().getPath());
        ChangeDirectory cd = new ChangeDirectory(this.system, this.errorHandler);
        //Then changes directory to the provided path
        cd.run(str);

      }
      else{
        //Error: no such file or directory
        errorHandler.dirOrFileNotFound();
      }

    }

  }

  /**
   * Gets the name of the command
   * 
   * @return the name of the command, commandName
   */
  @Override
  public String getCommandName() {
    return commandName;
  }

  /**
   * Documentation for the pushd command
   * 
   * @return pushd documentation
   */
  @Override
  public String toString() {
    //pushd's documentation
    String manual = "pushd DIR\n"
        + "Saves the current working directory on a directory stack, and\n"
        + "then changes current working directory to DIR. This follows\n"
        + "the LIFO behaviour of stack.";

    return manual;
  }

}
