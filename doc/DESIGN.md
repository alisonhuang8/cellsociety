
# Design

## Introduction

The goal of our program is to construct a platform on which various different simulations can be displayed. These simulations are all reflective of some aspect of nature, whether that be patterns of segregation among two disparate groups of individuals or forest fire cycles. Our primary design goals consist of a main class that initiates the execution of our program, a model superclass that is extended to be used as the backbone of 4 specific simulations, and a unit superclass that is extended for each of the various units that are the pawns of the different simulations.

# Overview

We made a superclass called Model which will be called by the main method to update all of the scenes because they share many of the same/similar methods and it seemed like it would be efficient to have all of the scenes be set from one core class (for simplicity). We made individual subclasses (Fire, Wator, Segregation, and Life) to implement the algorithms because although they share the same set of methods (like updateScene or getScene etc), the way that they implement them is different. For updating the simulations, we have decided to use Unit objects which are capable of displaying their state and which are given colors as instance variable. 

Here are the classes and their expected methods/instance variables:

	Main: 

	makes an instance of Model and prompts model to set a menu

	Model: 

	abstract public getNextScene() → calls the subclasses to have them figure out the next frame of the simulation
	private setNextScene() → sets the Stage to the new scene after updating the cells
	public abstract getRules() → calls the subclasses to get a text field for the rules
	private setupButtons() → sets buttons to start, stop, clear, or reset the model 

	Fire extends Model:

	Constructor sets the start field based on the starting cell dimensions and the values passed for fire spreading chance

	reset(): resets the grid
	getNextScene(): runs through the 2D array of Units, filling in a new 2D array of units based on the algorithm. Returns the new 2D array in the form of a root which can be added to the scene

	Wator extends Model:

	Constructor sets the start field based on the starting cell dimensions and the values passed for fire spreading chance

	reset(): resets the grid
	getNextScene(): runs through the 2D array of Units, filling in a new 2D array of units based on the algorithm. Returns the new 2D array in the form of a root which can be added to the scene

	Segregation extends Model:

	Constructor sets the start field based on the starting cell dimensions and the values passed for fire spreading chance

	reset(): resets the grid
	getNextScene(): runs through the 2D array of Units, filling in a new 2D array of units based on the algorithm. Returns the new 2D array in the form of a root which can be added to the scene

	Game of Life extends Model:

	Constructor sets the start field based on the starting cell dimensions and the values passed for fire spreading chance

	reset(): resets the grid
	getNextScene(): runs through the 2D array of Units, filling in a new 2D array of units based on the algorithm. Returns the new 2D array in the form of a root which can be added to the scene

	Unit extends rectangle and has a constructor which assigns the x, y, width, and height
	Unit also has the following public abstract methods:
	isBurning(), isBurnt(), isBlank(), isPredator(), isPrey(), isAlive(), isType1(), isType2()

	The Following classes extend Unit:
		Burning, Burnt, Blank, Predator, Prey, Alive, Type1, Type2
	Each class has a global color variable and implements each Unit abstract method returning true or false based on whether the class is the method being called. Example, Predator returns true for isPredator() and false for everything else.
	
# User Interface

The home screen will have 4 buttons for the user to choose from, called “Learn more”. Clicking on one of these buttons will lead the user to a screen that displays information and background on that particular simulation. Under the text, there will be two buttons, one (“Back”) that will bring the user back to the home screen, and another (“Start”) that will begin the simulation.

Game of Life: we will include several buttons on the screen available to the user. “How to play” will display the instructions and details on how to use the simulation; “Begin” will initiate the simulation; “Pause” will pause the animation; “Reset” will reset the simulation to the empty state. The user will need to choose the cells in the grid that are the beginning live cells, as this is what will be used to run the simulation. To do this, the user can click on cells in the grid. Once they’re done, they press “Begin”.

Fire: Buttons - “How to play” will display the instructions and details on how to use the simulation; “Begin” will initiate the simulation; “Pause” will pause the animation; “Reset” will reset the simulation to the empty state. The user will be able to input a percentage in a text box (0-100 inclusive). He/she will then click on one cell in the grid to indicate where they want to start the fire.

Predator and Prey: Buttons - “How to play” will display the instructions and details on how to use the simulation; “Begin” will initiate the simulation; “Pause” will pause the animation; “Reset” will reset the simulation to the empty state. The user will input how many predators and how many prey to start off with, and the starting locations of these will be randomized.

Segregation: Buttons - “How to play” will display the instructions and details on how to use the simulation; “Begin” will initiate the simulation; “Pause” will pause the animation; “Reset” will reset the simulation to the empty state. Other information that needs to be inputted by the user include a percentage (0-100 inclusive) of their tolerance for their neighbors being different, percent of the grid that they want to be Type 1, percent of the grid that they want to be Type 2, and percent of the grid they the want to leave blank. (Type 1 % + Type 2 % + Blank % must add up to 100). 

