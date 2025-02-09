
package minesweeper;
import javax.swing.*;
import java.util.*;
import java.awt.*;


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
     
     private void addCells(){ //Placing cells in Grid (JPanel)
         for(int i=0; i<cellGrid.size(); i++){
             add(cellGrid.get(i));
         }
     }
     
     public void clearCells(){
         cellGrid.clear();
     }
}
