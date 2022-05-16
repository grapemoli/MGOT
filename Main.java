//Main.java
//grace nguyen
//april 21, 22

import java.io.*;
import java.util.*;

public class Main {
  //attributes
  ArrayList<User> users = new ArrayList<User>();
  int gardenLimit = 3;

  public static void main(String[] args) {
    Main m = new Main();
    
    //load users if users.dat already exists
    boolean usersExists = m.userExists();
 
    if(usersExists == true) {
      m.users = m.loadUsers();
    } //end if
    m.mainMenu();
  } //end main
  

  //init-related methods
  public boolean userExists() {
    boolean exists = false;

    try{
      File file = new File("users.dat");

      if(file.length() != 0) {
        exists = true;
      } //end if
    }catch(Exception e){
      System.out.println("Error loading file 'users.dat'.");
    } //end exception handler
    return(exists);
  } //end userExists

  public void initUser() {
    Scanner scanner = new Scanner(System.in);
    boolean keepGoing = true;
    
    //start initialization if user limit has not been reached
    boolean limitReached = this.limitReached();
    
    if(limitReached == true) {
      keepGoing = false;
      System.out.println("Garden limit of " + this.gardenLimit + "! Delete a Garden to create a new Garden!");
    } //end if
    
    while(keepGoing) {
      System.out.println("Enter a name (0 to quit): ");
      String name = scanner.nextLine();
      name = name.trim();
      
      //check for improper name (only spaces) or if user inputs 0 to quit
      if(name.equals("0")) {
        keepGoing = false;
      }else if(name.equals("")){
        System.out.println("Please input a valid name.");
      }else{
        boolean keepConfirm = true;
        boolean nameExists = this.nameExists(name);
        
        //run confirmation only if user is not already registered
        if(nameExists == false) {
          //name confirmation
          while(keepConfirm == true) {
            System.out.println("Your name is " + name + "? (y/n)");
            String confirm = scanner.nextLine();

            if(confirm.equals("y")) {
              int newIndex;
              try{ 
                newIndex = this.users.size(); //throws error if empty arraylist
              }catch(Exception e){
                newIndex = 0;
              } //end exception handler

              this.users.add(new User(name, newIndex));
              this.saveUsers();
              keepGoing = false;
              keepConfirm = false;

            }else if(confirm.equals("n")){
              keepConfirm = false;
            } //end if-else
          } //end while
        }else {
          System.out.println(name + " already has a garden!");
        } //end if-else
      } //end if-else  
    } //end while
  } //end initUser
  
  public boolean nameExists(String name) {
    boolean exists = false;
    name = name.toLowerCase();
    
    for(int i = 0; i < this.users.size(); i++) {
      String currentUserName = this.users.get(i).getName();
      currentUserName = currentUserName.toLowerCase();

      if(currentUserName.equals(name)) {
        exists = true;
      } //end if
    } //end for
    
    return(exists);    
    
  } //end nameExists
  
  public boolean limitReached() {
    //there can only be three users max
    boolean limitReached = false;
    int numOfUsers = this.users.size();
    
    if(numOfUsers >= this.gardenLimit) {
      limitReached = true;
    } //end if
    
    return(limitReached);
    
  } //end limitReached
  

  //serialization methods
  public ArrayList<User> loadUsers() {
    try{
      FileInputStream objectFile = new FileInputStream("users.dat");
      ObjectInputStream objectIn = new ObjectInputStream(objectFile);

      ArrayList<User> loadedUsers = (ArrayList<User>)objectIn.readObject();
      this.users = loadedUsers; //updating attribute

      objectIn.close();
      return(loadedUsers);
    }catch(Exception e){
      System.out.println(e.getMessage());
      return(null);
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
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);

    while(keepGoing) {
      int length; //main menu changes dependent on length
      try {
        length = this.users.size(); //throws an error if empty arraylist
      }catch(Exception e) {
        length = 0;
      } //end exception handler

      System.out.println("\n_____\n\n$$$$$ Money Grows on Trees $$$$$");
      if(length == 0) {
        System.out.println("1. New Garden\n0. Quit\n\nWhat would you like to do? ");
        String action = scanner.nextLine();

        if(action.equals("0")) {
          keepGoing = false;  
        }else if(action.equals("1")) {
          this.initUser();  
        }else {
          System.out.println("Please input 1 or 0.");
        } //end if-else
      }else{
        System.out.println("1. Load Garden\n2. New Garden\n3. Delete Garden\n0. Quit\n\nWhat would you like to do? ");
        String action = scanner.nextLine();
        
        if(action.equals("0")) {
          keepGoing = false;
        }else if(action.equals("1")) {
          this.loadGardenMenu();  
        }else if(action.equals("2")) {
          this.initUser();  
        }else if(action.equals("3")){
          this.deleteGardenMenu();
        }else{
          System.out.println("Please 0, 1, 2, or 3.");
        } //end if
      } //end if 
    } //end while
  } //end mainMenu

