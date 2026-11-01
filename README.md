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

[!NOTE]
If the user flags a cell and later discovers that does not contain a mine, it can be unflagged by right-clicking it again.
