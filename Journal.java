//Journal.java
//grace nguyen
//april 4, 2022

import java.io.*;
import java.util.*;

public class Journal extends Book implements Serializable {
  //attribtes
  Queue<String> content;
  String currentDayLog;
  Integer day;

  public static void main(String[] args) {
    Journal grace = new Journal();
    grace.logWater("grakamoli");
    grace.logFertilizer("grakamoli");
    grace.nextDay();
    grace.logFungicide("grakamoli");
    grace.nextDay();
    grace.logCure("grakamoli", "mealybugs");
    grace.nextDay();
    grace.nextDay();
    grace.listContent();
  } //end main

  
  //constructors
  public Journal() {
    super("Journal");
    content = new LinkedList<String>();
    currentDayLog = "";
    day = 1;
  } //end constructor
  
  
  //getters and setters
  public String getCurrentDayLog() {
    return(this.currentDayLog);
  } //end getLog
  
  public Integer getDay() {
    //get the day attribute
    return(this.day);
  } //end getDay
  
  
  //listing method
  public void listContent() {
    //print all of the content attributes
    //take into account that journalDate works properly ONLY if the current day is greater than 3
    int journalDate = 0;
    if(this.day > 3) {
      journalDate = this.day - 3;
    }else{
      journalDate = 1;
    } //end if

    for(String dayLog : this.content) {
      System.out.println("# Day " + journalDate);
      journalDate++;
      System.out.println(dayLog);
    } //end for-each

    //and print the current day!
    System.out.println("# Day " + this.day +  "\n" + this.currentDayLog);
  } //end listContent
 

  //adder methods
  public void addLog(String action) {
    //add action to todays' log (currentDayLog)
    this.currentDayLog = this.currentDayLog + "- " + action + "\n";
  } //end addLog

  public void logFertilizer(String plantName) {
    //add fertilizer log to today's string
    String log = "Fertilized " + plantName;
    this.addLog(log);
  } //end logFertilizer

  public void logWater(String plantName) {
    //add water log to today's log
    String log = "Watered " + plantName;
    this.addLog(log);
  } //end logWater

  public void logInsecticide(String plantName) {
    //add insecticide log to today's log
    String log = "Treated " + plantName + " with insecticide";
    this.addLog(log);
    } //end logInsecticide

  public void logFungicide(String plantName) {
    //add fungicide log
    String log = "Treated " + plantName + " with fungicide";
  this.addLog(log);
  } //end logFungicide

  public void logRepot(String plantName) {
    String log = "Repotted " + plantName;
    this.addLog(log);
  } //end logRepot

  public void logCure(String plantName, String diseaseName) {
  String log = plantName + " was cured of " + diseaseName;
  this.addLog(log);
  } //end logCure

  public void logBuy(String bought, String cost) {
    String log = bought + " was bought for $" + cost;
    this.addLog(log);
  } //end logBuy

  
  //passing-of-time methods
  public void nextDay() {
    //add total day log to content attribute
    this.content.add(this.currentDayLog);
    this.currentDayLog = "";

    //increase the day attribute by 1 to signify a new day
    this.day++;
    
    //get rid of the head if length is greater than 3
    //only storing the past 3 days' worth of information
    Integer length = this.content.size();
    if(length > 3) {
      this.content.poll();
    } //end if
  } //end nextDay
  
  
} //end class def
