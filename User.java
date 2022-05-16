//User.java
//grace nguyen
//april 18, 2022

import java.io.*;
import java.util.*;

public class User implements Serializable {
  //attributes
  Inventory inventory;
  Journal journal;
  PlantBook plantBook;
  ArrayList<Houseplant> plants;  
  String name;
  double money;
  boolean hasWaterMeter;
  int userIndex;


  //final attributes
  private final String[] CATEGORY = {"Aglaonema", "Anthurium", "Bromeliad", "Cactus", "Dieffenbachia", "Dracaena", "Fern", "Ficus", "Foliage", "Hanging", "Other", "Palm", "Philodendron", "Sansevieria", "Spathiphyllum"};

  //main class
  public static void main(String[] args) {
    //class testing
    User grace = new User("grakamoil");
    grace.addInventory("Insecticide"); 
    grace.addInventory("Fungicide");
    grace.addInventory("Fertilizer");
    grace.addInventory("15 inch Pot");
    //grace.addPlant("Cactus", 1, "grace");
    grace.userMenu();
  } //end main
 
  
  //constructors
  public User() {
    //classes
    this.inventory = new Inventory();
    this.journal = new Journal();
    this.plantBook = new PlantBook("HousePlants101");

    //load the automatic free plant into HousePlants101
    this.plantBook.addContent("Cactus", 7);

    //load arraylist
    this.plants = new ArrayList<Houseplant>();
    this.plants.add(new Houseplant("Cactus", 7));

    //other attributes
    this.money = 0d;
    this.name = "";
    this.userIndex = -1;
  } //end constructor
  
  public User(String name) {
    //classes
    this.inventory = new Inventory();
    this.journal = new Journal();
    this.plantBook = new PlantBook("HousePlants101");

    //load the automatic free plant into HousePlants101
    this.plantBook.addContent("Cactus", 7);
    
    //load arraylist
    this.plants = new ArrayList<>();
    this.plants.add(new Houseplant("Cactus", 7));

    //other attributes
    this.money = 0d;
    this.name = name;
    this.userIndex = -1;
  } //end constructor
  
  public User(String name, int userIndex) {
    //classes
    this.inventory = new Inventory();
    this.journal = new Journal();
    this.plantBook = new PlantBook("HousePlants101");

    //load the automatic feww plant into HousePlants101
    this.plantBook.addContent("Cactus", 7);

    //load arraylist
    this.plants = new ArrayList<>();
    this.plants.add(new Houseplant("Cactus", 7, "", 0.5));

    //other attribtues
    this.money = 0d;
    this.name = name;
    this.userIndex = userIndex;
  } //end contructor


  //getters and setters
  public int getUserIndex() {
    return(this.userIndex);
  } //end getUserIndex

  public void setUserIndex(int userIndex) {
    this.userIndex = userIndex;  
  } //end setUserIndex

  public double getMoney() {
    return(this.money);
  } //end getMoney
 
  public void setMoney(double money) {
    this.money = money;
  } //end setMoney

  public String getName() {
    return(this.name);
  } //end getName

  public void setName(String name) {
    this.name = name;
  } //end setName

  public void setHasWaterMeter(boolean have) {
    this.hasWaterMeter = have;
  } //end setHasWaterMeter

  public String[] getCategory() {
    return(this.CATEGORY);
  } //end getCategories

  
  //money methods
  public void changeMoney(double money, boolean increment) {
    //increment and decrementing for daily fluctuations of the money attribute
    if(increment == true) {
      this.money = this.money + money;  
    }else {
      this.money = this.money - money;

      //bottom limit is 0
      if(this.money < 0d) {
        this.money = 0d;  
      } //end if
    } //end if-else
  } //end changeMoney

  public boolean hasEnoughMoney(double money) {
    boolean enough = false;
    
    if(this.money > money) {
      enough = true;
    } //end if

    return(enough);
  } //end hasEnoughMoney
   

