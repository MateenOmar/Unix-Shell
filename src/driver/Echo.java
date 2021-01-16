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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Echo overwrites and appends String into File, makes new file if File
 * does not exist
 * 
 * @author Kazi Shafin Shafayet
 * @version Nov 10, 2020
 *
 */
public class Echo extends Command{

  /**
   * system contains the File system to interact with
   */
  private FileSystemShell system;
  /**
   * errorHandler contains Error handler to use
   */
  private ErrorHandler errorHandler;
  /**
   * commandName contains the legal command name for echo
   */
  private String commandName;
  /**
   * checks if any is an invalid input
   */
  private boolean check = true;
  /**
   * Construct for Echo command object
   * 
   * @param system File system to interact with
   * @param errorHandler Error handler to use
   */
  public Echo(FileSystemShell system, ErrorHandler errorHandler) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.commandName = "echo";
  }
  /**
   * Get usable name for the command
   * 
   * @return the name of command
   */
  @Override
  public String getCommandName() {
    return commandName;
  }
  /**
   * echo "String">File
   * echo "String">>File
   * 
   * This command takes in a string between quotations to either append, 
   * overwrite or make new file for in current directory or a directory path.
   * > overwrites File with String
   * >> appends to File with String
   * if File does not exist make a new file for File
   * 
   * @param checkIfCommand checks if it is working with another command
   * @param userInput takes in raw user input
   */
  public void run(String userInput, boolean checkIfCommand) {
    Output output = new Output();
    String str = getUserString(userInput, checkIfCommand);//user inputed String
    String outFilePart = getOutFilePart(userInput);//substring after first >
    String[] outTemp = outFilePart.split(" ");//split by white spaces
    String path = seperatePath(outTemp);//path to file location
    String fileName = seperateFileName(outTemp, path);//file name
    int countBrac = countBrac(userInput, checkIfCommand);//number of >'s
    //checks if echo just prints or not
    System.out.println(path);
    if(outTemp.length < 3 && fileName.length() > 0 && check) {
      //checks if string is to append or overwrite
      if(str != null && outTemp[0].charAt(0)=='>' 
          && countBrac == 2 && fileName.charAt(0)!='>') {
        if(outTemp[0].charAt(1)=='>') {
          runOutput(str, ">>", path, fileName);
        }else {
          errorHandler.invalidArgument();//error for invalid input
        }
      }else if(str!=null && outTemp[0].charAt(0) == '>' && countBrac == 1 
          && fileName.charAt(0)!='>') {
        runOutput(str, ">", path, fileName);
      }else {
        errorHandler.invalidArgument();//error for invalid input
      }
    }else if(str != null && outTemp.length < 3 && countBrac == 0 && check){
      output.print(str);
    }else {
      errorHandler.invalidArgument();//error for invalid input
    }
  }

  /**
   * This method returns the String between the double quotes.
   * If there are more than 2 double quotes, will return "".
   * 
   * @param userInput is the user input
   * @param checkIfCommand checks if it is working with another command
   * @return String if there are only 2 double quotes
   */
  private String getUserString(String userInput, boolean checkIfCommand) {
    int countQuot = 0;//counts number of double quotes
    for(int i = 0; i < userInput.length(); i++) {
      if(userInput.charAt(i)=='\"') countQuot++;
    }
    if(countQuot != 2 && !checkIfCommand) return null;
    //strTemp is temporary String array and used to get the user String
    String[] strTemp = userInput.split("\"", -1);
    if(strTemp.length == 3 && strTemp[0].equals("echo ")) return strTemp[1];
    if(checkIfCommand) {
      String temp = "";
      for(int i = 1; i < strTemp.length-1; i++) {
        temp += strTemp[i];
        if(i != strTemp.length-2) temp += "\"";
      }
      return temp;
    }
    check = false;
    return "";
  }

  /**
   * This method returns the substring of userInput after the first <.
   * 
   * @param userInput is the user input
   * @return substring of userInput after the first <
   */
  private String getOutFilePart(String userInput) {
    //fileTemp is temporary String array and used get file location and name
    String[] strTemp = userInput.split("\"", -1);
    return strTemp[strTemp.length-1].trim().replaceAll(" +", " ");
  }

  /**
   * This method returns path to file if path is inputed by user on File
   * location of command.
   * 
   * @param outTemp is a string array of the outFilePart split by white spaces
   * @return path to file location
   */
  private String seperatePath(String[] outTemp) {
    if(!outTemp[0].equals("")) {
      String path = "";//string of path
      //outTempTrim takes where the file location path is
      String outTempTrim = outTemp[outTemp.length-1];
      //removes the file part from path
      if(outTemp.length == 1) {
        String[] temp = outTemp[0].split(">");
        if(temp.length>0) outTempTrim = temp[temp.length-1];
      }
      boolean checkSlash = false;
      for(int i = 0; i < outTempTrim.length()-1; i++) {
        if(outTempTrim.charAt(i)=='/') checkSlash = true;
      }
      if(checkSlash) {
        String[] temp = outTempTrim.split("/");
        int i = 0;
        if(outTempTrim.charAt(0) == '/') {
          path = "/";
          i++;
        }
        for(; i < temp.length-1; i++) {
          path += temp[i];
          if(i != temp.length-2) path += "/";
        }
        return path;//returns path
      }
    }
    return "";//returns "" if there is no path
  }

  /**
   * This method returns the name of the file.
   * 
   * @param outTemp is a string array of the outFilePart split by white spaces
   * @param path is the file location path
   * @return name of the file
   */
  private String seperateFileName(String[] outTemp, String path) {
    //removes the path part from file name
    if(!outTemp[0].equals("")) {
      String outTempTrim = outTemp[outTemp.length-1];
      if(outTemp.length == 1) {
        String[] temp = outTemp[0].split(">");
        if(temp.length>0)outTempTrim = temp[temp.length-1];
      }
      boolean checkSlash = false;
      for(int i = 0; i < outTempTrim.length()-1; i++) {
        if(outTempTrim.charAt(i)=='/') checkSlash = true;
      }
      //if path exists then it is split by / and returns the last path of array
      if(checkSlash && !path.equals("")) {
        String[] temp = outTemp[outTemp.length-1].split("/");
        return temp[temp.length-1];//returns file name from path
      }
      return outTempTrim;//returns outTempTrim if path does not exist
    }
    return "";//returns "" if no file name
  }

  /**
   * This method returns the number of >s.
   * 
   * @param userInput is a user input string
   * @param checkIfCommand checks if it is working with another command
   * @return number of >s
   */
  private int countBrac(String userInput, boolean checkIfCommand) {
    String temp = "";
    if(checkIfCommand) {
      String[] temp1 = userInput.split("\"", -1);
      temp = temp1[temp1.length-1].trim().replaceAll(" +", " ");;
    }else {
      temp = userInput;
    }
    int count = 0;//number of >s
    for(int i = 0; i < temp.length();i++) {
      if(temp.charAt(i)=='>')count++;
    }
    return count;//returns count
  }

  /**
   * This method appends or overwrite the data of File with String.
   * 
   * @param str is the String inputed by user
   * @param brac is the integer counting number of >s
   * @param path is the file location path
   * @param fileName is the name of File
   */
  private void runOutput(String str, String brac, String path, String fileName){
    Directory dir = system.getCurrentNode();
    if(!path.equals("")) dir = system.getDirectory(path);
    if(dir != null) {
      //if file exists append or overwrite String to it
      if(dir.getFile(fileName) != null) {
        if(brac.equals(">>")) {
          String temp = dir.getFile(fileName).getData();
          dir.getFile(fileName).setData(temp+str);
        }else {
          dir.getFile(fileName).setData(str);
        }
      }
      //else makes new file with String in it
      else {
        if(containsIllegalCharacters(fileName)) {
          File newFile = new File(str, fileName);
          dir.addFile(newFile, fileName);
        }else {
          errorHandler.invalidFileName();//illegal file name error
        }
      }
    }else {
      errorHandler.dirNotFound();//directory not found error
    }
  }
  /**
   * Checks for illegal characters within str
   * 
   * @param str string to be checked
   * @return True iff str contains only legal characters
   */
  private boolean containsIllegalCharacters(String str) {

    //Allowed characters
    Pattern p = Pattern.compile("[a-zA-Z0-9-+:;,=_]*");
    Matcher m = p.matcher(str);
    boolean b = m.matches();

    //True if there is not any non-allowed character in dir name
    return b;
  }
  /**
   * returns the documentation of echo
   * 
   * @return echo documentation
   */
  @Override
  public String toString() {
    //echo's documentation
    String manual = "echo STRING [> or >> OUTFILE]\n"
        + "STRING is surrounded by double quotation marks.\n"
        + "Prints to shell if OUTFILE not provided, if > and OUTFILE,\n"
        + "overwrites OUTFILE with STRING, if >> then append STRING\n"
        + "to OUTFILE.\n";

    return manual;
  } 

}
