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
 * Represents the popd command which takes care of retrieving formerly pushed paths
 * 
 * @author Omar Bin Mateen
 * @version Nov 9, 2020
 */
public class Pop extends Command{

  /** Stores the name of the command */
  private String commandName;
  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** Used to handler errors */
  private ErrorHandler errorHandler;

  /**
   * Construct a Pop object to handle popping from the directory stack
   * 
   * @param system is the current FileSystem being interacted with
   * @param errorHandler is used to handle errors
   */
  public Pop(FileSystemShell system, ErrorHandler errorHandler) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.commandName = "popd";
  }

  /**
   * Gets the path from the top of the directory stack, then changes directory
   * to the extracted path
   * 
   * @param str is the user input trimmed and split into a string array 
   */
  @Override
  public void run(String[] str) {

    if(str.length == 1) {

      //First checks if the directory stack is empty
      if(system.getDirectoryStack().isEmpty()) {
        //Error: stack is empty
        //And exits the method
        errorHandler.emptyStack();
        return;
      }

      //Gets the path on the top of the directoryStack
      String poppedPath = system.getDirectoryStack().pop();
      String[] newStr = {str[0], poppedPath};
      ChangeDirectory cd = new ChangeDirectory(this.system, this.errorHandler);
      cd.run(newStr);

    }
    else {
      //Error: too many arguments
      errorHandler.tooManyArguments();
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
   * Documentation for the popd command
   * 
   * @return popd documentation
   */
  @Override
  public String toString() {
    //popd's documentation
    String manual = "popd\n"
        + "Removes the top most directory from the directory\n" 
        + "stack and makes it the current working directory.\n"
        + "The removal follows the LIFO behaviour of stack\n";

    return manual;
  }

}
