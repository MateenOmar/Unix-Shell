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
 * Represents the error handling
 * 
 * @author Omar Bin Mateen
 * @version Nov 30, 2020
 */
public class ErrorHandler {

  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** An array that hold instances of all commands */
  private Command[] commands;
  /** Output instance used to print to the shell */
  private Output output;
  /** True iff an error occurred during execution of the current command */
  private static boolean errorOccurred = false;

  /**
   * Construct a ErrorHandler object to handle all errors
   * 
   * @param system is the current FileSystem being interacted with
   */
  public ErrorHandler(FileSystemShell system) {
    this.system = system;
    Command[] cmds = {new Exit(), new Concatenate(this.system, null), 
        new MakeDirectory(this.system, null),new ChangeDirectory(this.system, null), 
        new Man(system, null), new Echo(this.system, null), new Push(this.system, null),
        new Pop(this.system, null), new Echo(this.system, null), new List(this.system, null), 
        new PrintWorkingDirectory(this.system, null), new History(null,this.system, null),
        new SaveJShell(this.system,null,null), new LoadJShell(this.system, null, null),
        new Remove(this.system, null), new Copy(this.system, null), new Tree(this.system, null),
        new Search(this.system, null), new Curl(this.system, null), new Move(this.system, null)};
    this.commands = cmds;
    this.output = new Output();
  }

  /**
   * Checks to see whether users inputed a valid command
   * 
   * @param str is the user input trimmed and split into a string array
   * @return true if str[0] is a valid command
   */
  public boolean checkIfLegalCommand(String[] str) {

    //Iterate through each instance of a command in command
    for(Command c: commands) {
      //Check if c's name is equal to str[0]
      if(c.getCommandName().equals(str[0])) {
        return true;
      } 
    }

    return false;
  }

  /**
   * Checks if an error has occurred during execution of the current command.
   * @return true if an error has occurred during execution of the
   *         current command, false otherwise
   */
  public static boolean checkIfErrorOccurred() {
    return errorOccurred;
  }

  /**
   * Resets the checkIfErrorOccurred() state back to false.
   */
  public static void resetErrorOccurred() {
    errorOccurred = false;
  }

  /**
   * Prints message to output and flag that an error has occurred
   * during execution of the current command.
   * @param message string to print to shell
   */
  private void produceError(String message) {
    this.output.print(message);
    errorOccurred = true;
  }

  /**
   * Error handling for when too many arguments are passed when calling a command
   */
  public void tooManyArguments() {
    produceError("too many arguments");
  }

  /**
   * Error handling for when not enough arguments are passed when calling a command
   */
  public void notEnoughArguments() {
    produceError("not enough arguments");
  }

  /**
   * Error handling for when an invalid command is passed
   */
  public void commandNotFound() {
    produceError("command not found");
  }

  /**
   * Error handling for when a directory which already exists is trying to be 
   * created again
   */
  public void dirAlreadyExists() {
    produceError("directory already exists");
  }

  /**
   * Error handling for when a file which already exists is trying to be 
   * created again
   */
  public void fileAlreadyExists() {
    produceError("file already exists");
  }

  /**
   * Error handling for when a directory which does not exist is trying to be found
   */
  public void dirNotFound() {
    produceError("directory not found");
  }

  /**
   * Error handling for when a directory or file which does not exist is trying 
   * to be found
   */
  public void dirOrFileNotFound() {
    produceError("directory or file not found");
  }

  /**
   * Error handling for when an invalid argument is passed
   */
  public void invalidArgument() {
    produceError("invalid argument");
  }

  /**
   * Error handling for when an invalid argument is passed 
   * 
   * @param arg is the invalid argument
   */
  public void invalidArgument(String arg) {
    produceError("invalid argument: " + arg);
  }

  /**
   * Error handling for when an a name is not valid for a directory
   */
  public void invalidDirectoryName() {
    produceError("directory name not valid");
  }
  /**
   * Error handling for when an a name is not valid for a directory
   */
  public void invalidDirectoryName(String name) {
    produceError("directory name '"+name+"' not valid");
  }
  /**
   * Error handling for when an a name is not valid for a directory
   */
  public void delRoot() {
    produceError("Can't delete root");
  }
  /**
   * Error handling for when an a name is not valid for a file
   */
  public void invalidFileName() {
    produceError("file name not valid");
  }

  /**
   * Error handling for when the second directory name is not valid
   */
  public void invalidSecondDirectoryName() {
    produceError("second directory name not valid");
  }

  /**
   * Error handling for when the directory stack is empty
   */
  public void emptyStack() {
    produceError("directory stack is empty");
  }


  /**
   * Error handling for when the file does not exist is trying to be found
   */
  public void fileNotFound() {
    produceError("file not found");
  }

  /**
   * Error handling for when a directory tries to move/copy to itself
   */
  public void dirsAreSame() {
    produceError("cannot move/copy directory to same directory");
  }
  
  /**
   * Error handling for when a directory tries to move/copy 
   * to a child of itself
   */
  public void dirIsParent() {
    produceError("cannot move/copy directory to child of itself");
  }

  /**
   * Error handling for when a directory tries to move/copy to a file
   */
  public void invalidDest() {
    produceError("cannot move/copy directory to file");
  }

  /**
   * Error handling for when an invalid url is passed
   */
  public void invalidURL() {
    produceError("invalid URL");
  }

  /**
   * Error handling for when loadJShell is tried to run after already running a command
   * in the JShell
   */
  public void newJShellStarted() {
    produceError("cannot load a saved JShell as a new file system has been created");
  }

  /**
   * Error handling for when an invalid file is tried to access from JShell
   */
  public void invalidFile() {
    produceError("invalid JShell file");
  }

  /**
   * Error handling for when a file is referenced like a directory (/ at end)
   */
  public void notADirectory() {
    produceError("cannot reference file like a directory");
  }

  /**
   * Error handling for when a path is invalid
   */
  public void invalidPath(String path) {
    produceError("Path: " + path + " is not a valid path");

  }

  /**
   * Error handling for when a file cannot be created
   */
  public void fileCouldNotBeMade() {
    produceError("file failed to be created");
  }

  /**
   * Error handling for a directory is not found or file name is not valid
   */
  public void dirNotFoundAndFileNameNotValid() {
    produceError("directory not found and/or file name not valid");
  }

  /**
   * Error handling for when the user tries to move the current working
   * directory, or a parent of the current working directory
   */
  public void cannotMoveWD() {
    produceError("cannot move current working directory/"
        + "parent of current working directory");
  }
  
  /**
   * Error handling for when the user tries to move a directory into a
   * location where a non-empty directory sharing its name already exists
   */
  public void destIsNonEmpty() {
    produceError("cannot move directory contents into non-empty location");
  }

}