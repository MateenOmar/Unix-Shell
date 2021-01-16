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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Represents the curl command which takes care of getting a file from a URL
 * 
 * @author Omar Bin Mateen
 * @version Nov 30, 2020
 */
public class Curl extends Command{

  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** Used to handler errors */
  private ErrorHandler errorHandler;
  /** Stores the name of the command */
  private String commandName;

  /**
   * Construct a Curl object to be used for getting files from URLs
   * 
   * @param system is the current FileSystem being interacted with
   * @param errorHandler is used to handle errors
   */
  public Curl(FileSystemShell system, ErrorHandler errorHandler) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.commandName = "curl";
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
   * Gets the file located at the URL passed in and saves it and its contents
   * to the current working directory
   * 
   * @param str is the user input trimmed and split into a string array 
   */
  @Override
  public void run(String[] str) {

    if(str.length != 2) {

      if(str.length < 2) {
        //Error: not enough arguments
        errorHandler.notEnoughArguments();}
      else {
        //Error: too many arguments
        errorHandler.tooManyArguments();
      }

    }
    else {

      try {
        URL url = new URL(str[1]);
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        //Splits the URL to get the file name
        String[] fileNameSplit = str[1].substring(str[1].lastIndexOf("/") + 1).split("\\.");
        String fileName = fileNameSplit[0] + fileNameSplit[1];

        String inputLine;
        String fileContents = "";

        //Reads the contents of the file and appends it to fileContents
        while((inputLine = br.readLine()) != null) {

          if(fileContents.equals("")) {
            fileContents = inputLine;}
          else {
            fileContents = fileContents + "\n" + inputLine;
          }

        }

        //Add the file to current working directory with fileName and fileContents
        system.getCurrentNode().addFile(fileContents, fileName);        

        br.close();
      } catch (IOException e) {
        //Error: invalid URL
        errorHandler.invalidURL();
      }

    }

  }

  /**
   * Documentation for the curl command
   * 
   * @return curl documentation
   */
  @Override
  public String toString() {
    //loadJShell's documentation
    String manual = "curl URL\n"
        + "Retrieve the file at URL and add it\n"
        + "to the current work direcetory\n";

    return manual;
  }

}

