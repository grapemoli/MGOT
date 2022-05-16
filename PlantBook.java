//PlantBook.java
//grace nguyen
//april 2, 2022

import java.io.*;
import java.util.*;
import java.text.*;

public class PlantBook extends Book implements Serializable {
  //attributes
  Map<String, String> content;

  public static void main(String[] args) {
    //class testing
    PlantBook grace = new PlantBook("hi");
    grace.addContent("Other", 13); 
    grace.addContent("Hanging", 5);
    grace.addContent("Cactus", 2);
    grace.addContent("Cactus", 2); //Java takes care of duplicate keys
    grace.addContent("Cactus",4);
    grace.listContentKeys();
    Object[] keys = grace.getContentKeys();
    for(int i = 0; i < 3; i++){
      System.out.println(keys[i]);
    } //end for
    String choice = String.valueOf(keys[0]);
    System.out.println(grace.getContent(choice));
  } //end main
  
  
  //constructors
  public PlantBook(String name) {
    super(name);
    content = new HashMap<String, String>();
  } //end constructor
  
  
  //getters and setters
  public String getContent(String key) {
    //return content at a specific key 
    return(this.content.get(key));
  } //end getContent

  public Object[] getContentKeys() {
    //return an array of alphabetized keys
    Set<String> setKeys = content.keySet();
    Object[] arrKeys = setKeys.toArray();
    Arrays.sort(arrKeys);

    return(arrKeys);
  } //end getKeys

  public void listContentKeys() {
    //list HashMap by alphabetical key order
    //sort an array
    Set<String> mapKeys = content.keySet();
    Object[] arrKeys = mapKeys.toArray();
    Arrays.sort(arrKeys);

    int counter = 0;
    for(Object key : arrKeys) {
      counter += 1;
      System.out.println(counter + ". " + key);
      String stringKey = String.valueOf(key);
    } //end for-each  
  } //end sortContent
  
  
  //io files methods
  public String getPlantLine(String category, Integer line) {
    //read the file, and insert information to string as needed
    String fileName = "plants" + category + ".dat";    
    String plant = "";

    try{
      File readFile = new File(fileName);
      Scanner scanner = new Scanner(readFile);
      int counter = 0;
      Boolean keepGoing = true;

      while(keepGoing.equals(true)) {
        counter++;
        String data = scanner.nextLine();

        //get the requested plant information
        if(line.equals(counter)) {
          plant = data;
          keepGoing = false;
        } //end if

        //exit loop if requested plant info is not found
        if(!scanner.hasNextLine()) {
          keepGoing = false;
          System.out.println("HousePlants101 Entry not found.");
        } //end if
      } //end while

    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception hander
    return(plant);
  } //end getPlantLine

  public String getPlantKey(String plantInfo) {
    //get the key for this plant line of information
    String[] splitString = plantInfo.split("#");
    String key = splitString[0] + " (" + splitString[1] + ")";
    return(key);
  } // getPlantKey
 
  
  //formatting strings methods
  public String formatLine(String plantInfo) {
    //format the line taken in for the 'encyclopedia'
    String[] splitString = plantInfo.split("#"); 
    String formatted = "";

    for(Integer i = 0; i < splitString.length; i++) {
      if(i.equals(0)) {
        formatted = "\n~ " + splitString[i] + " ~\n";
      }else if(i.equals(1)){
        formatted = formatted + "Common Name: " + splitString[i] + "\n";
      }else if(i.equals(2)){
        formatted = formatted + "Categories: " + splitString[i] + "\n";  
      }else if(i.equals(3)){
        formatted = formatted + "Origin: " + splitString[i] + "\n";  
      }else if(i.equals(4)){
        formatted = formatted + "Climate: " + splitString[i] + "\n";
      }else if(i.equals(5)){
        String tempString = toFarenheit(splitString[i]);
        formatted = formatted + "Temperature Max. (F): " + tempString + "\n";
      }else if(i.equals(6)){
        String tempString = toFarenheit(splitString[i]);
        formatted = formatted + "Temperature Min. (F): " + tempString + "\n";
      }else if(i.equals(7)){
        formatted = formatted + "Ideal Light: " + splitString[i] + "\n";
      }else if(i.equals(8)){
        formatted = formatted + "Tolerated Light: " + splitString[i] + "\n";
      }else if(i.equals(12)){
        if(splitString[i].equals("00")){
          formatted = formatted + "Potential Height (ft): --\n";  
        }else{
          String heightString = toFeet(splitString[i]);
          formatted = formatted + "Potential Height (ft): " + heightString + "\n";
        } //end if
      }else if(i.equals(13)){
        if(splitString[i].equals("00")){
          formatted = formatted + "Potential Width (ft): --\n";
        }else{   
          String widthString = toFeet(splitString[i]);
          formatted = formatted + "Potential Width (ft): " + widthString + "\n";
        } //end if
      }else if(i.equals(14)){
        formatted = formatted + "Watering: " + splitString[i] + "\n" ; 
      }//end if-else    
    } //end for-loop
  
    return(formatted);
  } //end formatLine

  
  //unit conversion methods
  public String toFarenheit(String celsius) {
    //convert string celsius to farenheit
    Double temp = 0d;
    String farenheit = "";
    
    try{
      temp = Double.valueOf(celsius);

      temp = (temp * 9)/5;
      temp = temp + 32;
      farenheit = String.format("%.2f", temp);
    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception-handling
   
   return(farenheit);
 
 } //end toFarenheit

  public String toFeet(String meters) {
    //convert string meters to feet
    Double size = 0d;
    String feet = "";

    try{
      size = Double.valueOf(meters);

      size = size * 3.28;
      feet = String.format("%.2f", size);
    }catch(Exception e){
      System.out.println(e.getMessage());  
    } //end exception-handling
    
    return(feet);
  
  } //end toFeet

  
  //adder methods
  public void addContent(String category, Integer index) {
    //add content to the hashmap
    String plantInfo = getPlantLine(category, index);

    String content = formatLine(plantInfo);
    String key = getPlantKey(plantInfo);
    
    this.content.put(key, content);
  } //end addContent

} //end class def
