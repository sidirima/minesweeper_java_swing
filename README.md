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

### Starting a New Game
Upon clicking the "New Game" button on the Main Menu, a new Minesweeper object is generated. This object takes values from the "Settings" selections. This is performed with the .getValue() function for _difficulty_ and .getSelectedIndex() for _mineskin_.

> [!NOTE]
> Difficulty is set by the user, as the number of cells and mineskin is an option to select between grey and red mine color. More about this will be discussed on the "Options" menu.

The code below shows the action performed when player pushes the "New Game" button (from src/Minesweeper/MainMenu.java).
```Java
private void newgameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newgameActionPerformed
    Minesweeper game = new Minesweeper(this.difficulty.getValue(),this.mineskin.getSelectedIndex());
    this.dispose();
```

The constructor of the class is quite simple. First of all, the Screensize is calculated, in order to define the game Window. The width and height of the window are constants dependent on the number of cells on the grid. This is handled via a _switch_ statement.
Then, the number of mines is calculated, in order to not be more than 10% of the cells. Code for the constructor is shown in the picture below.

<p align = "center">
  <img width="600" alt="image" src="https://github.com/user-attachments/assets/a1874801-bbb2-4abb-8c7b-c59e004be277" />
</p>

### Window Class
The class' purpose is to generate a window for the game. This class **extends** the JFrame class and contains parameters conserning the window dimensions, cells for _Grid_ table and the _Handler_ class. Those classes will be discussed later. Also, it is responsible for showing the number of mines and the flag icon on window title bar. A part of code for this class is shown in the picture below.

<p align = "center">
  <img width="700" alt="image" src="https://github.com/user-attachments/assets/90e413d5-91b7-4419-ae28-8f3a6c402329" />
</p>

### Grid Class
The _Grid_ is a JPanel type square table. The dimension of the table is defined by the _gridsize_ variable. Also on the class there are arrayLists, one of type _Cell_ to define positions of table cells and one type _integer_ to define random cells for mines (using Math.random() function).
```Java
public class Grid extends JPanel{
     private int bound=Minesweeper.GRIDSIZE*Minesweeper.GRIDSIZE; //creates the grid
     private boolean picked = false; //flag
     
     private ArrayList<Integer> mines=new ArrayList<Integer>(); 
     public static ArrayList<Cell> cellGrid = new ArrayList<Cell>();

   
     public Grid(GridLayout g, Handler h){
         super(g);
         createCells(h);
         addCells();
         
     }
```

There is a method called _CreateCells()_ that checks every adjacent cell in order to define cell type for each cell.
> [!NOTE]
> There are 3 cell types: empty, mine or number. Their differences will be discussed later.
```Java
public void createCells(Handler h){
         for(int i=1;i<=Minesweeper.MINECOUNT;i++){
             while(!picked){
                    int minePosition = (int)(Math.random()*bound); //generate rand number for mine position
                    if(!mines.contains(minePosition))
                    {
                        mines.add(minePosition);
                        picked= true;
                    }
             }
           picked=false;  
         }
         
         for(int i=0;i<bound;i++){
             if(mines.contains(i)){
                 cellGrid.add(new Cell(1,i,false,false,h));//creates a TYPE1 cell
             }
             else if(i % Minesweeper.GRIDSIZE==0){ //CHECK LEFT EDGE
                     if(mines.contains(i-Minesweeper.GRIDSIZE)
                     || mines.contains(i-Minesweeper.GRIDSIZE+1)
                     || mines.contains(i+1)
                     || mines.contains(i+Minesweeper.GRIDSIZE)
                     || mines.contains(i+Minesweeper.GRIDSIZE+1)){
                 cellGrid.add(new Cell(2,i,false,false,h));//creates a TYPE2 cell
                    }
                     else {
                     cellGrid.add(new Cell(0,i,false,false,h));//creates a TYPE0 cell
                     }
             }
             else if(i % Minesweeper.GRIDSIZE== Minesweeper.GRIDSIZE-1){ //CHECK RIGHT EDGE
                     if(mines.contains(i-Minesweeper.GRIDSIZE-1) 
                     || mines.contains(i-Minesweeper.GRIDSIZE)
                     || mines.contains(i-1)
                     || mines.contains(i+Minesweeper.GRIDSIZE-1)
                     || mines.contains(i+Minesweeper.GRIDSIZE))
                     {
                 cellGrid.add(new Cell(2,i,false,false,h));//creates a TYPE2 cell
                     }
              
                     else{
                        cellGrid.add(new Cell(0,i,false,false,h));//creates a TYPE0 cell
                    }
             }
             else if(mines.contains(i-Minesweeper.GRIDSIZE-1)
                     || mines.contains(i-Minesweeper.GRIDSIZE)
                     || mines.contains(i-Minesweeper.GRIDSIZE+1)
                     || mines.contains(i-1)
                     || mines.contains(i+1)
                     || mines.contains(i+Minesweeper.GRIDSIZE-1)
                     || mines.contains(i+Minesweeper.GRIDSIZE)
                     || mines.contains(i+Minesweeper.GRIDSIZE+1)){
                 cellGrid.add(new Cell(2,i,false,false,h));//creates a TYPE2 cell
             }
             else{
                 cellGrid.add(new Cell(0,i,false,false,h));//creates a TYPE0 cell       
             }
         }
     }
```
Finally, there is also a method called _addCells()_ responsible for placing the CellGrid objects in the Grid object.
```Java
     private void addCells(){ //Placing cells in Grid (JPanel)
         for(int i=0; i<cellGrid.size(); i++){
             add(cellGrid.get(i));
         }
     }
}
```
