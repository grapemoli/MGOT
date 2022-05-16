//Botanique.java
//grace nguyen
//april 21, 2022

import java.io.*;
import java.util.*;

public class Botanique {
  //attributes
  int userIndex;
  ArrayList<User> users;
  
  public static void main(String[] args) {
    //class testing

  } //end main
  

  //constructor
  public Botanique(int userIndex) {
    this.userIndex = userIndex;
    this.users = new ArrayList<User>();
    this.loadUsers();
  } //end Botanique
  

  //getters and setters
  public void setUserIndex(int userIndex) {
    this.userIndex = userIndex;
  } //end setUserIndex

  public int getUserIndex() {
    return(this.userIndex);
  } //end getUserIndex

  
  //money methods
  public boolean hasEnoughMoney(double money) {
    double balance = this.users.get(this.userIndex).getMoney();
    boolean enough = false;

    if(balance >= money) {
      enough = true;
    } //end if
  
    return(enough);

  } //end hasEnoughMoney

  
  //pots menu methods
  public double calculatePotPrice(int index) {
    double price = (index * 3) + 2;
    return(price);
  } //end calculatePotPrice


  //plant menu methods
  public void listPlants(String category) {
    String file = "plants" + category + ".dat";
    int counter = 1;

    try{
      File readFile = new File(file);
      Scanner scanner = new Scanner(readFile);
      boolean keepGoing = true;

      while(keepGoing) {
        String data = scanner.nextLine();

        //format as latin name 'common name'
        String[] plantInformation = data.split("#");
        String outputLine = plantInformation[0] + " (" + plantInformation[1] + ")\n";

        //get the price
        double height = Double.valueOf(plantInformation[10]);
        double width = Double.valueOf(plantInformation[11]);
        double price = this.calculatePlantPrice(height, width);
          
        //periods to imporve legibility
        for(int i = 0; i < 50; i++) {
          outputLine = outputLine + "." ; 
        }

        System.out.format(counter + ". " + outputLine + "$%.2f\n", price);

        counter++;

        //end if reach the end of the file
        if(!scanner.hasNextLine()) {
          keepGoing = false;
        } //end if
      } //end while
    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception handling
  } //end listPlants

  public String[] getPlantLine(String category, int plantIndex) {
    String file = "plants" + category + ".dat";
    String[] plantLine = null;

    try{
      File readFile = new File(file);
      Scanner scanner = new Scanner(readFile);
      int counter = 0;
      boolean keepGoing = true;

      while(keepGoing) {
        counter++;
        String data = scanner.nextLine();

        if(counter == plantIndex) {
          plantLine = data.split("#");
          keepGoing = false;
        } //end if

        if(!scanner.hasNextLine()) {
          keepGoing = false;
        } //end if
      } //end while
    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception handler

    return(plantLine);

  } //end getPlantLine

  public double calculatePlantPrice(double height, double width) {
    double plantPrice = ((height*17) + (width*17));
    return(plantPrice);
  } //end getPlantPrice
 

  //methods to help with Houseplant instantiation
  public double calculateWaterRate(String category) {
    double waterRate = 1d; //standard water rate

    //exceptions to standard
    if(category.equals("Cactus")) {
      waterRate = 0.5;  
    }else if(category.equals("Spathephyllum")){
      waterRate = 1.5;  
    }else if(category.equals("Dieffenbachia")){
      waterRate = 1.5;  
    }else if(category.equals("Sansevieria")){
      waterRate = 0.5; 
    } //end if-else

    return(waterRate);

  } //end calculateWaterRate

  public void buyPlant(String category, int line) {
    //remember to log the buy
    String[] plantInfo = this.getPlantLine(category, line);
    double height = Double.valueOf(plantInfo[10]);
    double width = Double.valueOf(plantInfo[11]);
    double price = this.calculatePlantPrice(height, width);
    boolean hasEnough = this.hasEnoughMoney(price);

    if(hasEnough == true) {
      double waterRate = this.calculateWaterRate(category);
      String nickname = this.nicknameMenu();
      this.users.get(this.userIndex).addPlant(category, line, nickname,waterRate);
      this.users.get(this.userIndex).journal.logBuy(nickname, String.valueOf(price));
      this.users.get(this.userIndex).changeMoney(price, false);
      this.saveUsers(); //SAVE!!!
      System.out.format("New Balance: $%.2f\n", this.users.get(this.userIndex).getMoney());
    }else{
      System.out.format("You don't have enough money! Your balance is $%.2f.\nWe photo-sympathize with you.\n", this.users.get(this.userIndex).getMoney());
    } //end if
  } //end buy plant


  //inventory methods
  public void buyItem(String item, double price, int amount) {
    if(amount == 1) {
      this.users.get(this.userIndex).journal.logBuy(item, String.valueOf(price));
      this.users.get(this.userIndex).addInventory(item);
      this.users.get(this.userIndex).changeMoney(price, false);
      System.out.println("You bought a " + item + "!");
    }else{
      this.users.get(this.userIndex).journal.logBuy(item, String.valueOf(price));
      this.users.get(this.userIndex).addInventory(item, amount); 
      this.users.get(this.userIndex).changeMoney(price, false);
      System.out.println("You bought " + amount + " of "+ item + "!");
    } //end if-else
  
    //save
    this.saveUsers();
    System.out.format("New Balance: $%.2f", this.users.get(this.userIndex).getMoney());
  } //end buyItem


  //serialization methods
  public void loadUsers() {
    try{
      FileInputStream objectFile = new FileInputStream("users.dat");
      ObjectInputStream objectIn = new ObjectInputStream(objectFile);

      ArrayList<User> loadedUsers = (ArrayList<User>)objectIn.readObject();
      this.users = loadedUsers; //updating attribute

      objectIn.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception handling  
  } //end loadUsers

  public void saveUsers() {
    try{
      FileOutputStream objectFile = new FileOutputStream("users.dat", false);
      ObjectOutputStream objectOut = new ObjectOutputStream(objectFile);
      objectOut.writeObject(this.users);
      objectOut.flush();
      objectOut.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    } //end exception handling
  } //end saveUsers


  //menu methods
  public void mainMenu() {
    String name = this.users.get(this.userIndex).getName();
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    
    while(keepGoing) {
      this.loadUsers();
      System.out.println("\n_____\n\n>~~~~~ Botanique ~~~~~<");
      System.out.println("1. Buy Plants\n2. Buy Pots\n3. Buy Treatments\n4. Buy Soil\n5. Buy Tools\n0. Quit");
      System.out.println("\nThistle be fun, " + name + "! What would you like to do? ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        System.out.println("Grass-cias for coming today!");
        keepGoing = false;
      }else if(action.equals("1")){
        String category = this.selectCategoryMenu();

        if(category.equals("")) {
          //do nothing
        }else{
          this.plantsMenu(category);
        } //end if-else
      }else if(action.equals("2")) {
        this.potsMenu();
      }else if(action.equals("3")) {
        this.treatmentsMenu();  
      }else if(action.equals("4")) {
        this.soilMenu();  
      }else if(action.equals("5")) {
        this.toolsMenu();
      }else {
        System.out.println("Please choose a valid input.");  
      } //end if-else
      this.saveUsers();
    } //end while
  } //end mainMenu

  public void plantsMenu(String category) {
    //choose a category, then choose a plant
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);

    while(keepGoing) {
      System.out.println("\n~ " + category + " Plants ~");
      this.listPlants(category);
      System.out.println("0. Quit\n\nWhich plant dew you want? ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false; 
      }else{
        try {
          int plantIndex = Integer.valueOf(action);
          this.buyPlant(category, plantIndex); 
          keepGoing = false;
        }catch(Exception e) {
          System.out.println("Please type a valid input!");
        } //end exception handling
      } //end if-else
    } //end while
  } //end plantsMenu()

  public String nicknameMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    String nickname = "";

    while(keepGoing) {
      System.out.println("What dew you want to nickname this plant? (0 to give no nickname)");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else {
        nickname = action.trim();
        boolean nicknameExists = false;
        
        //check that nickname does not exist
        for(int i = 0; i < this.users.get(this.userIndex).plants.size(); i++) {
          String currentNickname = this.users.get(this.userIndex).plants.get(i).getNickname();
          
          if(currentNickname.equals(nickname)) {
            nicknameExists = true;
          } //end if
        } //end for
        
        if(nicknameExists == false) {
          System.out.println("Oh, my gourd. Thyme for a new plant named " + nickname + "!");
          keepGoing = false;
        }else {
          System.out.println("Nickname " + nickname + " has already been given to another plant!");
        } //end if-else
      } //end if-else
    } //end while
    return(nickname);
  } //end nicknameMenu

  public String selectCategoryMenu() {
    //return the category that the user selects
    this.loadUsers(); 
    User currentUser = this.users.get(userIndex);
    String[] category = currentUser.getCategory();
    
    String returnedCategory = "";

    int length = category.length;
    boolean keepGoing = true;
    Scanner input = new Scanner(System.in);

    while(keepGoing) {
      System.out.println("\n_____\n\n~ Category Selection ~");
      for(int i = 0; i < length; i++) {
        int counter = i + 1;
        System.out.println(counter + ". " + category[i]);
      } //end for

      System.out.println("0. Quit\n\nSelect a category: ");
      String action = input.nextLine();

      try{
        int categoryIndex = Integer.valueOf(action) - 1;

        if(categoryIndex == -1) {
          keepGoing = false; 
        }else{
          //implied: catches out-of-bound inputs
          returnedCategory = category[categoryIndex];
          keepGoing = false;
        } //end if-else
      }catch(Exception e){
        System.out.println("Please input a valid input!");
      } //end exception handling
    } //end while

    return(returnedCategory);

  } //end selectCategoryMenu

  public void potsMenu() {
    //choose a pot to buy
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    String[] pots = {"1 inch Pot", "5 inch Pot", "7 inch Pot", "10 inch Pot", "12 inch Pot", "15 inch Pot", "24 inch Pot"};

    while(keepGoing) {
      System.out.println("\n~ Pots Menu ~");
      for(int i = 0; i < pots.length; i++) {
        int counter = i + 1;
        double price = this.calculatePotPrice(i);
        String outputLine = counter  + ". " + pots[i];
        int excessSpace = 50 - outputLine.length();
        
        //formatting
        for(int k = 0; k < excessSpace; k++) {
          outputLine = outputLine + ".";
        } //end for
        System.out.format(outputLine + "$%.2f\n", price);
      } //end for
      
      System.out.println("0. Quit\n\nChoose a pot: ");

      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;
      }else{
        try{
          int potIndex = Integer.valueOf(action);
          String item = pots[potIndex];
          double price = this.calculatePotPrice(potIndex);
          boolean hasEnough = this.hasEnoughMoney(price);

          if(hasEnough == true) {
            this.buyItem(item, price, 1);
            keepGoing = false;
          }else{
            System.out.format("You only have $%.2f!\nWe photo-sympathize with you.\n", this.users.get(this.userIndex).getMoney()); 
            keepGoing = false;
          } //end if-else
        }catch(Exception e) {
          System.out.println("Please type a valid input.");  
        } //end exception handler 
      } //end if-else
    } //end while
  } //end pot menu

