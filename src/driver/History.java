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

import java.util.ArrayList;

/**
 * Represents the command that allows the user to view a list of
 * previous commands entered during the session. 
 * @author Jesse Yao
 */
public class History extends Command{

  /**
   * The name of the command as used by the user.
   */
  private String commandName;
  /**
   * The error handler this command uses to print errors.
   */
  private ErrorHandler errorHandler;
  /**
   * A running list of commands entered by the user.
   */
  private ArrayList<String> userHistory;
  /**
   * The output object used to print the command history.
   */
  private Output output = new Output();
  /**
   * 
   */
  private FileSystemShell system;

  /**
   * Constructs a History command object.
   * @param userHistory the list to be used to store the history of commands
   *                    entered in by the user
   * @param errorHandler the error handler to be used to show errors to the
   *                     user
   */
  public History(ArrayList<String> userHistory, FileSystemShell system, ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
    this.userHistory = userHistory;
    this.system = system;
    commandName = "history";
  }

  /**
   * Returns the name of this command, as used by the user.
   * @return the name of this command
   */
  @Override
  public String getCommandName() {
    return commandName;
  }

  /**
   * Displays a list of previous commands entered by the user during
   * this session. If str[].length == 1, display the entire history.
   * If str.length == 2 and str[1] represents an integer n >= 0, display
   * the last n commands entered by the user. Otherwise, show an error
   * and return.
   * @param str the list of arguments (including command name) given by
   *            the user 
   * @param outFile is the substring of the input after first >
   */
  @Override
  public void run(String[] str, String outFile) {
    if (str.length > 2) {
      errorHandler.tooManyArguments();
      return;
    }

    // truncate history so only the last numCommands commands show
    int numCommands;
    if (str.length > 1) {
      // set numCommands to the second argument, if possible
      try {
        numCommands = Integer.parseInt(str[1]);
      } catch(NumberFormatException e) {
        // not a valid integer
        errorHandler.invalidArgument(str[1]);
        return;
      }
      if (numCommands < 0) {
        // integer is less than 0
        errorHandler.invalidArgument(str[1]);
        return;
      }
    } else {
      // if no arguments are given other than the command name,
      // just have the command display the complete history
      numCommands = userHistory.size();
    }

    // calculate starting index i
    int i = userHistory.size() - numCommands;
    if (i < 0) {
      i = 0;
    }
    // takes in all strings to output
    String string = "";
    // print the list starting from i
    for (;i < userHistory.size(); i++) {
      string += (i + 1) + ". " + userHistory.get(i)+"\n";
    }
    output.outFile(string, outFile, system, errorHandler);
  }

  /**
   * Returns the documentation for this command.
   * @return the documentation for this command
   */
  @Override
  public String toString() {
    //history's documentation
    String manual = "history [number]\n"
        + "This command will print out recent commands, one command per line.\n"
        + "The output from history has two columns. The first column is\n"
        + "numbered such that the line with the highest number is the most\n" 
        + "recent command. The second column contains the actual command.\n" 
        + "Note: The output also contains any syntactical erros tped by the user\n";

    return manual; 
  } 

}
