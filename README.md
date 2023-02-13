# MGOT
## Summary
Money Grows on Trees, or MGOT, was created by web-scrapping plant information off of Tropicopia, and creating a textfiles of the plants seperated by plant categorization. This is one of my biggest projects, and is a realistic, comprehensive  simulator. Users can buy, nuture, grow, and possibly kill plants! Other points of interest is that I created a system that calculated logarithmic plant growth, which took into account the season and overall plant condition. 

This is my second major project done as a final assignment for a Computer Science course. Note that the original projected was published on my GitHub Enterprise account. I simply copied the project over to here, too.

Note: I created a makefile for ease in running this program. Simply type "make run" in the console. If not, the main file is Main.java.

### Goal
Often, people kill their first few plants because of improper care. The goal of MGOT is to provide an easy and afforadble (free!) learning system for people interested in taking care of plants. MGOT was made to be as realistic as possible. Examples of incorporated realism:
- over 150 unique plants to care for
- unique logistic growth for each plant
- need to repot when a plant root system outgrows the pot
- unique water consumption for each plant 
- change in plant behaviors with changfes in seasons, years (year is shortened by 2/3)
- possible contraction of diseases even with great care of plants
- possible contraction of pest infestations when purchasing plants from the store
- plant death from improper care

### Core Concepts
Concepts demonstrated in MGOT include:
- Java
- Object-Oriented Programming principles
- Object Serialization
- Web-scrapping (for the database of plants)
- Recursion (for web-scrapping)
- New-Load-Save system for multiple users
- Proper choice and use of Data Structures (HashMap, ArrayList, etc)
- Logistic Growth

## Instructions
A makefile is provided for the user. Using a Linux-based command shell, clone the git repository. Then, type 'make run' inside the directory. This will compile and run the program.

# Sources
Plant information was provided by Tropicopia, and disease information was provided by PennState.
