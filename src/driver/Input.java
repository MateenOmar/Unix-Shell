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
 * Represents the input handling
 * 
 * @author Omar Bin Mateen
 * @version Nov 30, 2020
 */
public class Input {

  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** The current Exit controlling the runtime of JShell */
  private Exit exit;
  /** An array that hold instances of all commands */
  private Command[] commands;
  /** Used to handler errors */
  private ErrorHandler errorHandler;
  /** Stores all raw user input */
  private ArrayList<String> userHistory = new ArrayList<String>();

  /**
   * Construct a Input object to handle all user input
   * 
   * @param system is the current FileSystem
   * @param exit is what controls the JShell to stay running
   */
  public Input(FileSystemShell system, Exit exit) {
    this.system = system;
    this.exit = exit;
    errorHandler = new ErrorHandler(this.system);
    //Array to hold an instance of all commands to avoid duplication
    Command[] cmds = {this.exit, new Concatenate(this.system, this.errorHandler), 
        new MakeDirectory(this.system, this.errorHandler),
        new ChangeDirectory(this.system, this.errorHandler), new Man(this.system, this.errorHandler),
        new Echo(this.system, this.errorHandler),new Push(this.system, this.errorHandler), 
        new Pop(this.system, this.errorHandler), new List(this.system, this.errorHandler),
        new PrintWorkingDirectory(this.system, this.errorHandler),
        new History(this.userHistory, this.system, this.errorHandler), 
        new SaveJShell(this.system, this.errorHandler, this.userHistory),
        new LoadJShell(this.system, this.errorHandler, this.userHistory),
        new Remove(this.system, this.errorHandler), new Copy(this.system, this.errorHandler), 
        new Tree(this.system, this.errorHandler), new Search(this.system, this.errorHandler),
        new Curl(this.system, this.errorHandler), new Move(this.system, this.errorHandler)};
    this.commands = cmds;
  }
  /**
   * Checks whether user input is actually a valid command, reports back an error
   * if invalid input, otherwise passes on the input to look for the relevant 
   * command 
   * 
   * @param userInput is the raw user input that was inputed into the shell
   */
  public void checkInput(String userInput) {

    userHistory.add(userInput);

    //Trim and split userInput
    String[] userInputSplit = userInput.trim().replaceAll(" +", " ").split(" ");
    String[] commandInput = userInput.split(">",2);
    String[] str = userInputSplit;
    String outFile = "";
    //checks if commands are not like ls>file or ls> file
    if(commandInput.length > 1 && str[0].split(">",-1).length == 1) {
      str = commandInput[0].trim().replaceAll(" +", " ").split(" ");
      outFile = ">"+commandInput[1];
    }

    if(errorHandler.checkIfLegalCommand(Arrays.copyOfRange(str, 0, 1))) {

      //Find the specific command and run its action
      findCommand(str, userInput, outFile);

    }
    else {
      //Error: command not found
      errorHandler.commandNotFound();
    }

  }

  /**
   * Uses the name of the command user inputed, and cross checks with every command
   * name until the correct command isn't found. Once found it calls the run(.)
   * method on the command
   * 
   * @param outFile is the substring of the input after first >
   * @param str is the user input trimmed and split into a string array
   * @param userInput is the raw user input that was inputed into the shell
   */
  private void findCommand(String[] str, String userInput, String outFile) { 
    Echo echo = new Echo(this.system, this.errorHandler);
    Man man = new Man(this.system, this.errorHandler);
    LoadJShell loadJShell = new LoadJShell(this.system, this.errorHandler, this.userHistory);
    //array to hold commands that have stdOuts
    Command[] stdOutCommands = {new Concatenate(system, errorHandler), 
        new PrintWorkingDirectory(system, errorHandler), 
        new Tree(this.system, this.errorHandler),
        new History(this.userHistory, this.system, this.errorHandler),
        new List(this.system, this.errorHandler), 
        new Search(this.system, this.errorHandler)}; 
    if(str[0].equals(echo.getCommandName())) {
      echo.run(userInput, false);
    }
    else if(str[0].equals(man.getCommandName())) {
      man.run(str, this.commands, outFile);
    }
    else if(str[0].equals(loadJShell.getCommandName()) && userHistory.size() > 1){
      errorHandler.newJShellStarted();
    }
    else {
      Boolean check = false;
      for(Command c:stdOutCommands) {
        if(c.getCommandName().equals(str[0])) check = true;
      }
      //Iterate through each instance of a command in commands
      for(Command c: commands) {
        //Check if c's name is equal to str[0]
        if(c.getCommandName().equals(str[0])) {
          //Run the run method with respect to c
          if(check) c.run(str, outFile);//for stdOut commands
          else c.run(str);
          break;
        }
      }
    }
  }
}