  public void loadGardenMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    int length = this.users.size();

    while(keepGoing) {
      System.out.println("\n---Load Garden---");

      //print all gardens in users arraylist
      for(int i = 0; i < length; i++) {
        int number = i + 1;
        String name = this.users.get(i).getName();
        System.out.println(number + ". " + name + "'s Garden");
      } //end for
      
      System.out.println("0. Quit\n\nChoose a garden: ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else {
        try {
          //implied check for non-int and out-of-bounds input
          int userIndex = Integer.valueOf(action) - 1;
          this.userMenu(userIndex);
          this.saveUsers(); //save all changes the user made
          keepGoing = false;
        }catch(Exception e) {
          System.out.println("Please input a valid input.");
        } //end exception handler
      } //end if-else
    } //end while 
  } //end loadGardenMenu
    
  public void deleteGardenMenu() {
    boolean keepGoing = true;
    Scanner scanner = new Scanner(System.in);
    int length = this.users.size();

    while(keepGoing) {
      System.out.println("\n---Delete Garden---");

      //print all gardens in users arraylist
      for(int i = 0; i < length; i++) {
        int number = i + 1;
        String name = this.users.get(i).getName();
        System.out.println(number + ". The Garden of " + name);
      } //end for
      
      System.out.println("0. Quit\n\nChoose a garden: ");
      String action = scanner.nextLine();

      if(action.equals("0")) {
        keepGoing = false;  
      }else {
        try {
          //implied check for non-int and out-of-bounds input
          int userIndex = Integer.valueOf(action) - 1;
          System.out.println(this.users.get(userIndex).getName() + "'s Garden was succesfully deleted!");
          this.users.remove(userIndex);
          this.saveUsers();
          keepGoing = false;
        }catch(Exception e) {
          System.out.println("Please input a valid input.");
        } //end exception handler
      } //end if-else
    } //end while 
    
  } //end deleteGardenMenu

  public void userMenu(int userIndex) {
    boolean keepGoing = true;

    while(keepGoing) {
      this.loadUsers();
      String action = this.users.get(userIndex).userMenu();
      
      if(action.equals("0")) {
        keepGoing = false;  
      }else if(action.equals("1")) {
        this.users.get(userIndex).listPlantMenu();
      }else if(action.equals("2")) {
        this.users.get(userIndex).inventoryMenu();
      }else if(action.equals("3")) {
        this.users.get(userIndex).treatMainMenu();
        this.saveUsers();
      }else if(action.equals("4")) {
        this.users.get(userIndex).repotMenu();
        this.saveUsers();
      }else if(action.equals("5")) {
        this.users.get(userIndex).waterPlantMenu();
        this.saveUsers();
      }else if(action.equals("6")) {
        this.users.get(userIndex).sellPlantMenu();
        this.saveUsers();
      }else if(action.equals("7")) {
        this.users.get(userIndex).readJournal();
      }else if(action.equals("8")) {
        this.users.get(userIndex).readPlantBookMenu();
      }else if(action.equals("9")) {
        this.users.get(userIndex).goToBotanique();
        this.loadUsers();
      }else if(action.equals("10")) {
        this.users.get(userIndex).nextDay();
        this.saveUsers();
      } //end if-else
    } //end while
  } //end userMenu


} //end class def

