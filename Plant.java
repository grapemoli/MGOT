//Plant.java
//grace nguyen
//april 4, 2022

import java.io.*;
import java.util.*;

abstract class Plant implements Serializable { 
  //attributes
  Water waterMeter;
  Happy happinessMeter;
  Disease diseases;
  double currentWidth;
  double currentHeight;
  String commonName;
  String latinName;
  int age;

  //more like constants, but attributes
  private double MAX_HEIGHT;
  private double MAX_WIDTH;
  private final double INITIAL_WIDTH;
  private final double INITIAL_HEIGHT;
  
  //comment out testing when static
  /*
  public static void main(String[] args) {
    //class testing
    Plant grace = new Plant("Cactus", 1, 1); 
    
    
    //testing time-based
    for(int i = 0; i < 10; i++) {
      grace.water();
      System.out.println("Day " + i);
      System.out.println("water: " + grace.getWaterMeter());
      System.out.println("happiness: " + grace.getHappinessMeter());
      grace.daily(i);
      System.out.println(grace.getDimensionsFeet());
    }
   
    System.out.println(grace.getMaxDimensionsFeet());
    grace.listDiseaseDescriptions();
    
    
    //seasonal testing
  } //end main
  */
 

  //constructors
  public Plant(String category, Integer line) {
    //init dimensions
    currentWidth = 0d;
    currentHeight = 0d;
    //set dimensions
    this.initDimensions(category, line);
    
    //classes
    waterMeter = new Water(); 
    diseases = new Disease();

    //constants
    this.INITIAL_HEIGHT = this.currentHeight;
    this.INITIAL_WIDTH = this.currentWidth;

    //make happiness meter random
    Random number = new Random();
    int num = number.nextInt(7);
    double status = Double.valueOf(num);
    status += 3d;
    happinessMeter = new Happy(status);

    //other
    this.age = 1;
  } //end constructor

  public Plant(String category, Integer line, double waterConsumption) {
    //init dimensions
    this.currentHeight = 0d;
    this.currentWidth = 0;
    //set dimensions
    this.initDimensions(category, line);

    //classes
    waterMeter = new Water(waterConsumption);
    diseases = new Disease();

    //constants
    this.INITIAL_HEIGHT = this.currentHeight;
    this.INITIAL_WIDTH = this.currentWidth;
    
    //ranomdize happiness meter
    Random number = new Random();
    int num = number.nextInt(7);
    double status = Double.valueOf(num);
    status += 3d;
    happinessMeter = new Happy(status);

    //other
    this.age = 1;
  } //end constructor

  
  //init method
  public void initDimensions(String category, Integer line) {
    //init the dimensions by parsing
    String file = "plants" + category + ".dat";
    
    try {
      File readFile = new File(file);
      Scanner scanner = new Scanner(readFile);
      int counter = 0;
      Boolean keepGoing = true;

      while(keepGoing.equals(true)) {
        counter++;
        String data = scanner.nextLine();

        //get plant info that was passed through function
        if(line.equals(counter)) {
          //get width and height dim (max size and size when bought)
          String plantLine = data;
          String[] splitString = plantLine.split("#");
         
          //set to the attributes
          this.latinName = String.valueOf(splitString[0]);
          this.commonName = String.valueOf(splitString[1]);
          this.MAX_HEIGHT = Double.valueOf(splitString[12]);
          this.MAX_WIDTH = Double.valueOf(splitString[13]);
          this.currentHeight = Double.valueOf(splitString[10]);
          this.currentWidth = Double.valueOf(splitString[11]);
          keepGoing = false;
        } //end if

        //end loop if requested plant info not found
        if(!scanner.hasNextLine()) {
          keepGoing = false;
          System.out.println("Plant info not found.");
        } //end if
      } //end while
    }catch(Exception e){
      //something went wrong. print needed information
      System.out.println(e.getMessage());
      System.out.println(file + " and plant info is not found.");
    } //end try-except
  } //end initDimensions
  

  //getters and setters
  public int getAge() {
    return(this.age);  
  } //end getAge

  public String getLatinName() {
    return(this.latinName);
  } //end latinName

  public String getCommonName() {
    return(this.commonName);  
  } //end getCommonName

  public double getHappinessMeter() {
    return(this.happinessMeter.getStatus());
  } //end getHappiness

  public void setHappinessMeter(boolean increment) {
    if(increment == true) {
      this.happinessMeter.increment(); 
    }else{
      this.happinessMeter.decrement(); 
    } //end if
  } //end setHappinessMeter

  public void setHappinessMeter(boolean increment, double amount) {
    //polymorphic behavior
    if(increment == true) {
      this.happinessMeter.increment(amount);
    }else{
      this.happinessMeter.decrement(amount);
    } //end if
  } //end setHappinessMeter

  public double getWaterMeter(){
    return(this.waterMeter.getStatus());
  } //end getWater

  public void setWaterMeter(boolean increment) {
    if(increment == true) {
      this.waterMeter.increment();      
    }else{
      this.waterMeter.decrement();
    } //end if
  } //end setWaterMeter