  public void treatmentsMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);

    while(keepGoing) {
      System.out.println("\n~ Treatments Menu ~");
      String[] treatments = {"Fertilizer", "Fungicide", "Insecticide"};
    
      for(int i = 0; i < treatments.length; i++) {
        int counter = i + 1;
        String outputLine = counter + ". " + treatments[i];
        int excessSpace = 50 - treatments[i].length();

        //formatting
        for(int k = 0; k < excessSpace; k++) {
          outputLine = outputLine + "."; 
        } //end for
        
        double price = Double.valueOf(counter) * 3d;
        System.out.format(outputLine + "$%.2f\n", Double.valueOf(price));
      } //end for
      
      System.out.println("0. Quit\n\nChoose a treatment: ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else {
        try {
          int treatmentIndex = Integer.valueOf(action) - 1;
          String name = treatments[treatmentIndex];
          double price = (Double.valueOf(treatmentIndex) + 1) * 3d;
          boolean hasEnough = this.hasEnoughMoney(price);

          if(hasEnough == true) {
            this.buyItem(name, price, 1);
            keepGoing = false;
          }else{
            System.out.format("You only have $%.2f!\nWe photo-sympathize with you.\n", this.users.get(this.userIndex).getMoney());
            keepGoing = false;
          } //end if-else
        }catch(Exception e) {
          System.out.println("Please type a valid input!");
        } //end exception handling
      } //end if-else
    } //end while
  } //end treatmentsMenu

  public void soilMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);

    while(keepGoing) {
      System.out.println("\n~ Soil Menu ~");
      String[] soil = {"1 Bag of Soil", "5 Bags of Soil", "10 Bags of Soil"};
      
      for(int i = 1; i <= soil.length; i++) {
        String outputLine = i + ". " + soil[i - 1];
        int excessSpace = 50 - outputLine.length();

        //formatting
        for(int k = 0; k < excessSpace; k++) {
          outputLine = outputLine + ".";
        } //end for

        System.out.format(outputLine + "$%.2f\n", (i * 3d));
      } //end for
      System.out.println("0. Quit\n\nHow much soil dew you want? ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else {
        try {
          int soilIndex = Integer.valueOf(action) - 1;
          String name = soil[soilIndex];
          double price = (soilIndex + 1) * 3d;
          boolean hasEnough = this.hasEnoughMoney(price);

          if(hasEnough == true) {
            int amount = 1;
            if(soilIndex > 0) {
              amount = soilIndex * 5;  
            } //end if
           
            this.buyItem("Soil", price, amount);
            keepGoing = false;
          }else {
            System.out.format("You only have $%.2f", this.users.get(this.userIndex).getMoney());
            keepGoing = false;
          } //end if-else
        }catch(Exception e) {
          System.out.println("Please type a valid input!");
        } //end exception handling
      } //end if-else
    } //end while
  } //end soilMenu

  public void toolsMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);

    while(keepGoing) {
      System.out.println("\n~ Tools Menu ~");
      String[] tools = {"Water Meter"};

      for(int i = 0; i < tools.length; i++) {
        int counter = i + 1;
        String outputLine = counter + ". " + tools[i];

        //formatting
        int excessSpace = 50 - outputLine.length();

        for(int k = 0; k < excessSpace; k++) {
          outputLine = outputLine + ".";
        } //end for
        
        double price = counter*5d;
        System.out.format(outputLine + "$%.2f\n", price);
      } //end for
      System.out.println("0. Quit\n\nChoose a tool: ");

      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else{
        int toolIndex = Integer.valueOf(action) - 1;
        String name = tools[toolIndex];
        double price = (toolIndex + 1) * 5d;
        boolean hasEnough = this.hasEnoughMoney(price);

        if(hasEnough == true) {
          this.buyItem(name, price, 1);
          this.users.get(this.userIndex).setHasWaterMeter(true);
          this.saveUsers();
          keepGoing = false;
        }else {
          System.out.format("You only have $%.2f!\nWe photo-sympathize with you.\n", this.users.get(this.userIndex).getMoney());
          keepGoing = false;
        } //end if-else
      } //if-else     
    } //end while
    
  } //end toolsMenu

} //end class def
