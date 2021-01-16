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
 * Represents the command that allows the user to copy
 * files and directories from one path location to another.
 * @author Jesse Yao
 */
public class Copy extends Command {
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
   * Constructs a Copy object.
   * @param system the file system the command interacts with
   * @param errorHandler the error handler the command uses to show errors
   */
  public Copy(FileSystemShell system, ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
    this.system = system;
    commandName = "cp";
  }

  /**
   * Returns the name of the command, as used by the user.
   * @return the name of the command
   */
  @Override
  public String getCommandName() {
    return commandName;
  }

  /**
   * Copies the file/directory in the path in str[1] to the path in
   * str[2]. If str.length != 3 or the paths are invalid, give an
   * error and return without copying anything.
   * @param str the list of arguments (including command name) given
   *            by the user
   */
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
    copy(str[1], str[2]);
  }

  /**
   * Copies the file/directory referred to by oldPath to the location
   * referred to by newPath. If either path is invalid, give an
   * error and return without copying anything.
   * @param oldPath the location to copy from
   * @param newPath the location to copy to
   */
  private void copy(String oldPath, String newPath) {
    File oldFile = system.getFile(oldPath);
    Directory oldDir = system.getDirectory(oldPath);
    if (oldFile != null) {
      copyFile(oldPath, newPath);
    } else if (oldDir != null) {
      copyDirectory(oldPath, newPath);
    } else {
      errorHandler.dirOrFileNotFound();
    }
  }
  
  /**
   * Copies the file referred to by oldPath to the location
   * referred to by newPath. If either path is invalid, give an
   * error and return without copying anything.
   * @param oldPath the location to copy from
   * @param newPath the location to copy to
   */
  private void copyFile(String oldPath, String newPath) {
    File oldFile = system.getFile(oldPath);
    File newFile = system.getFile(newPath);
    Directory newDir = system.getDirectory(newPath);
    if (newFile != null) {
      // copy contents to existing file
      copyFileToFile(oldFile, newFile);
    } else if (newDir != null) {
      // copy contents to a file of same name inside newDir
      // creates new file if not already there
      if (newDir.getFile(oldFile.getName()) == null) {
        newDir.addFile("", oldFile.getName());
      }
      newFile = newDir.getFile(oldFile.getName());
      copyFileToFile(oldFile, newFile);
    } else if (newPath.endsWith("/")) {
      // if newPath unambiguously refers to a directory that doesn't
      // exist, give an error
      if (!ErrorHandler.checkIfErrorOccurred()) { // don't create more errs
        errorHandler.dirNotFound();
      }
    } else { // newPath refers to a new file
      // copy contents to new file, if path is valid
      system.createFile(newPath, oldFile.getData());
    }
  }
  
  /**
   * Copies the directory referred to by oldPath to the location
   * referred to by newPath. If either path is invalid, give an
   * error and return without copying anything.
   * @param oldPath the location to copy from
   * @param newPath the location to copy to
   */
  private void copyDirectory(String oldPath, String newPath) {
    Directory oldDir = system.getDirectory(oldPath);
    File newFile = system.getFile(newPath);
    Directory newDir = system.getDirectory(newPath);
    if (newFile != null) {
      // can't copy directory to a file
      errorHandler.invalidDest();
    } else if (newDir != null) {
      // try to copy directory into a directory of same name inside newDir
      // create directory if it doesn't already exist
      boolean destMissing = newDir.getDirectory(oldDir.getName()) == null;
      if (destMissing) {
        newDir.addDirectory(oldDir.getName());
      }
      Directory destination = newDir.getDirectory(oldDir.getName());
      if (destination != null) {
        if (checkCopyDirs(oldDir, destination)) {
          copyDirToDir(oldDir, destination);
        } else if (destMissing) {
          // cancel operation, remove destination directory 
          // if it wasn't there originally
          newDir.removeDirectory(oldDir.getName());
        }
      }
    } else { // newPath refers to a new directory
      system.createDirectory(newPath);
      // check if operation was successful
      if (system.getDirectory(newPath) != null) {
        Directory destination = system.getDirectory(newPath);
        if (checkCopyDirs(oldDir, destination)) {
          copyDirToDir(oldDir, destination);
        } else {
          system.getDirectory(destination.getParent())
          .removeDirectory(destination.getName());
        }
      }
    }
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
   * Copies the contents of oldFile to newFile.
   * @param oldFile the File object to copy the contents from.
   * @param newFile the File object to copy the contents to.
   */
  private void copyFileToFile(File oldFile, File newFile) {
    newFile.setData(oldFile.getData());
  }

  /**
   * If oldDir can be copied into newDir (oldDir != newDir and oldDir
   * is not a parent of newDir), returns true. If not, displays the
   * appropriate error and returns false.
   * @param oldDir the directory to be copied into newDir. Cannot be null.
   * @param newDir the destination of the copying operation. Cannot be null.
   * @return whether oldDir can be copied into newDir.
   */
  private boolean checkCopyDirs(Directory oldDir, Directory newDir) {
    if (oldDir == newDir) {
      errorHandler.dirsAreSame();
      return false;
    } else if (isParentOf(oldDir, newDir)) {
      errorHandler.dirIsParent();
      return false;
    }
    return true;
  }

  /**
   * Recursively copies the contents of directory oldDir into newDir.
   * @param oldDir the directory to be copied into newDir. Cannot be null.
   * @param newDir the destination of the copying operation.
   *               Cannot be null or a child of oldDir.
   */
  private void copyDirToDir(Directory oldDir, Directory newDir) {
    // copy all subdirectories
    for (String subDirName: oldDir.getDirectoryNames()) {
      if (newDir.getDirectory(subDirName) == null) {
        newDir.addDirectory(subDirName);
      }
      // if new directory is successfully made or exists
      if (newDir.getDirectory(subDirName) != null) {
        copyDirToDir(oldDir.getDirectory(subDirName),
            newDir.getDirectory(subDirName));
      }
    }
    // copy all files
    for (String fileName: oldDir.getFileNames()) {
      if (newDir.getFile(fileName) == null) {
        newDir.addFile("", fileName);
      }
      // if new file is successfully made or exists
      if (newDir.getFile(fileName) != null) {
        copyFileToFile(oldDir.getFile(fileName), newDir.getFile(fileName));
      }
    }
  }

  /**
   * Returns the documentation for this command.
   * @return the documentation for this command
   */
  @Override
  public String toString() {
    //cp's documentation
    String manual = "cp OLDPATH NEWPATH\n" +
        "Copy item OLDPATH to NEWPATH. If NEWPATH is\n" +
        "a directory, copy the item into the directory.\n" +
        "If OLDPATH is a directory, recursively copy\n" +
        "the contents.";
    return manual;
  }
}