  //main user menu method
  public String userMenu() {
    Scanner scanner = new Scanner(System.in);
    boolean keepGoing = true;
    String userAction = "-1";

    while(keepGoing) {
      //always-updating variables
      int day = this.journal.getDay();
      String season = this.getSeason(day);

      System.out.println("\n_____\n\n||||| " + this.name + "'s Garden |||||");
      System.out.println("Day " + day );
      System.out.format("+ Balance: $%.2f", this.money);
      System.out.println("\n+ Season: " + season);
      System.out.println("---");
      System.out.println("1. View Plants\n2. View Inventory\n3. Treat Plants\n4. Repot Plants\n5. Water Plants\n6. Sell Plants\n7. Read Journal\n8. HousePlants101\n9. Go to Botanique\n10. Next Day\n0. Quit");
      System.out.println("\nWhat would you like to do? ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
        userAction = action;
      }else if(action.equals("1")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("2")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("3")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("4")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("5")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("6")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("7")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("8")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("9")) {
        userAction = action;
        keepGoing = false;
      }else if(action.equals("10")) {
        userAction = action;
        keepGoing = false;
      }else{
        System.out.println("Please input a valid input.");  
      } //end if-else
    } //end while
    return(userAction);
  } //end userMenu 
  
  public int selectPlantMenu(String menuName) {
    Scanner scanner = new Scanner(System.in);
    boolean keepGoing = true;
    int plantIndex = -1;
    System.out.println("_____");

    while(keepGoing) {
      System.out.println("\n\n-----" + menuName + " Menu-----");
      this.listPlantNames();
      System.out.println("0. Quit\n\nChoose a plant to repot: ");
      String userInput = scanner.nextLine();

      try{
        //get the index
        plantIndex = Integer.valueOf(userInput) - 1;

        if(plantIndex == -1) {
          keepGoing = false;  
        }else{
          //this will throw an error if the user types out of bounds
          //so there is no need to explicitly check for bounds
          String name = this.plants.get(plantIndex).getNickname();
          
          if(name.equals("")) {
            name = this.plants.get(plantIndex).getCommonName();  
          }else {
            name = name + " (" + this.plants.get(plantIndex).getCommonName() + ")";  
          } //end if-else
          
          keepGoing = false;
        } //end if-else
      }catch(Exception e){
        System.out.println("Please input a valid input.");
      } //end exception handling
    } //end while 
    return(plantIndex);
  } //end selectPlantMenu
  
  
  //plant methods
  public void listPlantNames() {
    int length = this.plants.size();

    for(int i = 0; i < length; i++) {
      int counter = i + 1;
      String name = this.plants.get(i).getNickname();

      //in case the user has not nicknamed a plant
      if(name.equals("")) {
        name = this.plants.get(i).getCommonName();
      }else {
        name = name + " (" + this.plants.get(i).getCommonName() + ")";
      } //end if-else

      System.out.println(counter + ". " + name);
    } //end for
  } //end listPlants

  public void listOnePlant(int index) {
    //list one plants': names (nickname, common name)
    //happiness meter, (water meter), height/width, observations
    Houseplant current = this.plants.get(index);
    double happiness = current.getHappinessMeter();
    System.out.println("\n*Information*\n- Nickname: " + current.getNickname());
    System.out.println("- Common Name: " + current.getCommonName());
    System.out.format("- Happiness Status: %.2f\n", happiness);
    
    //water meter info changes based on presence of water meter
    if(this.hasWaterMeter == true) {
      System.out.println("- Water Status: " + String.valueOf(current.getWaterMeter()));
    }else{
      double waterMeter = current.getWaterMeter();

      if(waterMeter > 8d) {
        System.out.println("- Water Status: You stick your finger three inches into the soil. It feels moist."); 
      }else if(waterMeter > 6d) {
        System.out.println("- Water Status: You stick your finger three inches into the soil. It feels moist.");  
      }else if (waterMeter > 3d) {
        System.out.println("- Water Status: You stick your finger three inches into the soil. It feels dry.");
      }else if (waterMeter >= 0d) {
        System.out.println("- Water Status: You stick your finger three inches into the soil. It feels very dry...");  
      }else{
        System.out.println("- Water Status: --");
      } //end if-else
    } //end if-else

    //repotting status
    boolean repot = current.needRepot();
    Double potSize = current.getPotDiameterInches();
    
    if(repot == true) {
      System.out.format("- Repot Status: %.2f-inch pot is too small for the plant\n", potSize);  
    }else if(repot == false){
      System.out.format("- Repot Status: %.2f-inch pot fits the plant!\n", potSize);  
    }else{
      System.out.println("- Repot Status: --\n");
    } //end if-else

    System.out.println(current.getDimensionsFeet());
    System.out.println("\n*Observations* ");
    current.listDiseaseDescriptions();
  } //end listOnePlant