  public void setWaterMeter(boolean increment, double amount) {
    //polymorphic behavior for setting water meter
    if(increment == true) {
      this.waterMeter.increment(amount);
    }else{
      this.waterMeter.decrement(amount);
    } //end if
  } //end setWaterMeter

  public void setWaterConsumption(double amount) {
    this.waterMeter.setWaterConsumption(amount);
  } //end setWaterConsumption

  public double getWaterConsumption() {
    return(this.waterMeter.getWaterConsumption());
  } //end getWaterConsumption

  public double getCurrentWidth() {
    return(this.currentWidth);
  } //end currentWidth
  
  public double getCurrentHeight() {
    return(this.currentHeight);
  } //end currentHeight
  
  public double getMaxHeight() {
    return(this.MAX_HEIGHT);
  } //end getMaxHeight

  public double getMaxWidth() {
    return(this.MAX_WIDTH);
  } //end getMaxWidth

  public String getMaxDimensionsFeet() {
    Double h = this.MAX_HEIGHT;
    Double w = this.MAX_WIDTH;
    String dimensions = "Max Height: ";
    
    h = h*3.28;
    w = w*3.28;
    dimensions = dimensions + String.format("%.2f\nMax Width: ", h);
    dimensions = dimensions + String.format("%.2f", w);

    return(dimensions);
  } //end getMaxDimensionsFeet

  public String getDimensionsFeet() {
    //convert string meters to feet
    Double h = this.currentHeight;
    Double w = this.currentWidth;
    String dimensions = "Height (ft): ";

    h = h*3.28;
    w = w*3.28;
    dimensions = dimensions + String.format("%.2f\nWidth (ft): ", h);
    dimensions = dimensions + String.format("%.2f", w);

    return(dimensions);
  } //end getInFeet

  
  //Disease methods + branches of Disease methods
  public String[] getDiseases() {
    return(this.diseases.getDiseases());
  }
  
  public void setDisease(String disease, boolean hasDisease) {
    this.diseases.setStatus(disease, hasDisease);
  } //end setDisease
  
  public void listDiseaseDescriptions() {
    this.diseases.listAllDescriptions();  
  } //end listDiseaseDescriptions

  public void listFull() {
    this.diseases.listFull();
  } //end listFull

  public void listDiseases() {
    //list all diseases in alphabetical order
    int[] diseaseIndex = this.diseases.ailments();
    String[] allDiseases = this.diseases.getDiseases();
   
    //get length of diseases contracted
    int length = 0;
    for(int i = 0; i < diseaseIndex.length; i++) {
      if(diseaseIndex[i] != -1) {
        length++;
      } //end if
    } //end for

    //get an array of only diseases contracted 
    String[] contracted = new String[length];

    for(int i = 0; i < length; i++) {
      int currentIndex = diseaseIndex[i]; 
      contracted[i] =  allDiseases[currentIndex];
    } //end for
  
    //alphabetize
    Arrays.sort(contracted, String.CASE_INSENSITIVE_ORDER);

    //print all the diseases
    for(int i = 0; i < length; i++) {
      System.out.println("- " + contracted[i]);
    } //end for
  } //end listDisease

  public int[] ailments() {
    return(this.diseases.ailments());
  } //end ailments

  public double diseasesEffect() {
    double effect = this.diseases.effect();

    //increase the effect if extremely overwatered or underwatered
    int daysUnderwatered = this.waterMeter.getDaysUnderwatered();
    int daysOverwatered = this.waterMeter.getDaysOverwatered();
    
    if(daysUnderwatered > 14) {
      effect = effect * 2;
    } //end if

    if(daysOverwatered > 14) {
      effect = effect * 2;
    } //end if
    
    return(effect);

  } //end diseasesEffect

  public boolean hasDisease(String diseaseName) {
    return(this.diseases.hasDisease(diseaseName));
  } //end hasDisease

  //disease methods w/ contracting randomly 
  //should apply to infestations or buying from store
  public void contractRandomly() {
    //store-bought random contraction
    this.diseases.contractRandomly();
  } //end contractRandomly
  
  public void contractRandomly(String diseaseName) {
    //infestation for one disease
    this.diseases.contractRandomly(diseaseName);
  } //end contractRandomly
  
  public void contractRandomly(String diseaseName, boolean contaminated) {
    //infestation random chance of contraction: true means to include the
    //contamination diseases. 
    //else, only check for diseases that every plant is susceptible to
    this.diseases.contractRandomly(diseaseName, contaminated);
  } //end contractRandomly

  public boolean isContagious() {
    return(this.diseases.isContagious());
  } //end isContagious

  
  //Happy methods + branches of Happy method
  public double dailyMoney() {
    double money = this.happinessMeter.dailyMoney();
    return(money);
  } //end dailyMoney

