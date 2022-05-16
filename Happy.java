//Happy.java
//grace nguyen
//march 10, 2022

import java.io.*;

public class Happy extends Attribute implements Serializable{
  public static void main(String[] args){
    //class testing
    Happy grace = new Happy(0);
    grace.decrement();
    System.out.println(grace.getStatus());
    grace.setStatus(100D);
    System.out.println(grace.getStatus());
  } //end main

  
  //constructors
  public Happy() {
    super("Happy Meter", 5d);
  } //end constructor

  public Happy(double status) { 
    super("Happy Meter", status);
  } //end construcotr
  
  
  //methods meant to be used with other classes (Plant.java)
  public Boolean isDead() {
    Boolean isDead = false;
    if(this.status.equals(0)) {
      isDead = true;
    } //end if
    return(isDead);
  } //end isDead

  public Double dailyMoney() {
    //returns the money that the plant 'gives' the owner
    //based on the happy meter
    //note: there is no negative money values
    //note: exponential growth (slower than quadratic)
    if(this.status < 1D) {
      return(0D);
    }else{
      double money = java.lang.Math.log10(this.status);
      money = money * 0.75;
      return(money);
    } //end if-else 
  } //end dailyMoney 

  
  //implement inherited abstract classes
  public void setStatus(Double status) {
    if(status < 0D) {
      this.status = 0D;
    }else{
      this.status = status;
    } //end if-else
  } //end setStatus
    
  public void decrement() {
    if(this.status >= 1D) {
      this.status--;
    }else{
      this.status = 0D;
    } //end if
  } //end decrement

  public void decrement(double amount) {
    //polymorphic behavior
    if(this.status >= amount) {
      this.status = this.status - amount; 
    }else{
      this.status = 0D;
    } //end if
  } //end decrement

  public void increment() {
    this.status++;
  } //end increment

  public void increment(double amount) {
    //polymorphic behavior
    //no max on happy status
    this.status = this.status + amount;
  } //end increment
  
} //end class def
