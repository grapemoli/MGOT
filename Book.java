//Book.java
//grace nguyen
//april 02, 2022

import java.io.*;

abstract class Book implements Serializable {
  //attributes
  protected String title;
  
  //constructors
  public Book(String title) {
    this.title = title;
  } //end constructor
  
  //getter and setters 
  public String getTitle() {
    return(this.title);    
  } //end getTitle

  public void setTitle(String title) {
    this.title = title;
  } //end setTitle

} //end class def
