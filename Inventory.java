//Inventory.java
//grace nguyen
//april 17, 2022

import java.io.*;
import java.util.*;

public class Inventory implements Serializable {
  //attributes
  Map<String, Integer> inventory;

  public static void main(String[] args) {
    //class testing
    Inventory grace = new Inventory(); 
    grace.setInventory("1 inch Pot", 10);
    grace.addInventory("15 inch Pot");
    grace.addInventory("Fertilizer", 2);
    grace.listInventory();
  } //end main


  //constructor
  public Inventory() {
    this.inventory = new HashMap<String, Integer>();

    //load up the inventories
    this.inventory.put("Fertilizer", 0);
    this.inventory.put("Fungicide", 0);
    this.inventory.put("Insecticide", 0);
    this.inventory.put("Soil", 0);
    this.inventory.put("Water Meter", 0);
    this.inventory.put("1 inch Pot", 0);
    this.inventory.put("5 inch Pot", 0);
    this.inventory.put("7 inch Pot", 0);
    this.inventory.put("10 inch Pot", 0);
    this.inventory.put("12 inch Pot", 0);
    this.inventory.put("15 inch Pot", 0);
    this.inventory.put("24 inch Pot", 0);
  } //end Inventoryi


  //setters and getters (and lister) methods!
  public void setInventory(String key, int newAmount) {
    if(newAmount < 0) {
      newAmount = 0;
    } //end if
    
    int amount = this.inventory.get(key);
    this.inventory.replace(key, amount, newAmount);
  } //end setInventory

  public Object[] getKeys() {
    //get the keys IN ALPHABETICAL ORDER
    Set<String> inventoryKeys = this.inventory.keySet();
    Object[] arrKeys = inventoryKeys.toArray();
    Arrays.sort(arrKeys);
    return(arrKeys);
  } //end getKeys

  public void listKeys() {
    //list inventory names (or the keys) only
    //in alphabetical order
    int counter = 1;
    Set<String> inventoryKeys = this.inventory.keySet();
    Object[] arrKeys = inventoryKeys.toArray();
    Arrays.sort(arrKeys);

    for(Object name : arrKeys) {
      System.out.println(counter + ". " + name);
      counter++;
    } //end for-each
  } //end listKeys

  public int getInventory(String key) {
    //get a specific inventory's amound
    return(this.inventory.get(key));
  } //end getInventory 

  public void listInventory() {
    //list all inventory and amount
    //in alphabetical order
    int counter = 1;
    Set<String> inventoryKeys = this.inventory.keySet();
    Object[] arrKeys = inventoryKeys.toArray();
    Arrays.sort(arrKeys);
    
    for(Object name : arrKeys) {
      String amount = String.valueOf(this.inventory.get(name));
      String line = String.valueOf(counter) + ". " + name + " - " + amount;
      System.out.println(line);
      counter++;
    } //end for-each
  } //end listInventory

  
  //has methods
  public boolean hasInventory(String key) {
    boolean has = true;
    int amount = this.inventory.get(key);

    if(amount == 0) {
      has = false;  
    } //end if
    return(has);
  } //end hasInventory

  
  //use inventory
  public void useInventory(String key) {
    //decrement inventory by 1
    boolean has = this.hasInventory(key);

    if(has == true) {
      int amount = this.inventory.get(key);
      int newAmount = amount - 1;
      this.setInventory(key, newAmount);
    }else{
      System.out.println("You do not have enough " + key + "!");
    } //end if
  } //end useInventory

  public void addInventory(String key) {
    //increment inventory by 1
    int amount = this.inventory.get(key);
    int newAmount = amount + 1;
    this.setInventory(key, newAmount);
  } //end addInventory

  public void addInventory(String key, int addBy) {
    //increment inventory by amount
    int amount = this.inventory.get(key);
    int newAmount = amount + addBy;
    this.setInventory(key, newAmount);
  } //end addInventory

} //end class def 
