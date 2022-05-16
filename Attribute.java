//Attribute.java
//grace nguyen
//march 10, 2022

import java.io.*;

abstract class Attribute implements Serializable {
  //attributes 
  Double status;
  String name;

  public static void main(String[] args) {
    //class testing
    //Attribute grace = new Attribute("Happy", 5.0);
    //grace.increment(0.01);
    //System.out.println(grace.getStatus());
  } //end main
  
  
  //constructor
  public Attribute(String name) {
    this.name = name;
  } //end constructor

  public Attribute(String name, double status) {
    this.name = name;
    this.status = status;
  } //end constructor
  
  
  //getters and setters
  public String getName() {
    return(this.name);
  } //end getName

  public double getStatus() {
    return(this.status);
  } //end getStatus

  
  //abstract classes
  public abstract void setStatus(Double status);
  public abstract void increment();
  public abstract void increment(double amount);
  public abstract void decrement();
  public abstract void decrement(double amount);
  
} //end class def
