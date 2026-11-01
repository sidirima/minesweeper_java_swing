# Minesweeper Game using Java Swing
This is a teamwork semester project for Human - Computer Interaction course on my Integrated Master's studies.

The purpose of the project was to create a clone to the common known Minesweeper game that came along early versions of Windows, like XP. In the image below you
can see an instance of that game.

<p align = "center">
  <img width="185" height="276" alt="image" src="https://github.com/user-attachments/assets/0f25ed0a-be7d-48ba-8fb5-3119d7cb6b36" />
</p>

The project was mostly directed in using Java Swing to create a useful and functioning graphical interface and not so much about the game mechanics.
Therefore, our primary goal was an easy to understand User Interface and User Experience (UI/UX).

## Basic Principles of Minesweeper game
The game is pretty simple. Players must locate all the mines that are randomly located in a square table of a random dimension. The player has 2 options for each cell of the table.
- **Left click with the mouse**: The cell is "opened" and if there is no mine there, a "danger counter" appears on the box as a number. This counter shows the number of mines located in adjacent cells that are not opened. In the picture below for example, the number 2 on the cell shown, shows that 2 of the 4 marked in red circle cells contain mines. If there is a mine on that specific cell, the game is over cause the player hit a mine.
- **Rigth click with the mouse**: The cell is "flagged" to specify the location of a mine.

<p align ="center">
  <img width="350" height="350" alt="image" src="https://github.com/user-attachments/assets/aa090969-fc85-466a-af7b-c69d37bdf98f" />
</p>

Therefore, there are only 2 ways the game is over:
- **Victory**: The player flags all the mines on the table correctly.
- **Defeat**: The player hits a mine. 

> [!NOTE]
> If the user flags a cell and later discovers that does not contain a mine, it can be unflagged by right-clicking it again.

## What is Java Swing?
Swing is a GUI widget toolkit for Java. It is found in Oracle's JFC, which is an API that allows the programmer to create graphical environments for Java programs.

There are various widgets supported in Swing like menus, text fields, tabbed panes, checklists. A sample of the abilities Swing can offer is shown on the image below.

<p align = "center">
  <img width="519" height="394" alt="image" src="https://github.com/user-attachments/assets/2a92fc97-8f56-435f-bf28-d74abec1b77c" />
</p>

Using Swing, developed programs are very lightweight, although due to its limited potentials, they have a 2000s era aesthetic.

## Our Java Swing Implementation

### Main Menu
When opening the application, the player comes across the Main Menu, which is consisted of a single JFrame and a JTabbedPane consisted of 3 different jPanel. Navigation between the jPanels is performed with the use of jButtons. The background image of the main menu is a jLabel type object. The structure of the menu is shown in the left picture below. The right image is a screenshot showing the default main menu.

<p float="left" align = "center">
  <img src="https://github.com/user-attachments/assets/e269e98f-eae0-44ef-b27e-8a6d05d8c3b6" width="400" />
  <img src="https://github.com/user-attachments/assets/25795179-f2a0-4608-9aa8-478aebec4574" width="407" />
</p>

From the main menu, we can select the two other jPanels appart from the default, the one being the "Options" menu and the other is the "Instructions" menu. The first button is used to start a new game.

The main menu is called from the main() class of the Java project.  
> [!TIP]
> The [src](/src) folder contains the Java classes files used for the project.
