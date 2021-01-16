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
 * Represents the shell
 * 
 * @author Omar Bin Mateen
 * @version Nov 28, 2020
 */
public class JShell {

  /**
   * Handles the actual runtime of the shell
   * 
   * @param args by default
   */
  public static void main(String[] args) {
    Scanner sc= new Scanner(System.in); 

    //Create a new FileSystem instance
    FileSystemShell system = FileSystem.createFileSystemInstance();

    //Create a new Exit instance
    Exit exit = new Exit();

    //Create a new Input instance
    Input input = new Input(system, exit);
    String userInput = "";

    do {

      System.out.print(system.getCurrentNode().getPath() + "#:");
      //Receive input from user
      userInput = sc.nextLine();
      ErrorHandler.resetErrorOccurred();
      Output.resetLastPrinted();
      input.checkInput(userInput);

    } while(exit.isShellOpen()); //Keep looping as long as exit is not called

    //Close Scanner
    sc.close();
  }

}
