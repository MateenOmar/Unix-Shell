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
 * Represents the man command which takes care of fetching command documentations
 * 
 * @author Omar Bin Mateen
 * @version Nov 30, 2020
 */
public class Man extends Command{

  /** Stores the name of the command */
  private String commandName;
  /** Used to handler errors */
  private ErrorHandler errorHandler;
  /** The current FileSystem being interacted with  */
  private FileSystemShell system;

  /**
   * Construct a Man object to be used for getting documentation of commands 
   * 
   * @param errorHandler is used to handle errors
   * @param system is the current FileSystem being interacted with
   */
  public Man(FileSystemShell system, ErrorHandler errorHandler) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.commandName = "man";
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
   * Gets the command passed in str, and prints out its documentation
   * 
   * @param str is the user input trimmed and split into a string array 
   * @param commands is an array of instances of all commands previously initialized
   * @param outFile is the substring of the input after first >
   */
  public void run(String[] str, Command[] commands, String outFile) {

    Output output = new Output();

    if(str.length != 2) {

      if(str.length < 2) {
        //Error: not enough arguments
        errorHandler.notEnoughArguments();
      }
      else {
        //Error: too many arguments
        errorHandler.tooManyArguments();
      }

    }
    else {

      if(errorHandler.checkIfLegalCommand(Arrays.copyOfRange(str, 1, 2))) {

        for(Command c: commands) {
          //Call .toString for the relevant command
          if(c.getCommandName().equals(str[1])) {
            output.outFile(c.toString(), outFile, system, errorHandler);
            break;
          } 

        }

      }
      else {
        //Error: command not found
        //Does not proceed with any more commands, if any
        errorHandler.commandNotFound();
      }

    }

  }

  /**
   * Documentation for the man command
   * 
   * @return man documentation
   */
  @Override
  public String toString() {
    //man's documentation
    String manual = "man CMD [CMD2 ...]\n"
        + "Print the documentation for CMD(s)\n";

    return manual;
  }

}