  public void listPlantMenu() {
    Scanner scanner = new Scanner(System.in);
    boolean keepGoing = true;
    System.out.println("_____");

    while(keepGoing) {
      System.out.println("\n-----List Plant Menu-----");
      this.listPlantNames();
      System.out.println("0. Quit");
      System.out.println("\nWhich plant would you like to see? ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else{
        //this is in case the user types out of bounds
        try{
          int index = Integer.valueOf(action) - 1;
          this.listOnePlant(index);
        }catch(Exception e){
          System.out.println("Please input a valid input.");
        } //end exception handler
      } //end if-else
    } //end while
  } //end listPlantMenu

  public void addPlant(String category, int lineNumber) {
    this.plants.add(new Houseplant(category, lineNumber));
    this.plantBook.addContent(category, lineNumber);
  } //end addPlant

  public void addPlant(String category, int lineNumber, String nickname) {
    this.plants.add(new Houseplant(category, lineNumber, nickname));
    this.plantBook.addContent(category, lineNumber);
  } //end addPlant

  public void addPlant(String category, int lineNumber, String nickname, double waterRate) {
    this.plants.add(new Houseplant(category, lineNumber, nickname, waterRate));
    this.plantBook.addContent(category, lineNumber);
  } //end addPlant


  //inventory methods
  public void listInventory() {
    this.inventory.listInventory();
  } //end listInventory

  public boolean hasInventory(String key) {
    boolean has = this.inventory.hasInventory(key);
    return(has);
  } //end hasInventory

  public void useInventory(String key) {
    this.inventory.useInventory(key);
  } //end useInventory

  public void addInventory(String key) {
    this.inventory.addInventory(key);
  } //end addInventory

  public void addInventory(String key, int amount) {
    this.inventory.addInventory(key, amount);
  } //end addInventory

  public void inventoryMenu() {
    //user menu for inventory methods
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    System.out.println("_____");

    while(keepGoing) {
      System.out.println("\n-----Inventory Menu-----");
      System.out.println("1. List Inventory\n0. Quit\n\nWhat would you like to do? ");
      String action = scanner.nextLine();

      if(action.equals("1")) {
        this.listInventory();
        keepGoing = false;
      }else if(action.equals("0")) {
        keepGoing = false;  
      }else{
        System.out.println("Please input 1 or 0.");  
      } //end if-else
    } //end while
  } //end inventoryMenu


  //treatment methods
  public void fungicide(int index) {
    //check all the diseases that fungicide cures
    //and cure/print messages as needed
    String name = this.plants.get(index).getNickname(); 
    String[] targets = {"Powdery Mildew", "Generic Fungal", "Root Rot"};
    boolean hasTargets = false;

    if(name.equals("")) {
      name = this.plants.get(index).getCommonName();
    }else {
      name = name + " (" + this.plants.get(index).getCommonName() + ")";
    } //end if-else
  
    //cure diseases that fungicide targets 
    for(int i = 0; i < 3; i++) {
      boolean current = this.plants.get(index).hasDisease(targets[i]);
      
      if(current == true) {
        hasTargets = true;
        this.plants.get(index).setDisease(targets[i], false);
        this.journal.logCure(name, targets[i]);
      }//end if
    } //end for

    //print the appropiate statements to shell
    if(hasTargets == true) {
      System.out.println(name + " successfully treated!");  
    }else{
      System.out.println("Nothing changed...");  
    } //end if-else
  } //end fungicide

  public void insecticide(int index) {
    //check all diseases insecticide heals and
    //cure as needed
    String name = this.plants.get(index).getNickname();
    String[] targets = {"Mealybugs", "Fungus Gnats", "Spider Mites", "Aphids"};
    boolean hasTargets = false;

    //check if no nickname was set
    if(name.equals("")) {
      name = this.plants.get(index).getCommonName();
    }else {
      name = name + " (" + this.plants.get(index).getCommonName() + ")";
    } //end if-else

    //check the targets and cure as needed
    for(int i = 0; i < 4; i++) {
      boolean currentTarget = this.plants.get(index).hasDisease(targets[i]);
      if(currentTarget == true) {
        hasTargets = true;
        this.plants.get(index).setDisease(targets[i], false);
        this.journal.logCure(targets[i], name);
      } //end if
    } //end for

    //print the appropiate messages to shell
    if(hasTargets == true) {
      System.out.println(name + " was succesfully treated!"); 
    }else{
      System.out.println("Nothing changed...");
    } //end if-else
  } //end insecticide

