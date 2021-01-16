package driver;

import java.util.Stack;

public interface FileSystemShell {

  public Directory getRoot();

  public Directory getCurrentNode();

  public void setCurrentNode(Directory currentNode);

  public void createDirectory(String path);

  public void createFile(String path, String data);

  public void removeFile(String path);

  public Directory getDirectory(String path);

  public Directory getDirectory(String[] paths);

  public File getFile(String path);

  public File getFile(String[] paths);

  public Stack<String> getDirectoryStack();

  public void setDirectoryStack(Stack<String> directoryStack);

  public String[] getProcessedPath(String str);

}
