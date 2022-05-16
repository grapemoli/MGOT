//Water.java
//grace nguyen
//march 10, 2022

import java.io.*;

class Water extends Attribute implements Serializable {
  //attributes
  int daysOverwatered;
  int daysUnderwatered;
  double waterConsumption;

  public static void main(String[] args) {
    //class testing
    Water grace = new Water(1D);
    for(int i = 0; i < 10; i++) {
      grace.dailyWaterConsumption();
      System.out.println(grace.getDaysUnderwatered());
      System.out.println(grace.getStatus());
      System.out.println(grace.isUnderwatered());
    }
  } //end main
  
  
  //constructors
  public Water() {
    super("Water Meter", 5D);
    this.daysOverwatered = 0;
    this.daysUnderwatered = 0;
    this.waterConsumption = 1D;
  } //end constructor

  public Water(double waterConsumption) {
    super("Water Meter", 5D);
    this.daysOverwatered = 0;
    this.daysUnderwatered = 0;
    this.waterConsumption = waterConsumption;
  } //end constructor

  public Water(double waterConsumption, Double status) {
    super("Water Meter", status);
    this.daysOverwatered = 0;
    this.daysUnderwatered = 0;
    this.waterConsumption = waterConsumption;
  } //end constructor

  
  //getters and setters
  public void setWaterConsumption(double waterConsumption) {
    this.waterConsumption = waterConsumption;
  } //end setWaterConsumption

  public double getWaterConsumption() {
    return(this.waterConsumption);
  } //end getWaterConsumption

  public int getDaysOverwatered() {
    return(this.daysOverwatered);
  } //end getDaysOverwatered

  public int getDaysUnderwatered() {
    return(this.daysUnderwatered);
  } //end getDaysUnderwatered
  
  
  //boolean methods; checking statuses
  public boolean isUnderwatered() {
    //return true if the days underwatered is greater than 7 days
    boolean underwatered = false;
    if(this.daysUnderwatered >= 7) {
      underwatered = true; 
    } //end if
    return(underwatered);
  } //end isUnderwatered
  
  public boolean hasRootRot() {
    //return true if the days overwatered is greater than 7 days
    boolean rootRot = false;
    if(this.daysOverwatered >= 7) {
      rootRot = true;
    } //end if
    return(rootRot);
  } //end hasRootRot

  
  //passage-of-time methods
  //i.e. methods meant to be used to show time has passed
  public void checkWater() {
    //increase overwatered if status is at 7-10
    //increase underewated if status is 0-3
    if(this.status > 7) {
      this.daysOverwatered++;
    }else{
      this.daysOverwatered = 0;
    } //end if-else

    if(this.status < 3) {
      this.daysUnderwatered++;
    }else{
      this.daysUnderwatered = 0;
    } //end if-else
  } //end dailyWaterCheck

  public void dailyWaterConsumption() {
    //plants consume water a little bit everyday
    this.decrement(this.waterConsumption);
  } //end dailyWaterConsumption
  
  
  //action methods
  public void water() {
    this.status = 10d;
  } //end water
  
  
  //implement abstract classes
  public void setStatus(Double status) {
    if(status > 10D){
      this.status = 10D;
    }else if(status < 0D){
      this.status = 0D;
    }else{
      this.status = status;
    } //end if-else
  } //end setStatus

  public void decrement() {
    if(this.status >= 1d) {
      this.status--;
    }else{
      this.status = 0d;
    } //end if-else
  } //end decrement

  public void decrement(double amount) {
    //polymorphic behavior that takes into account
    //the bounds of the water attribute
    if(this.status <= amount) {
      this.status = 0d;
    }else {
      this.status = this.status - amount;
    } //end if-else
  } //end decrement

  public void increment() {
    if(this.status >= 9d) {
      this.status = 10D;
    }else{
      this.status++; 
    } //end if-else
  } //end increment

  public void increment(double amount) {
    if(this.status < (10D - amount)) {
      this.status = this.status + amount;
    }else{
      this.status = 10D;
    } //end if-else
  } //end increment
  
  
} //end class def