  public void fertilize(int index) {
    String name = this.plants.get(index).getNickname();

    if(name.equals("")) {
      name = this.plants.get(index).getCommonName();
    }else {
      name = name + " (" + this.plants.get(index).getCommonName() + ")";
    } //end if-else

    this.plants.get(index).setHappinessMeter(true, 5);
    System.out.println(name + " is happy!");
  } //end fertilize

  public void treatPlantMenu(String treatment) {
    Scanner scanner = new Scanner(System.in);
    boolean keepGoing = true;

    while(keepGoing) {
      System.out.println("\n-----" + treatment + "-----");
      this.listPlantNames(); 
      System.out.println("0. Quit\n\nWhich plant would you like to treat? ");
      String userInput = scanner.nextLine();

      //catch non-acceptable inputs
      try{
        int index = Integer.valueOf(userInput) - 1;

        if(index == -1) {
          keepGoing = false;  
        }else{
          //this will throw an error if user input is out of bounds
          String name = this.plants.get(index).getNickname();
          if(name.equals("")) {
            name = this.plants.get(index).getCommonName();
          }else {
            name = name + " (" + this.plants.get(index).getCommonName() + ")";
          } //end if

          //check has enough inventory
          boolean hasInventory = this.hasInventory(treatment);
          
          if(hasInventory == true) {
            this.useInventory(treatment);
            if(treatment.equals("Fungicide")) {
              this.journal.logFungicide(name);
              this.fungicide(index);
            }else if(treatment.equals("Insecticide")) {  
              this.journal.logInsecticide(name);
              this.insecticide(index);
            }else if(treatment.equals("Fertilizer")) {
              this.journal.logFertilizer(name);
              this.fertilize(index);
            }else{
              System.out.println("Error in TreatPlantMenu");
            } //end if-else
          }else{
            System.out.println("You do not have enough " + treatment + "!");  
            System.out.println("Walking back to the treatment menu...");
            keepGoing = false;
          } //end if-else

        } //end if
      }catch(Exception e){
       System.out.println("Please input a valid input."); 
      } //exception handling
    } //end while
  } //end treatPlantMenu

  public void treatMainMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    System.out.println("_____");

