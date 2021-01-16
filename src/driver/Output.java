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
 * Represents the Output class which takes care of printing to the shell
 * 
 * @author Omar Bin Mateen
 * @version Nov 29, 2020
 */
public class Output {

  /**
   * Keeps track of what has been printed to the console
   */
  private static String lastPrinted;

  /**
   * Construct a Output object
   */
  public Output() {
    lastPrinted = "";
  }

  /**
   * Print to the shell
   */
  public void print(String string) {
    System.out.println(string);
    lastPrinted += string + "\n";
  }

  /**
   * Returns what has been printed to the console during execution
   * of the current command.
   */
  public static String getLastPrinted() {
    return lastPrinted;
  }

  /**
   * Resets the output of getLastPrinted() back to an empty string,
   * to prepare for the next command.
   */
  public static void resetLastPrinted() {
    lastPrinted = "";
  }

  /**
   * This method is for stdOut commands to out file the output to a file.
   * 
   * @param string is the output of the command
   * @param outFile is the substring of the input after first >
   * @param system is the file system
   * @param errorHandler is the error handler
   */
  public void outFile(String string, String outFile, FileSystemShell system, 
      ErrorHandler errorHandler) {
    //out files the string to file using echo
    if(!outFile.equals("")) {
      Echo echo = new Echo(system, errorHandler);
      //System.out.println("echo \""+string+"\""+outFile);
      //the boolean checks if it is working with another command
      echo.run("echo \""+string+"\""+outFile, true);
    }   	
    //prints if no out filing
    else {
      this.print(string);
    }
  }

}
