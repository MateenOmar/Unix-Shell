package driver;
/**
 * Remove Command Object
 * Removes Directory objects from FileSystem
 * 
 * 
 * @author Kevin Fernandes
 *
 */
public class Remove extends Command{

  /** Stores the name of the command */
  private String commandName;
  /** The current FileSystem being interacted with */
  private FileSystemShell system;
  /** Used to handler errors */
  private ErrorHandler errorHandler;

  /**
   * Construct a Command object
   */
  public Remove(FileSystemShell system, ErrorHandler err)
  {
    this.system = system;
    this.errorHandler = err;
    this.commandName = "rm";
  }
  /**
   * Gets the name of the command
   * 
   * @return usable name of command
   */
  public String getCommandName() {
    return commandName;
  }

  /**
   * removes the DIR from the file system.  
   * This also removes all the children of DIR (i.e.  it acts recursively).
   * 
   * @param str is the user input trimmed and split into a string array
   */
  public void run(String[] str) 
  {
    if (str.length != 2)
    {
      if (str.length < 2) errorHandler.notEnoughArguments();
      if (str.length > 2) errorHandler.tooManyArguments();
      return;
    }
    Directory toDel = system.getDirectory(str[1]);
    if (toDel == null)
    {
      errorHandler.dirNotFound();
      return;
    }
    else if (toDel.getParent() == null)
    {
      errorHandler.delRoot();
      return;
    }
    Directory parent = system.getDirectory(toDel.getParent());
    parent.removeDirectory(toDel.getName());
    return;
  }

  /**
   * Documentation for each command
   * 
   * @return ""
   */
  @Override
  public String toString() {
    return "rm DIR\n"
        + "Removes the DIR from the file system.  This also removes all\n"+
        "the children of DIR (i.e.  it actsrecursively).";
  }


}
