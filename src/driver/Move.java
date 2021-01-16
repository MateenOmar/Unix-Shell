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
 * Represents the command that allows the user to move
 * files and directories from one path location to another.
 * @author jesse
 */
public class Move extends Command {

  /**
   * The name of the command as used by the user.
   */
  private String commandName;
  /**
   * The file system this command interacts with.
   */
  private FileSystemShell system;
  /**
   * The error handler this command uses to show errors.
   */
  private ErrorHandler errorHandler;
  /**
   * The Copy object used to duplicate a file/directory from one
   * location to another.
   */
  private Copy copy;
  /**
   * The Remove object used to delete the original copy of a directory.
   */
  private Remove remove;

  /**
   * Constructs a ChangeDirectory object.
   * @param system the file system the command interacts with
   * @param errorHandler the error handler the command uses to show errors
   */
  public Move(FileSystemShell system, ErrorHandler errorHandler) {
    this.system = system;
    this.errorHandler = errorHandler;
    this.copy = new Copy(system, errorHandler);
    this.remove = new Remove(system, errorHandler);
    this.commandName = "mv";
  }

  /**
   * Returns the name of the command, as used by the user.
   * @return the name of the command
   */
  @Override
  public String getCommandName() {
    return commandName;
  }

  @Override
  public void run(String[] str) {
    if (str.length != 3) {
      if(str.length < 3) {
        errorHandler.notEnoughArguments();
      }
      else {
        errorHandler.tooManyArguments();
      }
      return;
    }
    
    // cannot move working dir/parent of working dir
    // cannot move dir to a location where it would replace a non-empty dir
    // don't do anything if source and dest files are the same
    if (checkIfMovingWorkingDirOrParent(str) ||
        checkIfDestIsNonEmpty(str) ||
        checkIfSourceAndDestIsSame(str)) {
      return;
    }
    
    
    String[] copyArgs = {"cp", str[1], str[2]};
    copy.run(copyArgs);
    // only delete original copy if copy operation is successful
    if (!ErrorHandler.checkIfErrorOccurred()) {
      if (system.getDirectory(str[1]) != null) { // does str[1] lead to a dir?
        String[] removeArgs = {"rm", str[1]};
        remove.run(removeArgs);
      } else { // ...or does str[1] lead to a file?
        system.removeFile(str[1]);
      }
    }
  }
  
  /**
   * Check whether the move operation with arguments given by str would result
   * in the current working directory or a parent of it being moved
   * @param str array of arguments of length 3
   * @return true iff the move operation with the given arguments would result
   *         in the working directory or a parent of it being moved
   */
  private boolean checkIfMovingWorkingDirOrParent(String[] str) {
    Directory oldDir = system.getDirectory(str[1]);
    if (oldDir != null && (oldDir == system.getCurrentNode() ||
        isParentOf(oldDir, system.getCurrentNode()))) {
      errorHandler.cannotMoveWD();
      return true;
    }
    return false;
  }
  
  /**
   * Check whether the move operation with arguments given by str would result
   * in a directory moving into a location with a non-empty directory 
   * with the same name as the source
   * @param str array of arguments of length 3
   * @return true iff the move operation with the given arguments would result
   *         in a directory moving into a location with a non-empty directory
   *         with the same name as the source
   */
  private boolean checkIfDestIsNonEmpty(String[] str) {
    Directory oldDir = system.getDirectory(str[1]);
    Directory newDir = system.getDirectory(str[2]);
    if (oldDir != null && newDir != null) {
      Directory dest = newDir.getDirectory(oldDir.getName());
      if (dest != null && 
          !(dest.getDirectories().isEmpty() && dest.getFiles().isEmpty())) {
        errorHandler.destIsNonEmpty();
        return true;
      }
    }
    return false;
  }
  
  /**
   * Check whether the move operation with arguments given by str would result
   * in a file being moved to its own location
   * @param str array of arguments of length 3
   * @return true iff the move operation with the given arguments would result
   *         in a file being moved back to its own location
   */
  private boolean checkIfSourceAndDestIsSame(String[] str) {
    File oldFile = system.getFile(str[1]);
    File newFile = system.getFile(str[2]);
    Directory newDir = system.getDirectory(str[2]);
    if (oldFile != null) {
      if (newFile != null && oldFile == newFile) {
        return true;
      } else if (newDir != null &&
          oldFile == newDir.getFile(oldFile.getName())) {
        return true;
      }
    }
    return false;
  }
    
  /**
   * Returns true iff dir1 is a parent of dir2.
   * @param dir1 the directory to check whether dir2 is a child of.
   *        Cannot be null.
   * @param dir2 the directory to check whether dir1 is a parent of.
   *        Cannot be null.
   * @return whether dir1 is a parent of dir2
   */
  private boolean isParentOf(Directory dir1, Directory dir2) {
    // repeatedly jump up the file hierarchy until a parent
    // matches dir1 or we hit root
    Directory parent = dir2;
    while (parent != system.getRoot()) {
      parent = system.getDirectory(parent.getParent());
      if (dir1 == parent) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns the documentation for this command.
   * @return the documentation for this command
   */
  @Override
  public String toString() {
    //cp's documentation
    String manual = "mv OLDPATH NEWPATH\n" +
        "Move item OLDPATH to NEWPATH. If NEWPATH is\n" +
        "a directory, move the item into the directory.\n";
    return manual;
  }
}
