//Disease.java
//grace nguyen
//april 11, 2022

import java.io.*;
import java.util.*;

public class Disease implements Serializable {
  //attributes
  String[] diseases;
  int[][] status;

  public static void main(String[] args) {
    //class testing
    Disease grace = new Disease();

    //case testing
    //grace.setStatus("Aphids", true);
    grace.setStatus("Root Rot", true);
    //grace.setStatus("Mealybugs", true);
    //grace.setStatus("Powdery Mildew", true);
    //grace.setStatus("Fungus Gnats", true);
    grace.setStatus("Underwatered", true);
    //grace.setStatus("Spider Mites", true);
    grace.setStatus("Generic Fungal", true);
  
    //grace.continueDiseases();
    int[] gn = grace.ailments();
    int i = 0;
    String[] d = grace.getDiseases();

    while(i < 8) {
      if(gn[i] != -1) { 
        //System.out.println(d[i] + ":\n" + grace.getDescription(d[i]));
      }
      i++;
    } //end while

    //test listAllDescriptions
    //grace.listAllDescriptions(); //works!

    //test effect
    System.out.println(grace.effect()); //should be 5.6
  
    //test random
    //grace.contractRandomly();
    
    //test contagious case
    grace.contractRandomly("Aphids", true);
    boolean contagious = grace.isContagious();
    System.out.println(contagious);

    //printing everything
    grace.list();
  } //end main
  
  
  //constructor
  public Disease() {
    this.diseases = new String[8];
    this.status = new int[8][2];

    //put into array based on order of importance
    this.diseases[0] = "Aphids";
    this.diseases[1] = "Underwatered";
    this.diseases[2] = "Fungus Gnats";
    this.diseases[3] = "Mealybugs";
    this.diseases[4] = "Powdery Mildew";
    this.diseases[5] = "Spider Mites";
    this.diseases[6] = "Generic Fungal";
    this.diseases[7] = "Root Rot";
    
    //no diseases when instantiated
    for(int i = 0; i < 8; i++) {
      this.status[i][0] = 0;
      this.status[i][1] = 0;
    } //end for
  } //end constructor
  
  
  //getters and setters
  public void setStatus(String diseaseName, boolean hasDisease) {
    int index = this.getIndex(diseaseName);

    //set the status based on passed variable
    if(index != -1) {
      if(hasDisease == true) {
        this.status[index][0] = 1;
      }else{
        this.status[index][0] = 0; 
        this.status[index][1] = 0;
      } //end if-else
    }else{
      System.out.println("DISEASE.JAVA_ERROR: Could not find " + diseaseName + ".");
    } //end if-else
  } //end setStatus

  public int getIndex(String diseaseName) {
    //return index of the passed diseaseName if exists
    //if DNE, return -1
    int index = -1;

    for(int i = 0; i < 8; i++) {
      String name = this.diseases[i];

      if(name.equals(diseaseName)) {
        index = i;
      } //end if
    } //end for
    return(index);
  } //end getIndex
  
  public String[] getDiseases() {
    return(this.diseases);    
  } //end getDiseases

  public int[] ailments() {
    //return an array of indices of the the diseases the plant has
    int[] ailments = new int[8];
    int firstFreeIndex = 0;
    int lastFreeIndex = 7;

    for(int i = 0; i < 8; i++) {
      int hasAilment = this.status[i][0];

      if(hasAilment == 1) {
        ailments[firstFreeIndex] = i;
        firstFreeIndex++;
      }else{
        //signify the end of the array by loading up 'empty' spots with -1
        ailments[lastFreeIndex] = -1;
        lastFreeIndex--;
      } //end if-else
    } //end for

    return(ailments);
  } //end ailments

  
  //listing diseases in various forms and methods
  public void list() {
    //list everything
    for(int i = 0; i < 8; i++) {
      String name = this.diseases[i];
      String status = String.valueOf(this.status[i][0]);
      String days = String.valueOf(this.status[i][1]);
      System.out.println(name + ": " + status + " (" + days + " days)");
    } //end for
  } //end list
  
