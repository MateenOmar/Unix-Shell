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
 * Concatenate prints data of 1 or more files
 * 
 * @author Kazi Shafin Shafayet
 * @version Nov 30, 2020
 *
 */
public class Concatenate extends Command{

  /**
   * system contains File system to interact with
   */
  private FileSystemShell system;
  /**
   * errorHandler contains the Error handler to use
   */
  private ErrorHandler errorHandler;
  /**
   * commandName contains the legal name of concatenate
   */
  private String commandName;

  /**
   * Construct Concatenate command object
   * 
   * @param system contains File system to interact with
   * @param errorHandler contains Error handler to use
   */
  public Concatenate(FileSystemShell system, ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
    this.system = system;
    this.commandName = "cat";
  }

  /**
   * returns legal name of concatenate, cat.
   * 
   * @return the legal name of concatenate
   */
  @Override
  public String getCommandName() {
    return commandName;
  }

  /**
   * cat File1 [File2 ...]
   * This command takes in 1 or more file and outputs the contents of the file
   * with 3 new lines in between each file
   * 
   * @param str user given call to cat
   * @param outFile is the string used for redirection
   */

  public void run(String[] str, String outFile) {
    Output output = new Output();
    String string = "";//output string
    //checks if user inputed a file with the command
    if(str.length >= 2) {
      string = runCat(str, outFile);
    }else {//error for wrong input for cat command
      errorHandler.notEnoughArguments();
    }
    if(!outFile.equals("")) {
      output.outFile(string, outFile, system, errorHandler);
    }
  }


  /**
   * This method runs cat command
   * 
   * @param str user given call to cat
   * @return returns the output for cat command
   */
  private String runCat(String[] str, String outFile) {
    Output output = new Output();
    //goes through every file name that the user inputed
    String outString = "";
    for(int i = 1; i < str.length; i++) {
      String fileName = "";//stores the name of File
      //checks if user inputed a file name or a path to file
      if(checkIfSlash(str[i])) {
        //separates the path and the file name
        String[] temp = str[i].split("/");
        String dirLoc = "";//stores path
        int j = 0;
        if(str[i].charAt(0) == '/') {
          dirLoc = "/";
          j++;
        }
        for(; j < temp.length-1; j++) {
          dirLoc += temp[j];
          if(j != temp.length-2) dirLoc += "/";
        }
        Directory dir = system.getDirectory(dirLoc);//stores path directory
        if(temp.length != 0) fileName = temp[temp.length-1];
        else {
          errorHandler.invalidArgument();
          break;}
        if(dir != null) { //checks if directory exists
          //checks if file exists
          if(dir.getFile(fileName) != null) {
            //outputs content of File
            if(outFile.equals("")) output.print(dir.getFile(fileName).getData());
            else outString += dir.getFile(fileName).getData()+"\n";
          }else { //error if file does not exist
            errorHandler.fileNotFound();
            break;}
        }else { //error if directory does not exist
          errorHandler.dirNotFound();
          break;}
      }else { //user inputs only file name
        fileName = str[i];
        Directory dir = system.getCurrentNode();//current directory
        if(dir.getFile(fileName) != null) {
          //outputs content of File
          if(outFile.equals("")) output.print(dir.getFile(fileName).getData());
          else outString += dir.getFile(fileName).getData()+"\n";
        }else { //error if file does not exist
          errorHandler.fileNotFound();
          break;}}
      if(i != str.length-1&&str[i+1].charAt(0)!='>') outString += "\n\n\n";
      if(i != str.length-1&&str[i+1].charAt(0)!='>'&&outFile.equals("")) output.print("\n\n");}
    return outString;
  }

  /**
   * checks if there is a / in file to know if it is a path or not
   * 
   * @param str is the File
   * @return true if there is a /, false otherwise
   */
  private boolean checkIfSlash(String str) {
    for(int i = 0; i < str.length()-1; i++) {
      if(str.charAt(i)== '/') return true;
    }
    return false;
  }
  
  /**
   * returns the documentation of concatenate
   * 
   * @return concatenate documentation
   */
  @Override
  public String toString() {
    //description of command cat
    String manual = "cat FILE1 [FILE2…]\n"
        + " Display the contents of FILE1 and other files\n"
        + "(i.e. File2…) concatenated in the shell.\n";
    return manual;
  }

}
