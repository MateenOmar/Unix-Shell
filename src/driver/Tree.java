package driver;
import java.util.*;

/**
 * Outputs the FileSystem as a tree of
 * directory and files
 * 
 * @author Kevin Fernandes
 *
 */
public class Tree extends Command{
  /**
   * File system to get current node from
   */
  private FileSystemShell sys;
  /**
   * calling name of command
   */
  private String commandName;
  /**
   * Error Handler to send errors to
   */
  private ErrorHandler err;

  /**
   * Construct Tree command object
   * 
   * @param sys FileSystem to interact with
   * @param err Error Handler to use
   */
  public Tree(FileSystemShell sys, ErrorHandler err)
  {
    this.sys = sys;
    this.err= err;
    this.commandName = "tree";
  }

  /**
   * Get usable name of command
   * 
   * @return name of command
   */
  public String getCommandName() {
    return commandName;
  }

  /**
   * tree
   * starting from the root directory, displays the entire
   *  filesystem as a tree
   * 
   * @param str user given call to tree
   * @param outFile is the substring of the input after first >
   */
  public void run(String[] str, String outFile)
  {
    if (str.length != 1){
      err.tooManyArguments();
      return;
    }
    Output output = new Output();
    Stack<Object> tree = new Stack<Object>();
    Object topObject;
    Directory tempDir;
    File tempFile;
    tree.push(sys.getRoot());
    int numTabs = 0;
    String outputString = "/";
    do
    {
      topObject = tree.pop();
      if (topObject instanceof Directory)
      {
        tempDir = (Directory)topObject;
        for (int i = 0; i < numTabs; i++) outputString += "\t";
        outputString += tempDir.getName() + "\n";
        numTabs++;
        tree.push("!");
        for (Directory d: tempDir.getDirectories()) tree.push(d);
        for (File f: tempDir.getFiles()) tree.push(f);
      }
      else if (topObject instanceof File)
      {
        tempFile = (File)topObject;
        for (int i = 0; i < numTabs; i++) outputString += "\t";
        outputString += tempFile.getName()+ "\n";
      }
      else
      {
        numTabs--;
      }
    }while(tree.size() > 0); 
    output.outFile(outputString, outFile, sys, err);
  }


  /**
   * Documentation for tree
   * 
   * @return tree Documentation
   */
  public String toString() 
  {
    String manual = "tree\n"
        + "starting from the root directory, displays "
        + "the entire filesystem as a tree";

    return manual;
  }


}