  public boolean isDead() {
    //plant is dead if happiness status is 0...
    double happy = this.getHappinessMeter();
    boolean dead = false;
    
    if(happy <= 0d) {
      dead = true;
    } //end if
    return(dead);
  } //end isDead

  
  //Water methods + branches of Water methods
  public void waterAilments() {
    //water ailments: root rot and underwatered
    double water = this.getWaterMeter();
  
    //check the water meter for problems
    this.waterMeter.checkWater();
    boolean rootRot = this.waterMeter.hasRootRot();
    boolean underwatered = this.waterMeter.isUnderwatered();
    
    //if there are problems, set the right disease
    if(rootRot == true) {
      int daysOverwatered = this.waterMeter.getDaysOverwatered();
      
      //if first day, set to true
      if(daysOverwatered == 7) {
        this.setDisease("Root Rot", true);
      }//end if
    }else{
      //root rot false... set to false
      this.setDisease("Root Rot", false);
    } //end if

    if(underwatered == true) {
      int daysUnderwatered = this.waterMeter.getDaysUnderwatered();

      if(daysUnderwatered == 7) {
        this.setDisease("Underwatered", true);
      } //end if
    }else{
      this.setDisease("Underwatered", false);
    } //end if
  } //end waterAilments

  public void water() {
    this.waterMeter.water();
  } //end water

  
  //passage of time methods -- shows time has passed
  public void continueDiseases() {
    //time goes on, decrease the effect of the disease from the happiness meter
    this.diseases.continueDiseases();
    
    double effect = this.diseasesEffect();
    this.happinessMeter.decrement(effect);
  } //end continueDisease
  
  public void consumeWater() {
    this.waterMeter.dailyWaterConsumption();
  } //end consumeWater 
  
  public void grow(int day) {
    //growth modeled by rational function, x >= 0
    //requires: derivative of rational function
    //dependent variables: season and age
    String season = getSeason(day);
    
    //using quotient rule to get the derivative of a rational function
    double dNumeratorHeight = this.MAX_HEIGHT;
    double dNumeratorWidth = this.MAX_WIDTH;
    double heightRate = 0.01; //default values if plant has no limit on height/width growth
    double widthRate = 0.01;

    if(this.MAX_HEIGHT != 0d) {
      heightRate = ((this.age + this.INITIAL_HEIGHT)*(dNumeratorHeight)) - ((this.MAX_HEIGHT*this.age)*(1));
    heightRate = heightRate/((this.age + this.INITIAL_HEIGHT)*(this.age + this.INITIAL_HEIGHT));
    } //end if
    
    if(this.MAX_WIDTH != 0d) {
      widthRate = ((this.age + this.INITIAL_WIDTH)*(dNumeratorWidth)) - ((this.MAX_WIDTH*this.age)*(1));
      widthRate = widthRate/((this.age + this.INITIAL_WIDTH)*(this.age + this.INITIAL_WIDTH));
    } //end if
  
    //edit height and weight rate based on season
    if(season.equals("WINTER")){
      heightRate = heightRate * 0.01;
      widthRate = widthRate * 0.01;
    }else if(season.equals("FALL")){
      heightRate = heightRate * 0.125;
      widthRate = widthRate * 0.125;
    }else if(season.equals("SUMMER")){
      heightRate = heightRate * 0.25;
      widthRate = widthRate * 0.25;
    }else if(season.equals("SPRING")){
      heightRate = heightRate * 0.5;
      widthRate = widthRate * 0.5;
    }

    this.currentHeight = this.currentHeight + heightRate;
    this.currentWidth = this.currentWidth + widthRate;
  } //end grow
  
  public String getSeason(int day) {
    //account for years; each year is 122 days long
    if(day > 122) {
      day = day%122;
    } //end if

    if(day <= 30){
      return("SPRING"); 
    }else if(day <= 62){
      return("SUMMER");
    }else if(day <= 92){
      return("FALL");  
    }else if(day <= 122){
      return("WINTER");  
    }else{
      return(null);
    } //end if
  } //end getSeason

  public void changeSeason(int day) {
    //changing water consumption rate based on the season
    if(day > 122) {
      if(day == 123) {
        day = 0;  
      }else{
        day = day%122;
      }
    } //end if
    
    double rate = this.getWaterConsumption();

    if(day == 31){ //summer starts
      //same water rate consumption
      rate = rate * 2;
    }else if(day == 63){ //fall starts
      rate = rate * 0.25;
    }else if(day == 93){ //winter starts
      rate = rate * 0.5;
    }else if(day == 0){ //spring starts
      rate = rate * 4; 
    } //end else-if

    //commit changes
    this.setWaterConsumption(rate);
  } //end changeSeason

  public void dailyHappiness() {
    //increase happiness
    //dependent on: age, current meter
    //diseases are already accounted for
    double increaseBy = 0;

    Random random = new Random();
    increaseBy = random.nextDouble();
    increaseBy = increaseBy*2;

    this.setHappinessMeter(true, increaseBy);
  } //end changeHappiness0

  public void age() {
    this.age++;
  } //end age


  //abstract methods
  abstract void daily(int day);

} //end class def