    while(keepGoing) {
      System.out.println("\n-----Treatment Menu-----");
      System.out.println("1. Fungicide\n2. Insecticide\n3. Fertilizer\n0. Quit");
      System.out.println("\nTreatment Option: ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;
      }else if(action.equals("1")){
        this.treatPlantMenu("Fungicide");
      }else if(action.equals("2")){
        this.treatPlantMenu("Insecticide");
      }else if(action.equals("3")){
        this.treatPlantMenu("Fertilizer");
      }else{
        System.out.println("Please input a valid input.");
      } //end if-else 
    } //end while
  } //end treatMainMenu

  
  //repot methods
  public void repotPlant(int index, double potInch) {
    double potMeter = potInch/39.37; //inches to meters
    int day = this.journal.getDay();
    String season = this.plants.get(index).getSeason(day);
    
    //repot the plant
    boolean repotted = this.plants.get(index).repot(potMeter, season); 
      
    //log to journal
    if(repotted == true) {
      this.journal.logRepot(name); 
    }else{
      String log = "Attempted to repot " + name;
      this.journal.addLog(log);
    } //end if-else
  } //end repotPlant

  public void repotMenu() {
    //get the user input to choose a pot
    int plantIndex = this.selectPlantMenu("Repotting");
    
    //only run if the returned plant index is not -1 (in other words, the User did not choose to quit the menu)
    if(plantIndex != -1) {
      //get the name
      String name = this.plants.get(plantIndex).getNickname();
    if(name.equals("")) {
      name = this.plants.get(plantIndex).getCommonName();
    }else {
      name = name + " (" + this.plants.get(plantIndex).getCommonName() + ")";
    } //end if-else
      
    //and now, let us do get the user input for pot size
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    String currentDiameterInches = String.valueOf(this.plants.get(plantIndex).getPotDiameterInches());

     while(keepGoing) {
       //print all the pots
       System.out.println("\n~Repotting " + name + "~");

       String[] pots = {"1 inch Pot", "5 inch Pot", "7 inch Pot", "10 inch Pot", "12 inch Pot", "15 inch Pot", "24 inch Pot"};
       int[] potInches = {1, 5, 7, 10, 12, 15, 24};    

        for(int i = 0; i < 7; i++) {
          int counter = i + 1;
          String amount = String.valueOf(this.inventory.getInventory(pots[i]));
          System.out.println(counter + ". " + pots[i] + " - " + amount);    
        } //end for
        System.out.println("0. Quit\nCurrent Pot Diameter (in): " + currentDiameterInches + "\n\nChoose a pot: ");
        String potInput = String.valueOf(scanner.nextLine());

        try{
          int potIndex = Integer.valueOf(potInput) - 1;

          if(potIndex == -1) {
            keepGoing = false;
          }else{
            boolean hasPot = this.inventory.hasInventory(pots[potIndex]);

            if(hasPot == true) { //check for pots
              if(this.hasInventory("Soil") == true) { //check for soil
                this.repotPlant(plantIndex, potInches[potIndex]);
                this.useInventory(pots[potIndex]);
                this.useInventory("Soil");
                keepGoing = false;
              }else{
                System.out.println("You do not have enough soil!");
              } //end if-else
            }else{
              System.out.println("You don't have any " + pots[potIndex] + "!");
            } //end if-else
          } //end if-else
        }catch(Exception e){
          System.out.println("Please input a valid input.");
        } //end exception handling
      } //end while
    } //end if
  } //end potMenu


  //water methods
  public void waterPlantMenu() {
    int plantIndex = this.selectPlantMenu("Watering");

    //continue water plant menu only if user did not choose to quit
    if(plantIndex != -1) {
      String name = this.plants.get(plantIndex).getNickname();

      if(name.equals("")) {
        name = this.plants.get(plantIndex).getCommonName();
      }else {
        name = name + " (" + this.plants.get(plantIndex).getCommonName() + ")";
      } //end if-else

      //water plants
      this.plants.get(plantIndex).water();
      System.out.println("You watered " + name + "!");
      this.journal.logWater(name);
    } //end if
  } //end waterPlantMenu


  //sell plants method
  public double sellPlant(int plantIndex) {
    //the value of the sold plant = 25% of the happiness meter
    double happyMeter = this.plants.get(plantIndex).getHappinessMeter();
    double moneyEarned = happyMeter * 0.25;

    //increase money attribute
    this.changeMoney(moneyEarned, true);

    //delete the plant
    this.plants.remove(plantIndex);
    return(moneyEarned);
  } //end sellPlant

  public void sellPlantMenu() {
    int plantIndex = this.selectPlantMenu("Selling");

    //continue if user did not select 0 to quit
    if(plantIndex != -1) {
      String name = this.plants.get(plantIndex).getNickname();

      if(name.equals("")) {
        name = this.plants.get(plantIndex).getCommonName();
      }else {
        name = name + " (" + this.plants.get(plantIndex).getCommonName() + ")";
      } //end if-else

      double partingGift = this.sellPlant(plantIndex);
      System.out.println("You tearfully dew what you must, and depart from " + name + ".");
      System.out.format(name + " leaves a parting gift of $%.2f.", partingGift);
    
      //log to journal
      String log = "Sold " + name;
      this.journal.addLog(log);
    } //end if
  } //end sellPlantMenu


  //journal methods
  public void readJournal() {
    System.out.println("\n~~~" + name + "'s Journal~~~");
    this.journal.listContent();
  } //end readJournal


  //houseplants101 methods
  public void readPlantBook(String latinName) {
    System.out.println(this.plantBook.getContent(latinName));
  } //end readPlantBook

  public void readPlantBookMenu() {
    //sold houseplant information is still listed in HousePlants101
    //therefore, only listing the current plants will NOT work
    Scanner scanner = new Scanner(System.in);
    int plantIndex = 0;
    boolean keepGoing = true;
    System.out.println("_____");

    while(keepGoing) {
      //get the user choice on which plant to view the information
      System.out.println("\n~~~~ HousePlants101 ~~~~~");
      this.plantBook.listContentKeys();
      System.out.println("0. Quit\n\nOh my gourd. Which plant would you like to read about? ");
      String userInput = scanner.nextLine();

      try{
        plantIndex = Integer.valueOf(userInput) - 1;

        if(plantIndex == -1) { //user chose to quit
          keepGoing = false;  
        }else{
          //this will throw an error is a user types out of bounds.
          //therefore no need to expliclitly look for out-of-bounds input
          Object[] allKeys = this.plantBook.getContentKeys();
          String plantKey = String.valueOf(allKeys[plantIndex]);
          this.readPlantBook(plantKey);
        } //end if-else
      }catch(Exception e){
        System.out.println("Please input a valid input!");
      } //end exception handling
    } //end while
  } //end readPlantBookMenu



  //botanique methods
  public void goToBotanique() {
    Botanique store = new Botanique(userIndex);
    store.mainMenu();
  } //end goToBotanique


  //death methods
  public void killPlants() {
    //remove all plants with happiness meter of 0!
    ArrayList<Integer> deadPlants = new ArrayList<Integer>();
    
    //get an array of the plant indices to be removed
    for(int i = 0; i < this.plants.size(); i++) {
      double happiness = this.plants.get(i).getHappinessMeter();
      
      if(happiness == 0d) {
        deadPlants.add(i);
        
        String name = this.plants.get(i).getNickname();
        if(name.equals("")) {
          name = this.plants.get(i).getCommonName();
        } //end if
           
        System.out.println(name + " died.");
      } //end if
    } //end for 
    
    //delete the plants, starting from end (to not mess up them indices prematurely)
    if(deadPlants.size() > 0) {
      for(int i = deadPlants.size(); i > 0; i--) {
        this.plants.remove(deadPlants.get(i - 1));
       } //end for 
    } //end if
 
  } //end killPlants


  //contagious diseases methods
  public ArrayList<Integer> getContagions() {
    //if one plant has a contagious disease... then return true
    ArrayList<Integer> contagions = new ArrayList<Integer>();
    int length = this.plants.size();

    for(int i = 0; i < length; i++) {
      boolean isContagious = this.plants.get(i).isContagious();
     
      //indices of non-contagious diseases are: 1, 6, 7
      if(isContagious == true) {
        int[] ailments = this.plants.get(i).ailments();
            
        int index = 0;
        while(ailments[index] != -1) {
          if(ailments[index] != 1) {
            if(ailments[index] != 6) {
              if(ailments[index] != 7) {
                contagions.add(index);
              } //end if
            } //end if
          } //end if
        } //end while
      } //end if
    } //end for
    return(contagions);
  } //end getContagions

  public void spreadContagions() {
    ArrayList<Integer> contagions = this.getContagions();
    int length = contagions.size();
    String[] diseaseNames = this.plants.get(0).getDiseases();
    
    for(int i = 0; i < length; i++) {
      int currentIndex = contagions.get(i);
      String currentDisease = diseaseNames[currentIndex];

      int size = this.plants.size();
      for(int j = 0; j < size; j++) {
        this.plants.get(j).contractRandomly(currentDisease);
      } //end for
    } //end for
  } //end spreadContagions


  //time-related methods
  public String getSeason(int day) {
    //one year is 122 days long in this sim
    if(day > 122)  {
      day = day%122;  
    } //end if

    if(day <= 30) {
      return("SPRING");  
    }else if(day <= 62) {
      return("SUMMER");
    }else if(day <= 92) {
      return("FALL");
    }else if(day <= 122) {
      return("WINTER");
    }else{
      return(null);
    } //end if-else
  } //end getSeason

  public void nextDay() {
    //daily method from Journal 
    this.journal.nextDay();
    int day = this.journal.getDay();

    //daily method from Houseplant
    int length = this.plants.size();

    for(int i = 0; i < length; i++) {
      //money earnings from plant
      double earnings = this.plants.get(i).dailyMoney();
      this.money = this.money + earnings;

      //next day method
      this.plants.get(i).daily(day);
      
      //daily contraction of fungal diseases
      this.plants.get(i).contractRandomly("general", false);
      
      //spreadContagions will throw an error if the plants attribute is empty
      //so only run when plants attributed is loaded
      int size = this.plants.size();
      if(size > 0) {
        this.spreadContagions();  
      } //end if
    } //end for

    //kill plants w happiness of 0
    this.killPlants();
  } //end nextDay


} //end class def 