# Design Details 

The main class CellSociety will have a start method that sets up the home screen: there will be 4 panels each representing one of the four simulations. When any of these panels are clicked on, the background information of that particular simulation will be displayed, along with the “Back to home” button and the “Start simulation” button. If the user clicks on the “Start simulation” button, an instance of the model superclass will be created and we will pass in the stage to the constructor of the model superclass.

The model superclass will have a public abstract method called getNextScene() which will acquire the new board for the current simulation by calling the implemented method in the subclasses. The superclass will also have have a private setNextScene() method that will take the new board that getNextScene() has just acquired and set it as the scene of the stage. There will also be a private method called setUpButtons() that will create the 4 buttons described in the User Interface section (“How to play”, “Begin”, “Pause”, “Reset”). The “How to play” button will depend on a separate abstract public method called getRules() which will acquire a set of text instructions from the subclasses; then upon pressing the “How to play” button, these instructions will be displayed. We also want to include a “Return to Home” button on every one of the simulations, so that players can return back to that main screen whenever they would like. In the superclass, we will create an instance of a Timeline that will add frames to an animation created by the getNextScene() method (this is our step function). There will also be an abstract method called checkNeighbors() that will be implemented depending on which model is chosen. The checkNeighbors() method will return a value corresponding to whether or not the state of the cell should change in the next step, and this will be decided algorithmically depending on which instance of the model superclass we are in.

There will be a superclass called Unit that extends Rectangle (built-in Java package). Then depending on which simulation subclass is created, different subclasses of Unit will also be created. These subclasses include Burning, Burnt, Predator, Prey, Blank, Alive, Type1, and Type2.  These subclasses will contain methods that will return a variable pertaining to the color of the cell and a boolean pertaining to what type of cell it is.

Game of Life subclass: We will need to implement getNextScene() since it’s an abstract method. There will be a currentState grid and a nextState grid, both with the same dimensions and populated with instances of the Unit superclass. The getNextScene() method will loop through all the cells in our currentState grid and determine the state of that cell for the next step using a checkNeighbors method. This next state will be used to create an instance of the Unit class and will be placed in the same spot but in the nextState grid. At the end of the getNextScene() method, currentState will be set equal to the nextState grid. 

Predator and Prey subclass, Fire subclass, and the Segregation subclass will operate in the same way as the Game of Life subclass with two grids populated by instances of the Unit superclass. The only differences lie in how the checkNeighbors method calculates the next state of each cell. 

### Use Cases
	We plan on having two matrices. One to represent the current state and one to represent the next state. We will fill in the next state after looping through the current. We will then set the current equal to the next and return the current for display.
	
	Set a simulation parameter: set the value of a parameter, probCatch, for a simulation, Fire, based on the value given in an XML fire
	
	Instead of manually setting the parameter probCatch by asking for user input, this value can also be set by storing it in a file.  This file can then be read from a scanner and the intended value can be extracted and saved as a private variable within the Fire class.

	Switch simulations: use the GUI to change the current simulation from Game of Life to Wator

	A return to home button can be included in the Game of Life screen that when pressed by the user, will set the stage to the scene from the homepage.  

## Design Considerations 


	1. Making a Unit superclass, which all of the unit cell types extend. Cells would be able to modify themselves.

	Pros: 

	Makes the design more readable
	
	Eliminates repetition between each unit cell type class

	Cons:
	
	Makes it hard to make modifications to individual units simultaneously (versus sequentially)

	An extra class to keep track of

	2. Having the subclasses display a scene versus having the main class display the scene.  Decided to have main class display scene

	Pros:

	Prevents a problem with multiple scenes being displayed at the same time

	Do not have to worry about multiple frames trying to be added to the animation

	Means that the stage does not have to be passed down the hierarchy to each model type class as a parameter

	Cons:

	Requires returning scenes after each step

## Team Responsibilities
We have agreed to work on all four simulations together, and complete one of them at a time. Starting with Game of Life, we will make sure the main class and Model superclass are functioning properly and then move into writing the checkNeighbors method. Gideon is focusing on composing the main class, Faith will take care of the Model superclass, and Alison will focus on the supplementary components of the aforementioned classes. As a whole, we intend to all be working on making the higher components of the hierarchy first (the superclasses). This is the most important, as the superclasses are what gets extended into the lower levels. After that, we will be focusing on getting the individual components (the helper methods) to function properly. Our strategy is to make sure each of the simulations are running properly one at a time. Once this is done, we will double back and clean up certain code snippets and make it look better.


