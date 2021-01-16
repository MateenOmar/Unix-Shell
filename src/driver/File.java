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
 * File that can store data as a String
 * 
 * @author Kevin Fernandes
 * @version 20/11/10
 */
public class File {
  /**
   * data stored within the file (as a String)
   */
  private String data;
  /**
   * name of file
   */
  private String name;

  /**
   * Construct a file with given name and data stored
   * 
   * @param data data to be stored as string
   * @param name name of file
   */
  public File(String data,String name)
  {
    this.data = data;
    this.name = name;
  }

  /**
   * Set data to given value (old data is lost)
   * 
   * @param data data to be set
   */
  public void setData(String data)
  {
    this.data = data;
  }

  /**
   * Get data from file
   * 
   * @return data from file
   */
  public String getData()
  {
    return data;
  }

  /**
   * Get the name of the file
   * 
   * @return name of file
   */
  public String getName() {
    return name;
  }

}