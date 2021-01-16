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
 * Represents the exit command which takes care of keeping the shell running 
 * 
 * @author Omar Bin Mateen
 * @version Nov 9, 2020
 */
public class Exit extends Command{

  /** Stores the name of the command */
  private String commandName;
  /** Stores the current status of shell being active */
  private boolean shellOpen;

  /**
   * Construct a Exit object to handle JShell 
   */
  public Exit() {
    this.shellOpen = true;
    this.commandName = "exit";
  }

  /**
   * Get the value of this.shellOpen
   * 
   * @return the value of this.shellOpen
   */
  public boolean isShellOpen() {
    //Return whether the shell is open or closed
    return shellOpen;
  }

  /**
   * Set the value of this.shellOpen
   * 
   * @param shellOpen is the value that wants to be set
   */
  private void setShellOpen(boolean shellOpen) {
    //Change the value of shellOpen
    this.shellOpen = shellOpen;
  }

  /**
   * Change the value of this.shellOpen to false, in turn closing the shell
   * 
   * @param str is the user input trimmed and split into a string array
   */
  public void run(String[] str) {
    ErrorHandler errorHandler = new ErrorHandler(null);

    if(str.length != 1) {
      errorHandler.tooManyArguments();
    }
    else {
      setShellOpen(false);
    }

  }

  /**
   * Gets the name of the command
   * 
   * @return the name of the command, commandName
   */
  @Override
  public String getCommandName() {
    //Return the name used to call the command
    return commandName;
  }

  /**
   * Documentation for the exit command
   * 
   * @return exit documentation
   */
  @Override
  public String toString() {
    //exit's documentation
    String manual = "exit\n"
        + "Quit the program\n";

    return manual;
  }

}
