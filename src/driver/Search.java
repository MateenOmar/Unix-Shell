package driver;

import java.util.Arrays;
/**
 * Searches for a given expresssion through a list of
 * given paths within the file system
 * 
 * @author Kevin Fernandes
 *
 */
public class Search extends Command{
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
   * Construct Search command object
   * 
   * @param sys FileSystem to interact with
   * @param err Error Handler to use
   */
  public Search(FileSystemShell sys, ErrorHandler err)
  {
    this.sys = sys;
    this.err= err;
    this.commandName = "search";
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
   * search
   * performs search operation on list of paths for files/directories[f|d]
   * that match a given expression
   * 
   * @param str using given call to search
   * @param outFile whether output is to be sent to console or a file
   */
  public void run(String[] str, String outFile)
  {
    Output output = new Output();
    if (str.length < 6 || !str[str.length-4].equals("-type") ||
        !str[str.length-2].equals("-name")) {
      err.invalidArgument();
      return;
    }
    boolean isDir = str[str.length-3].equals("d");
    if (!isDir && !str[str.length-3].equals("f")) {
      err.invalidArgument(str[str.length-3]);
      return;
    }
    String exp = str[str.length-1];
    exp = exp.replace("\"", "");
    String outString = doSearch(Arrays.copyOfRange(str, 1, str.length-4), isDir, exp);
    output.outFile(outString, outFile, sys, err);
  }

  /**
   * Performs search for given paths for exp on [d|f]
   * 
   * @param paths list of paths to search
   * @param isDir true if searching for dir, false for file
   * @param exp expression to search for
   * @return String of found and not found locations
   */
  private String doSearch(String[] paths, boolean isDir, String exp)
  {
    Directory temp;
    String[] names;
    String outString = "";
    for (int i = 0; i < paths.length; i++)
    {
      temp = sys.getDirectory(paths[i]);
      if (temp == null) {
        err.dirNotFound();
        return null;
      }
      names = temp.getFileNames();
      if (isDir) names = temp.getDirectoryNames();
      if (isIn(names, exp)) {
        outString += temp.getPath() + exp + "\n"; 
      }
      else {
        outString += "\"" + exp + "\" not found in " + temp.getPath() + "\n"; ; 
      }
    }
    return outString;
  }

  /**
   * checks whether exp exists within a given array
   * 
   * @param strs array to check against
   * @param exp expression to check for
   * @return true iff exp is in strs
   */
  private boolean isIn(String[] strs, String exp)
  {
    for (String s: strs)
    {
      if (exp.equals(s))
      {
        return true;
      }
    }
    return false;
  }

  /**
   * Documentation for search
   * 
   * @return search Documentation
   */
  public String toString() 
  {
    String manual = "search\n"
        + "Searches through list of paths for files or directories that match\n"
        + "the given expression";

    return manual;
  }
}
