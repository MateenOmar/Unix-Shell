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

/**
 * Represents the saveJShell command which takes care of saving the current JShell
 * 
 * @author Omar Bin Mateen
 * @version Nov 30, 2020
 */
public class SaveJShell extends Command{

  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** Used to handler errors */
  private ErrorHandler errorHandler;
  /** Stores the name of the command */
  private String commandName;
  /** A running list of commands entered by the user */
  private ArrayList<String> userHistory;

  /**
   * Construct a SaveJShell object to be used for save a current working JShell
   * 
   * @param system is the current FileSystem being interacted with
   * @param errorHandler is used to handle errors
   * @param userHistory the list to be used to store the history of commands
   *                    entered in by the user
   */
  public SaveJShell(FileSystemShell system, ErrorHandler errorHandler, ArrayList<String> userHistory) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.userHistory = userHistory;
    this.commandName = "saveJShell";
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
   * Saves the current working file system in its entirety with the provided name
   * and in the provided path
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

      java.io.File writeFile = new java.io.File(str[1]);

      try {
        writeFile.createNewFile();
      } catch (IOException e) {

        errorHandler.dirNotFoundAndFileNameNotValid();
        return;
      }

      if(writeFile.exists()){

        writeFunction(writeFile);

      }else {
        //Directory not found
        errorHandler.dirNotFound();
      }

    }

  }

  /**
   * Handle the execution of writing the contents of the current filesystem to writeFile
   * 
   * @param writeFile is the file being written to
   */
  private void writeFunction(java.io.File writeFile) {

    try {

      BufferedWriter writer = new BufferedWriter(new FileWriter(writeFile));
      //Write details about the root directory
      writer.append(system.getRoot().getName() + " ");
      writer.append(system.getRoot().getParent() + " ");
      writer.append(system.getRoot().getPath() + " ");
      writer.append(system.getRoot().getDirectoryNames().length + " ");
      writer.append(system.getRoot().getFileNames().length + " ");
      //Write current path
      writer.append(system.getCurrentNode().getPath() + "\n");

      //Write the contents of directoryStack
      for(int i = 0; i < system.getDirectoryStack().size(); i++) {
        writer.append(system.getDirectoryStack().get(i));
        if(i != system.getDirectoryStack().size()-1) {
          writer.append(" ");
        }
      }
      writer.append("\n");

      //Repeat all writing for sub-directories and files
      writeToFile(system.getRoot(), writeFile, writer);

      writer.append("\n");

      //Write the command history 
      for(String input : userHistory) {
        writer.append(input + "\n");
      }

      writer.close();

    } catch (Exception e1) {
      //Error: file was not able to be created
      errorHandler.fileCouldNotBeMade();
    }

  }

  /**
   * Write all the directories and files to writeFile
   * 
   * @param directory is the current directory being looked at 
   * @param writeFile is the file being written to
   * @param writer is the writer used to write to writeFile
   */
  private void writeToFile(Directory directory, java.io.File writeFile, BufferedWriter writer) {

    //If directory has no sub-directories or files
    if(directory.getDirectoryNames().length == 0 && directory.getFileNames().length == 0) {
      return;
    }

    //Write details about each sub-directory of directory
    for(String name : directory.getDirectoryNames()) {

      try {

        writer.append(directory.getDirectory(name).getName() + " ");
        writer.append(directory.getDirectory(name).getParent() + " ");
        writer.append(directory.getDirectory(name).getPath() + " ");
        writer.append(directory.getDirectory(name).getDirectoryNames().length + " ");
        writer.append(directory.getDirectory(name).getFileNames().length + "\n");

      } catch (IOException e) {
        //Error: file was not able to be created
        errorHandler.fileCouldNotBeMade();
        return;
      }

      //Recursive call on each sub-directory of directory
      writeToFile(directory.getDirectory(name), writeFile, writer);

    }
    //Write the name and contents of each file of directory
    writeFilesToFile(directory, writeFile, writer);

  }

  /**
   * Writes the name and contents of each file that belongs to directory
   * 
   * @param directory is the current directory being looked at
   * @param writeFile is the file being written to
   * @param writer is the writer used to write to writeFile
   */
  private void writeFilesToFile(Directory directory, java.io.File writeFile, BufferedWriter writer) {

    //Write the name and contents of each file of directory
    for(String name : directory.getFileNames()) {

      try {

        writer.append(directory.getFile(name).getName() + "\n");
        writer.append("-startdata-\n");
        writer.append(directory.getFile(name).getData() + "\n");
        writer.append("-enddata-\n");


      } catch (IOException e) {
        //Error: file was not able to be created
        errorHandler.fileCouldNotBeMade();
        return;
      }

    }

  }

  /**
   * Documentation for the saveJShell command
   * 
   * @return saveJShell documentation
   */
  @Override
  public String toString() {
    //saveJShell's documentation
    String manual = "saveJShell FileName\n"
        + "saveJShell must save the entirety of the current working filesystem to FileName.\n"
        + "The file FileName is some file that is stored on the actual filesystem of your\n"
        + "computer. FileName can be a file name or a path that includes file name.\n";

    return manual;
  }

}
