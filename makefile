PlantBook.class: PlantBook.java Book.class
	javac -g PlantBook.java

Book.class: Book.java
	javac -g Book.java

Journal.class: Journal.java Book.class
	javac -g Journal.java

Plant.class: Plant.java Happy.class Water.class Disease.class Attribute.class
	javac -g Plant.java

Attribute.class: Attribute.java
	javac -g Attribute.java

Happy.class: Happy.java Attribute.class
	javac -g Happy.java

Water.class: Water.java Attribute.class
	javac -g Water.java

Disease.class: Disease.java
	javac -g Disease.java

Inventory.class: Inventory.java
	javac -g Inventory.java

Botanique.class: Botanique.java
	javac -g Botanique.java

Main.class: Main.java
	javac -g Main.java

Houseplant.class: Houseplant.java Plant.class Happy.class Water.class Disease.class Attribute.class Inventory.class
	javac -g Houseplant.java

User.class: User.java Houseplant.class Plant.class Inventory.class Disease.class Happy.class Water.class Attribute.class Journal.class PlantBook.class Book.class Botanique.class
	javac -g User.java

run: Main.class User.class Houseplant.class Plant.class Happy.class Water.class Disease.class Attribute.class Inventory.class PlantBook.class Book.class Journal.class Botanique.class
	java Main

clean:
	rm *.class

debug: Main.class
	jdb Main
