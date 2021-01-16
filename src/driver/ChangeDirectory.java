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
 * Represents the command that allows the user to change the
 * current working directory in the file system.
 * @author Jesse Yao
 */
public class ChangeDirectory extends Command {

  /**
   * The name of the command as used by the user.
   */
  private String commandName;
  /**
   * The file system this command interacts with.
   */
  private FileSystemShell system;
  /**
   * The error handler this command uses to show errors.
   */
  private ErrorHandler errorHandler;

  /**
   * Constructs a ChangeDirectory object.
   * @param system the file system the command interacts with
   * @param errorHandler the error handler the command uses to show errors
   */
  public ChangeDirectory(FileSystemShell system, ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
    this.system = system;
    commandName = "cd";
  }

  /**
   * Returns the name of the command, as used by the user.
   * @return the name of the command
   */
  @Override
  public String getCommandName() {
    return commandName;
  }

  /**
   * Changes the working directory in the file system to the given
   * path in str[1]. If str is not length 2, or the path is invalid,
   * show an error and return.
   * @param str the list of arguments (including command name) given
   *            by the user
   */
  @Override
  public void run(String[] str) {
    if (str.length != 2) {
      if(str.length < 2) {
        errorHandler.notEnoughArguments();
      }
      else {
        errorHandler.tooManyArguments();
      }
      return;
    }
    String path = str[1];
    // set current directory to the one referred to by path
    Directory destination = this.system.getDirectory(path);
    if (destination != null) {
      system.setCurrentNode(destination);
    } else {
      errorHandler.dirNotFound();
    }
  }

  /**
   * Returns the documentation for this command.
   * @return the documentation for this command
   */
  @Override
  public String toString() {
    //cd's documentation
    String manual = "cd DIR\n" +
        "Change directory to DIR, which may be relative to\n" +
        "the current directory or may be a full path.\n" +
        "As with Unix, .. means a parent directory and a .\n" +
        "means the current directory. The foot of the file\n" +
        "system is a single slash: /.\n";
    return manual;
  }

}
