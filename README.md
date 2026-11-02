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
Then, the number of mines is calculated, in order to not be more than 10% of the cells. Code is shown below.
```Java
public Minesweeper(int difficulty,int skin){
    Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
    this.skin=skin;
    this.difficulty=difficulty;
    switch(difficulty){
        case 1:
                GRIDSIZE=10;
                WIDTH=480;
                HEIGHT=480;
                break;
        case 2:
                GRIDSIZE=20;
                WIDTH=1080;
                HEIGHT=720;
                break;
        case 3:
                GRIDSIZE=30;
                WIDTH=1080;
                HEIGHT=1080;     
                break;
        default:
                break;            
    }
    SKIN=this.skin;
    MINECOUNT = (int)Math.round(GRIDSIZE * GRIDSIZE * 0.1);
    Window window = new Window(WIDTH,HEIGHT,GRIDSIZE,"Minesweeper",this,handler);
    if(difficulty==3){
        window.getFrame().setExtendedState(JFrame.MAXIMIZED_BOTH);
    }  
    Window.update(0);
    }
```

### Window Class
The class' purpose is to generate a window for the game. This class **extends** the JFrame class and contains parameters conserning the window dimensions, cells for _Grid_ table and the _Handler_ class. Those classes will be discussed later. Also, it is responsible for showing the number of mines and the flag icon on window title bar. A part of code for this class is shown in the picture below.

```Java
public class Window {
    
    private static JFrame frame;
    private static String title;
    
    public Window(double width,double height,int gridsize, String title, Minesweeper minesweeper, Handler handler){
        Window.title=title;
        frame= new JFrame(title);
        frame.setPreferredSize(new Dimension((int)width,(int) height));
        frame.setMaximumSize(new Dimension((int)width, (int)height));
        frame.setMinimumSize(new Dimension((int)width, (int)height));
        frame.setLocationRelativeTo(null);
        StretchIcon img=new StretchIcon(getClass().getResource("/minesweeper/flag-6.png"));
        
        frame.setIconImage(img.getImage());
        
        JPanel panel = new Grid(new GridLayout(gridsize, gridsize), handler);
        
         frame.setContentPane(panel);
        frame.pack();
        
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
```

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

### Cell Class
The _Cell_ class is responsible for creating the cells. More specifically, the class describes each cell's parameters, their position in the table, if they have been flagged and in what type they belong to.
There are 3 types of cells:
- Empty cells
- Number cells
- Mine Cells

> [!NOTE]
> The default state of each cell when the game starts is "Undiscovered".

On the image below we can see all the cell types.

<p align = "center">
  <img width="300" alt="image" src="https://github.com/user-attachments/assets/d1ee1ed0-829c-486d-8ac1-ff625faf720d" />
  <br>
  <em>From left to right: Empty, Number, Undiscovered, Mine</em>
</p>

```Java
public class Cell extends JButton {
    
    private int type; 
    private int position;
    private boolean discovered;
    private boolean flaged;
    
    private Handler handler;

    public Cell(int type, int position, boolean discovered, boolean flaged,Handler handler) {
        this.type = type;
        this.position = position;
        this.discovered = discovered;
        this.flaged = flaged;
        this.handler = handler;
```

The class is also responsible for actions upon clicking on a cell. With the use of a _MouseListener()_ object class _Handler_ is called. As we can see on the code below, there is no other action when mouse enters, exits, presses or releases. 

```Java
addMouseListener(new MouseListener(){
    @Override
    public void mouseClicked(MouseEvent e){
        if(SwingUtilities.isRightMouseButton(e))
            rightClickButton();
        else
            try {
                clickButton();
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            } catch (LineUnavailableException ex) {
                Logger.getLogger(Cell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            public void mouseEntered(MouseEvent e){}
            
            public void mouseExited(MouseEvent e){}
            
            public void mousePressed(MouseEvent e){}
            
            public void mouseReleased(MouseEvent e){}
        });
    }
.
.
.
    public void clickButton() throws UnsupportedAudioFileException,
        IOException, LineUnavailableException{
        handler.click(this);
    }

    public void rightClickButton(){
        handler.rightClick(this);
    }
```

### Handler class
Basic function of _Handler_ is to translate each of the player's actions (right clicking, left clicking) in program actions. So, there are two very important methods used in this class: _click()_ and _rightClick()_. Those two methods are called from the MouseListener() as we mentioned above. 

The _click()_ method is responsible for checking each cell's type and optically customize them accordingly. The method also defines the winning or losing conditions and also defines the color of mines selected in "Options" menu. For more information, you can check the [Handler.java](/src/minesweeper/Handler.java) file.

The _rightClick()_ method is an if statement. If the cell that is right-clicked is undiscovered, then it becomes "flagged" and also the flag counter is incremented. If the cell that is right-clicked already has a flag, then the flag disappears and the flag counter is decremented.

In the image below, you can see part of the in-game table, with a flagged cell.
<p align = "center">
  <img width="300" alt="image" src="https://github.com/user-attachments/assets/a712351e-84d4-4d81-b3f2-ce009eb2a447" />
