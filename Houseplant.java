//Houseplant.java
//grace nguyen
//april 16, 2022

import java.io.*;
import java.util.*;

public class Houseplant extends Plant implements Serializable {
  //attributes
  String nickname;
  double potDiameter;

  public static void main(String[] args) {
    //class testing
    Houseplant grace = new Houseplant("Cactus", 7, "grakamoli");
    
    for(int i = 0; i < 18; i++)  {
      grace.daily(i);
      System.out.println(grace.getDimensionsFeet());
      System.out.println(grace.getMaxDimensionsFeet()); 
    } //end for

    //test repotting
   
    
    System.out.println(String.valueOf(grace.getHappinessMeter()));
    System.out.println(String.valueOf(grace.getPotDiameterInches()));
    //grace.repot(3, "WINTER"); 
    //System.out.println(String.valueOf(grace.getHappinessMeter()));
  } //end main
  

  //constructors
  public Houseplant() {
    super("Cactus", 7);
    this.nickname = "";
    this.potDiameter = 0d;
    this.initPot("Cactus", 7);
  } //end constructor

  public Houseplant(String category, int line) {
    super(category, line);
    this.nickname = "";
    this.potDiameter = 0d;
    this.initPot(category, line);
  } //end constructor

  public Houseplant(String category, int line, String nickname) {
    super(category, line);
    this.nickname = nickname;
    this.potDiameter = 0d;
    this.initPot(category, line);
  } //end constructor

    public Houseplant(String category, int line, String nickname, double waterRate) {
      super(category, line, waterRate);
      this.nickname = nickname;
      this.potDiameter = 0d;
      this.initPot(category, line);
    } //end constructor
    
  
  //init methods
  public void initPot(String category, int line) {
    //read file to init pot radius attribute
    String file = "plants" + category + ".dat";

    try{
      File readFile = new File(file);
      Scanner scanner = new Scanner(readFile);
      int counter = 0;
      boolean keepGoing = true;

      while(keepGoing == true) {
        counter++;
        String data = scanner.nextLine();

        //parse info
        if(line == counter) {
          //get pot radius
          String plantLine = data;
          String[] splitString = plantLine.split("#");

          //set to attributes
          this.potDiameter = Double.valueOf(splitString[9]);
          this.potDiameter = this.potDiameter * 0.01; //cm to m
          keepGoing = false;          
        } //end if

        //end, in case plant information not found
        if(!scanner.hasNextLine()) {
          keepGoing = false;
          System.out.println("Plant info not found.");
        } //end if
      } //end while
    }catch(Exception e){
      //something went wrong, print the needed info
      System.out.println(e.getMessage());
      System.out.println(file + " and plant info not found.");
    } //end exception handler
  } //end initPot


  //getters and setters
  public String getNickname() {
    return(this.nickname);
  } //end getNickName

  public void setNickname(String nickname) {
    this.nickname = nickname;
  } //end setNickname

  public double getPotDiametr() {
    return(this.potDiameter);
  } //end getPotRadius

  public void setPotDiameter(double potDiameter) {
    this.potDiameter = potDiameter;
  } //end setPotRadius

  public double getPotDiameter() {
    return(this.potDiameter);
  } //end getPotDiameter

  public double getPotDiameterFeet() {
    double inFeet = this.potDiameter * 3.28;
    return(inFeet);
  } //end getPotRadiusFeet

  public double getPotDiameterInches() {
    double inInches = this.potDiameter * 39.37;
    return(inInches);
  } //end getPotDiameterInches


  //boolean methods; check state of being
  public boolean needRepot() {
    //repotting should occur every 1-1.5 yrs
    //but for this simulation, repotting must occur when:
    //height is 6x greater than diameter OR width is 4x greater than diameter
    double height = this.currentHeight;
    double width = this.currentWidth;
    double diameter = this.potDiameter;
    
    boolean repot = false;
    
    //check height
    //anecdotal and observational: plants can maintain
    //high quality of lives with height up to 5 times the diameter
    if((diameter * 5) < height) { 
      repot = true;  
    } //end if

    //check width
    //anecdotal and observational: plants can maintain 
    //height quality of lives with height up to 4 times the diameter
    if((diameter * 4) < width ) {
      repot = true;
    } //end if

    //and return
    return(repot);
  } //end needRepot

  public double potEffect() {
    //quantify effect of a cramped roots system
    double height = this.currentHeight;
    double width = this.currentWidth;
    double diameter = this.potDiameter;
    
    //ratio of height to pot diameter range takes up 50% of effect
    //ratio of width to pot diameter range takes up 50% of effect
    double heightRatio = height/(5*diameter);
    double widthRatio = width/(4*diameter);
    
    if(heightRatio < 1){
      heightRatio = 0;  
    }else{
      heightRatio = heightRatio/2;  
    } //end if

    if(widthRatio < 1){
      widthRatio = 0;
    }else{
      widthRatio = widthRatio/2;  
    } //end if
    return(heightRatio + widthRatio);
  } //end potEffect

  public void dailyPot() {
    //day-to-day effects of a pot
    boolean repot = this.needRepot();

    if(repot == true) {
      //repot is needed... roots system is cramped
      //therefore, decrease happiness meter 
      //based on how cramped the roots system is
      double effect = this.potEffect();
      this.setHappinessMeter(false, effect);
    } //end if
  } //end dailyPot

  public boolean repot(double newPot, String season) {
    //setter but with restrictions on pot diameter
    //note: new pot diameter is in METERS!
    double height = this.currentHeight;
    double width = this.currentWidth;
    double currentPot = this.potDiameter;
    boolean canRepot = false;

    //check that pot is big enough to hold plant 
    double heightRange = newPot * 5;
    double widthRange = newPot * 4;
    
    if(height < heightRange) {
      canRepot = true;
    } //end if
    
    if(width < widthRange) {
      canRepot = true;
    } //end if

    //repot if true; halve the happiness meter if the season is winter
    if(canRepot == true) {
      String name = this.nickname;
      if(this.nickname.equals("")) {
        name = this.commonName;  
      } //end if

      double newPotInches = newPot*39.37;
      double oldPotInches = this.getPotDiameterInches();
      System.out.format(name + " thanks you for repotting from %.2f inches to %.2f inches! Grass-cias.", oldPotInches, newPotInches);
      this.setPotDiameter(newPot);  
      
      if(season.equals("WINTER")) {
        System.out.println("Repotting in WINTER: happiness decreased by 50%!");
        double status = this.getHappinessMeter();
        double decrementBy = status/2;
        this.setHappinessMeter(false, decrementBy);
      } //end if
    }else{
      System.out.println("Repotting is not possible. The new pot is too small!");  
    } //end if
    return(canRepot);
  } //end repot


  //abstract methods def
  public void daily(int day) {
    //mini-main that holds all the daily-actions together
    if(day%4 == 0) {
      this.grow(day); //growth occurs every other day
    } //end if
    
    this.changeSeason(day); 
    this.consumeWater(); 
    this.waterAilments(); //check water status
    this.dailyPot();
    this.age();
    this.dailyHappiness();
    this.continueDiseases(); //continue new and old diseases
  } //end daily

} //end class def
