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
 * Represents the basic outline of each command's class
 * 
 * @author Omar Bin Mateen
 * @version Nov 25, 2020
 */
public class Command {

  /**
   * Construct a Command object
   */
  public Command() {}

  /**
   * Gets the name of the command
   * 
   * @return ""
   */
  public String getCommandName() {
    return "";
  }

  /**
   * Executes the function of the command
   * 
   * @param str is the user input trimmed and split into a string array
   */
  public void run(String[] str) {}

  /**
   * Executes the function of the command
   * 
   * @param str is the user input trimmed and split into a string array
   * @param outFile is the part to append or overwrite the output to OutFile
   */
  public void run(String[] str, String outFile) {}

  /**
   * Documentation for each command
   * 
   * @return ""
   */
  @Override
  public String toString() {
    return "";
  }  

}