</p>

```Java
public void rightClick(Cell cell){ //Flag & Unflag function
        
    if(!cell.isDiscovered()){
        if(!cell.isFlagged()){
            cell.setFlagged(true);
            cell.setIcon(flag); // FLAG ICON INSERTION
            flaggedCells++;
            Window.update(flaggedCells);
        }else {
            cell.setFlagged(false);
            cell.setIcon(null);
            flaggedCells--;
            Window.update(flaggedCells);
            }
            
      }
}
```

### Winning & Losing Conditions
As mentioned before, the game is won when all the mines on the table have been flagged properly. In order to check that condition, we use the following code block in _Handler_ class.
```Java
if(discoveredCells==Grid.cellGrid.size()- Minesweeper.MINECOUNT){ //win Condition
    for(int x=0;x < Grid.cellGrid.size();x++){
        if(Grid.cellGrid.get(x).getType()==1){
            Grid.cellGrid.get(x).setIcon(mine);
            Grid.cellGrid.get(x).setFlagged(true);

        }
        else{
            Grid.cellGrid.get(x).setEnabled(false);
            Grid.cellGrid.get(x).setText(":) ");
      }
      }
      new endMatch(true);
      Grid.cellGrid.clear();
}
```
There is a different block of code that handles the situation when a game is lost. In _Handler_ class, this is a simple clear function for the cellGrid.

```Java
if(loose){
    endMatch endMatch = new endMatch(false);
    Grid.cellGrid.clear();//Empty Grid after game ends
```

There is a different class, called _endMatch_, that actually handles the two different outcomes of the game. If the game is victorious, the endMatch class calls a JPanel informing the user about his win. On this panel there are options for starting a new game, by selecting difficulty level (number of mines) with a Slider. There are also options for returning to the main menu or exiting the game (closing application). This panel is shown in the image below.

<p align = "center">
  <img width="706" height="496" alt="image" src="https://github.com/user-attachments/assets/188d6b29-defb-42fd-a361-1a92a4a050a7" />
</p>

In case of defeat, the JPanel shown has a different .GIF image and .WAV file than the victorious game. Also the message now informs the user about defeat. All the other elements are the same as the previous case, as we can see in the image below.

<p align = "center">
  <img width="706" alt="image" src="https://github.com/user-attachments/assets/54cde83a-066f-4008-bab5-5fafb70c46ca" />

</p>

## Options Menu
The Options Menu is the second JPanel accessible from the Main Menu. Its purpose is to set preffered difficulty of a new game, using a Slider object. Difficulty is then calculated via the number of mines selected by the user. On the menu, there is also a comboBox associated with _mineskin_. This comboBox allows the player to select the color of the mines for the new game. There are two colors available: red and grey. Default setting is grey. On the menu, there are also 3 JButtons for navigation:
- Return to Main Menu
- Start New Game
- Exit

The image below shows the appearance of the Options Menu, as seen by the user:

<p align = "center">
  <img width="651" height="413" alt="image" src="https://github.com/user-attachments/assets/76471b51-ffa6-4a5b-a979-b48cd30474c7" />
</p>

The code block below shows the action performed when starting a new game with selected parameters from the options menu.
```Java
private void optionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsActionPerformed
        Tabs.setSelectedIndex(1); // Goes to SubMenu
}//GEN-LAST:event_optionsActionPerformed

private void newgameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newgameActionPerformed
    Minesweeper game = new Minesweeper(this.difficulty.getValue(),this.mineskin.getSelectedIndex());
    this.dispose();
}//GEN-LAST:event_newgameActionPerformed
```

## Instructions Menu
The instructions menu is used as a guide for new players to understand the purpose of the game and the way to win. The JPanel includes an HTML code block encapsulated as a JLabel object for the instructions. There is also a single JButton for returning to the Main Menu.
The image below shows that instructions JPanel.

<p align = "center">
  <img width="650" alt="image" src="https://github.com/user-attachments/assets/c0b429e2-b0ab-43e2-bba4-6cb8f82a808a" />
</p>

## Future Improvements
The overall usability of the application is achieved, and it was a fun way familiarizing with Java Swing API. Although, on Beta testing of the game we found some imperfections that could be corrected in future improvements:

1. Solving issue of **terminating an ongoing game and going back to Main Menu** without terminating the program. For the time being, the only way to stop an ongoing game is via pressing the Exit button on the application window.
2. **Main menu image frame needs to be adjusted properly**, because its acting in a weird way when resizing the window or when using the application in monitors with different resolutions.
3. **Difficulty selector Slider when a game ends needs to be isolated from _endMatch_** class and appear in a new window.
4. **Flag counter needs to be cleared and set back to 0 before starting a new game via _endMatch_**, because now it just continues counting up from the last state of the previous game.
5. Add a new window with a **message of exiting the program confirmation** by the user, in order to avoid pressing exit button by mistake.

