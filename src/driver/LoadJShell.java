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
import java.io.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Represents the loadJShell command which takes care of loading a saved JShell
 * 
 * @author Omar Bin Mateen
 * @version Nov 30, 2020
 */
public class LoadJShell extends Command{

  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** Used to handler errors */
  private ErrorHandler errorHandler;
  /** Stores the name of the command */
  private String commandName;
  /** A running list of commands entered by the user */
  private ArrayList<String> userHistory;

  /**
   * Construct a LoadJShell object to be used for loading a previously saved JShell
   * 
   * @param system is the current FileSystem being interacted with
   * @param errorHandler is used to handle errors
   * @param userHistory the list to be used to store the history of commands
   *                    entered in by the user
   */
  public LoadJShell(FileSystemShell system, ErrorHandler errorHandler, ArrayList<String> userHistory) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.userHistory = userHistory;
    this.commandName = "loadJShell";
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
   * Gets the saved JShell file at the provided path, then loads the previously saved JShell
   * into the current file system
   * 
   * @param str is the user input trimmed and split into a string array 
   */
  @Override
  public void run(String[] str) {

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

      java.io.File readFile = new java.io.File(str[1]); 

      if(readFile.exists()) {

        readFunction(readFile);

      }
      else {
        //Error: File or directory does not exist
        errorHandler.dirOrFileNotFound();
      }

    }

  }

  /**
   * Handle the execution of reading the contents of the readFile and implementing back
   * into the current filesystem
   * 
   * @param readFile is the file being read from
   */
  private void readFunction(java.io.File readFile) {

    try {

      BufferedReader br = new BufferedReader(new FileReader(readFile));
      Stack<String> directoryStack = new Stack<String>();

      String[] line = br.readLine().split(" ");
      //Number of sub-directories and files of root
      int numDirs = Integer.parseInt(line[3]);
      int numFiles = Integer.parseInt(line[4]);
      //The directory in which the user was last in before saving
      String currentDirectory = line[5];
      line = br.readLine().split(" ");

      if(!line[0].equals("")) {

        for(String path : line) {
          directoryStack.push(path);}
        system.setDirectoryStack(directoryStack);}

      //Recursive call on each sub-directory of the root
      for(int i = 0; i < numDirs; i++) {
        readWrittenFile(system.getRoot(), br);}

      //Used to add files of each directory
      readFilesInWrittenFile(system.getRoot(), br, numFiles);

      //Read empty line
      br.readLine();

      String history;
      String loadHistory = userHistory.get(0);
      userHistory.clear();

      //Add the command history back to userHistory
      while((history = br.readLine()) != null) {
        userHistory.add(history);}
      userHistory.add(loadHistory);

      //Change directory to currentDirectory
      ChangeDirectory cd = new ChangeDirectory(this.system, this.errorHandler);
      cd.run(new String[] {"", currentDirectory});

      br.close();
    } catch (Exception a) {
      //Error: invalid JShell file
      errorHandler.invalidFile();
    }

  }

  /**
   * Read all the directories and files in the file then add in them to the
   * correct places
   * 
   * @param directory is the current directory being looked at
   * @param br is the BufferedReader being used to read from the file
   */
  private void readWrittenFile(Directory directory, BufferedReader br) {

    try {
      String[] line = br.readLine().split(" ");
      //Number of sub-directories and files of directory 
      int numDirs = Integer.parseInt(line[3]);
      int numFiles = Integer.parseInt(line[4]);
      directory.addDirectory(line[0]);

      //If directory has no sub-directories or files
      if(numDirs == 0 && numFiles == 0) {
        return;
      }

      //Recursive call on each sub-directory of directory
      for(int i = 0; i < numDirs; i++) {
        readWrittenFile(directory.getDirectory(line[0]), br);
      }

      //Used to add files of each directory
      readFilesInWrittenFile(directory.getDirectory(line[0]), br, numFiles);

    } catch (Exception e) {
      //Error: invalid JShell file
      errorHandler.invalidFile();
    }

  }

  /**
   * Adds all the files that belong to directory back into directory
   * 
   * @param directory is the current directory being looked at
   * @param br is the BufferedReader being used to read from the file
   * @param numFiles is the number of files in this directory
   */
  private void readFilesInWrittenFile(Directory directory, BufferedReader br, int numFiles) {

    try {

      for(int i = 0; i < numFiles; i++) {
        String lineFileName = br.readLine();
        String fileContents = "";
        String lineContent = "";
        //Delimiter used to ensure when file contents start and finish
        if(br.readLine().equals("-startdata-")) {

          while(!(lineContent = br.readLine()).equals("-enddata-")) {

            //Add the contents of each line to fileContents until end delimiter is not read
            if(fileContents.equals("")) {
              fileContents = lineContent;
            }
            else {
              fileContents = fileContents + "\n" + lineContent;
            }

          }

        }

        //Add file to current working directory
        directory.addFile(fileContents, lineFileName);
      }

    } catch (Exception e) {
      //Error: invalid JShell file
      errorHandler.invalidFile();
    }

  }

  /**
   * Documentation for the loadJShell command
   * 
   * @return loadJShell documentation
   */
  @Override
  public String toString() {
    //loadJShell's documentation
    String manual = "loadJShell FileName\n"
        + "Load in the saved JShell FileName or located at the path provided\n"
        + "in FileName, and make it the current working filesystem. This command\n"
        + "can only be executed at the beginning of a new JShell.\n";

    return manual;
  }

}