  public String getDescription(String disease) {
    //read file and parse for disease line of information
    String description = "";
    
    try{
      File readFile = new File("diseases.txt");
      Scanner scanner = new Scanner(readFile);
      int index = this.getIndex(disease);
      int days = this.status[index][1];
      boolean keepGoing = true;
      
      while(keepGoing) {
        //read each line
        String line = scanner.nextLine();
        String[] splitLine = line.split("#");

        //matching disease name
        if(splitLine[0].equals(disease)) {
          //get description matching to the day of affliction
          if(days == 0) {
            //nothing happens. no affliction. exit.
            keepGoing = false;
          }else{
            //in case the plant has been afflicted for very long....
            if(days > 3) {
              description = "- " + splitLine[1] + "\n- " + splitLine[2] + "\n- " + splitLine[3];
            }else{
              for(int i = 1; i <= days; i++) {
                if(description.equals("")) {
                  description = "- " + splitLine[i];
                }else{
                  description = description + "\n- " + splitLine[i]; 
                }
              } //end for
            } //end if-else
            keepGoing = false;
          } //end if-else
        } //end if
      } //end while
    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception-handler
    
    //annnnd return!
    return(description);
  } //end getDescription

  public void listAllDescriptions() {
    //get all descriptions of the diseases the plant is afflicted with
    int[] ailments = this.ailments();
    int i = 0;
    String desc = "";

    while(i < 8) {
      if(ailments[i] != -1) {
        int currentIndex = ailments[i];
        String diseaseName = this.diseases[currentIndex];
        desc = desc + "\n"+ (this.getDescription(diseaseName));
      } //end if
      i++;
    } //end while

    if(desc.equals("")) {
      desc = "- No observations (thankfully!)";
    } //end if
    System.out.println(desc);
  } //end getAllDescriptions

  public void listFull() {
    //get all descrations and and names associated iwth descriptions
    int[] ailments = this.ailments();
    int i = 0;

    while(i < 8) {
      if(ailments[i] != -1) {
        int currentIndex = ailments[i];
        String diseaseName = this.diseases[currentIndex];
        System.out.println(diseaseName + ": ");
        System.out.println(this.getDescription(diseaseName));
      } //end if
      i++;
    } //end while
  } //end listFull

  
  //methods that quantify Disease; meant to be used in conjunction with other Attribute classes
  public double effect() {
    //return the total effect the disease(s) have on the plant
    //this is determined based on: disease, and time length
    //disease and status data structure are arranged so that: goes from least to most 'dangerous' ailment
    int[] ailments = this.ailments(); 
    int i = 0;
    double totalEffect = 0;

    while(i < 8) {
      if(ailments[i] != -1) {
        int currentIndex = ailments[i];
        int timeLength = this.status[currentIndex][1];
        
        double singleEffect = (currentIndex*0.2) + 0.1;
        singleEffect = singleEffect*timeLength;
        totalEffect = totalEffect + singleEffect;
      } //end if
      i++;
    } //end while
    
    //anddddd return
    return(totalEffect);
  
  } //end effect
  
  public boolean hasDisease(String diseaseName) {
    //return true if plant has passed disease
    //return false if not found or if plant does not have disease
    boolean hasDisease = false;
    int index = this.getIndex(diseaseName);

    if(index != -1) {
      if(this.status[index][0] == 1) {
        hasDisease = true;  
      }else{
        hasDisease = false;
      } //end if-else
    } //end if
    return(hasDisease);
  } //end getStatus
  
  public void continueDiseases() {
    //if has disease, then +1 the days afflicted
    int index = 0;
    
    while(index != 8) {
      int hasDisease = this.status[index][0];

      if(hasDisease == 1) {
        this.status[index][1]++;
      } //end if
      index++;
    } //end while
  } //end continueDisease
  

  //contract randomly, infections
  public void contractRandomly() {
    //simulate the random contraction of diseases
    //diseases to get randomly: all but root rot and underwater
    for(int i = 0; i < 8; i++) {
      Random num = new Random();
      int result = num.nextInt(25);

      //1 in 25 chance of contracting disease randomly
      if(result == 1) {
        if(i == 1){
          //do nothing -- underwatered
        }else if(i == 7) { 
          //do nothing -- root rotting
        }else{
          this.status[i][0] = 1;
        } //end if-else
      } //end if
    } //end for 
  } //end contractRandomly

  public void contractRandomly(String diseaseName) {
    //same as above, but with polymoprhic behavior in case of only a singular disease
    int index = this.getIndex(diseaseName);
    
    Random num = new Random();
    int result = num.nextInt(25);

    //1 in 25 chance ...
    if(result == 1) {
      this.status[index][0] = 1;
    } //end if
  } //end contractRandomly

  public void contractRandomly(String diseaseName, boolean contamination) {
    //polymorphic behavior: true = inclusion of contamination contractions
    if(contamination == true) {
      int index = this.getIndex(diseaseName);

      Random num = new Random();
      int result = num.nextInt(5);

      //1 in 5 chance ...
      if(result == 1) {
        this.status[index][0] = 1;
      } //end if
    } //end if
    
    //index 4 and 6 can be contracted even with great care of plants
    Random num = new Random();
    int mildew = num.nextInt(25);
    int generic = num.nextInt(25);
    
    if(mildew == 1) {
      this.status[4][0] = 1;
    } //end if
    
    if(generic == 1) {
      this.status[6][0] = 1;
    } //end if
  } //end contractRandomly 

  public boolean isContagious() {
    //return true if plant is afflicted within the contagious disease
    //i.e. 0, 2, 3, 4, 5
    boolean contagious = false;
    for(int i = 2; i < 6; i++) {
      boolean hasDisease = this.hasDisease(this.diseases[i]);
      if(hasDisease == true) {
        contagious = true;
      } //end if
    } //end for

    //and check for aphids, index 1
    if(this.hasDisease("Aphids") == true) {
      contagious = true;
    } //end if
    
  //aaaaand return
  return(contagious);

  } //end isContagious

} //end class def
