
package minesweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.plaf.metal.MetalButtonUI;

public class Handler {
        
    private ArrayList<Cell> current = new ArrayList<Cell>();
    private ArrayList<Cell> queue = new ArrayList<Cell>();
    private static int flaggedCells=0; 
    private StretchIcon mine;
    private StretchIcon explodedmine;
    StretchIcon flag=new StretchIcon(getClass().getResource("/minesweeper/flag-6.png"));
    
    public void click(Cell cell) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        int discoveredCells=0;
        
        
        if(Minesweeper.SKIN==0){//Choose mine colour based on settings
            mine=new StretchIcon(getClass().getResource("/minesweeper/mine26.png"));
           explodedmine=new StretchIcon(getClass().getResource("/minesweeper/mine36.png"));
        }else
        {
            mine=new StretchIcon(getClass().getResource("/minesweeper/mine6.png"));
            explodedmine=new StretchIcon(getClass().getResource("/minesweeper/mine46.png"));
        }
        boolean loose=false;
        if(!cell.isFlagged()){

            cell.setEnabled(false); //THE CELL IS NOT YET ENABLED, NOT YET CHECKED
            cell.setDiscovered(true); //Once the cell is clicked, it is discovered

            int position = cell.getPosition();

            switch (cell.getType()) {
                case 0:
                    // If the cell is EMPTY
                    if(position < Minesweeper.GRIDSIZE){
                        // If the cell is in FIRST ROW
                        if(position % Minesweeper.GRIDSIZE == 0){
                            // TOP - LEFT CORNER CELL
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)));
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)));
                            queue.add(Grid.cellGrid.get((position + 1)));
                        }
                        else if(position % Minesweeper.GRIDSIZE == Minesweeper.GRIDSIZE -1){
                            // TOP - RIGHT CORNER CELL
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)));
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)));
                            queue.add(Grid.cellGrid.get((position - 1)));
                        }
                        else{
                            // Other cells in FIRST ROW
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)));
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)));
                            queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)));
                            queue.add(Grid.cellGrid.get((position - 1)));
                            queue.add(Grid.cellGrid.get((position + 1)));
                        }
                        
                    }
                    else if(position >= (Minesweeper.GRIDSIZE) * (Minesweeper.GRIDSIZE -1) ){
                        // If the cell is in LAST ROW
                        if(position % Minesweeper.GRIDSIZE == 0){
                            // BOTTOM - LEFT CORNER CELL
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)));
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)));
                            queue.add(Grid.cellGrid.get((position + 1)));
                        }
                        else if(position % Minesweeper.GRIDSIZE == Minesweeper.GRIDSIZE -1){
                            // BOTTOM - RIGHT CORNER CELL
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)));
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)));
                            queue.add(Grid.cellGrid.get((position - 1)));
                        }
                        else{
                            // Other cells in LAST ROW
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)));
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)));
                            queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)));
                            queue.add(Grid.cellGrid.get((position - 1)));
                            queue.add(Grid.cellGrid.get((position + 1)));
                        }
                    }
                    else if(position % Minesweeper.GRIDSIZE == 0){
                        // If the cell is in FIRST COLUMN
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                    }
                    else if(position % Minesweeper.GRIDSIZE == Minesweeper.GRIDSIZE - 1){
                        //If the cell is in LAST COLUMN
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                    }
                    else{
                        //Normal position cells
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)));
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)));
                        queue.add(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)));
                        queue.add(Grid.cellGrid.get((position - 1)));
                        queue.add(Grid.cellGrid.get((position + 1)));
                    }   break;
                case 2:
                    // If the cell is NUMBER
                    int dangerCount = 0;
                    if(position < Minesweeper.GRIDSIZE){
                        //FIRST ROW CELLS
                        if(position % Minesweeper.GRIDSIZE == 0){
                            //TOP LEFT CORNER CELL
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)).getType() == 1)
                                dangerCount++; //If the cell next to you is mine, increase number
                            
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)).getType() == 1)
                                dangerCount++; //If the cell next to you is mine, increase number
                            
                            if(Grid.cellGrid.get((position + 1)).getType() == 1)
                                dangerCount++; //If the cell next to you is mine, increase number
                        }
                        else if(position % Minesweeper.GRIDSIZE == Minesweeper.GRIDSIZE - 1){
                            //TOP RIGHT CORNER CELL
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - 1)).getType() == 1)
                                dangerCount++;
                        }
                        else{
                            //Other first row cells
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position + 1)).getType() == 1)
                                dangerCount++;
                        }
                        
                    }
                    else if(position >= (Minesweeper.GRIDSIZE * (Minesweeper.GRIDSIZE -1))){
                        //LAST ROW CELLS
                        if(position % Minesweeper.GRIDSIZE == 0){
                            //BOTTOM LEFT CORNER CELL
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position + 1)).getType() == 1)
                                dangerCount++;
                        }
                        else if(position % Minesweeper.GRIDSIZE == Minesweeper.GRIDSIZE - 1){
                            //BOTTOM RIGHT CORNER CELL
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - 1)).getType() == 1)
                                dangerCount++;
                        }
                        else{
                            //Other last row cells
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position - 1)).getType() == 1)
                                dangerCount++;
                            
                            if(Grid.cellGrid.get((position + 1)).getType() == 1)
                                dangerCount++;
                        }
                    }
                    else if(position % Minesweeper.GRIDSIZE == 0){
                        //FIRST COLUMN CELLS
                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position + 1)).getType() == 1)
                            dangerCount++;
                        
                    }
                    else if(position % Minesweeper.GRIDSIZE == Minesweeper.GRIDSIZE - 1){
                        //LAST COLUMN CELLS
                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position - 1)).getType() == 1)
                            dangerCount++;
                    }
                    else{
                        //NORMAL POSITION CELLS
                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE - 1)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position - Minesweeper.GRIDSIZE + 1)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE - 1)).getType() == 1)
                            dangerCount++;

                        if(Grid.cellGrid.get((position + Minesweeper.GRIDSIZE + 1)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position + 1)).getType() == 1)
                            dangerCount++;
                        
                        if(Grid.cellGrid.get((position - 1)).getType() == 1)
                            dangerCount++;
                    }   
                        switch(dangerCount){
                            case 1:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(0,0,255);
                                    }
                                });
                                break;
                            case 2:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(0,102,0);
                                    }
                                });
                                break;
                            case 3:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(255,0,0);
                                    }
                                });
                                break;
                            case 4:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(0,0,102);
                                    }
                                });
                                break;
                            case 5:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(255,0,255);
                                    }
                                });
                                break;
                            case 6:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(0,204,102);
                                    }
                                });
                                break;
                            case 7:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(255,128,0);
                                    }
                                });
                                break;
                            case 8:
                                cell.setUI
                                    (new MetalButtonUI(){
                                    protected Color getDisabledTextColor(){
                                        return new Color(128,128,128);
                                    }
                                });
                                break;
                            default:
                                cell.setForeground(Color.YELLOW);
                        }
                        cell.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
                        cell.setText(String.valueOf(dangerCount)); // Print the number on number cell
                        
                        
                    break;
                case 1:
                    // If the cell is MINE
                    loose=true;
                    cell.setBackground(Color.RED);
                    cell.setOpaque(true);
                    for(int x =0; x<Grid.cellGrid.size(); x++){
                        Grid.cellGrid.get(x).setEnabled(false); //Disable ALL cells
                        Grid.cellGrid.get(x).setText(""); //Print NOTHING on all cells (temp)
                        
                        if(Grid.cellGrid.get(x).getType() == 1){
                            //reveal all mines
                            Grid.cellGrid.get(x).setIcon(explodedmine);
                            Grid.cellGrid.get(x).setFlagged(true);
                            Grid.cellGrid.get(x).setEnabled(true);
                        }
                        else{
                            Grid.cellGrid.get(x).setIcon(null);//remove icons from all cells (temp)
                        }
                        
                    }   //cell.setIcon (mineHit); // Print * on the MINE THE PLAYER HIT icon insertion
                    break;
                default:
                    break;
            }

            for(int x = 0; x <queue.size(); x++){
                if(!queue.get(x).isDiscovered()){
                    current.add(queue.get(x));
                    queue.get(x).setDiscovered(true);

                }
            }
            queue.clear(); //Ready for next use

            while(!current.isEmpty()){ // Click function
                Cell temp = current.get(0);
                current.remove(0);
                temp.clickButton();

            }
            
            for(int x = 0; x < Grid.cellGrid.size(); x++){
                if(Grid.cellGrid.get(x).isDiscovered()){
                    discoveredCells++;
                }
            }
            
            if(discoveredCells==Grid.cellGrid.size()- Minesweeper.MINECOUNT){ //win Condition
                for(int x=0;x < Grid.cellGrid.size();x++){
                    if(Grid.cellGrid.get(x).getType()==1){
                        
                        Grid.cellGrid.get(x).setIcon(mine);
                        Grid.cellGrid.get(x).setFlagged(true);
                      
                        
                    }else{
                        Grid.cellGrid.get(x).setEnabled(false);
                        Grid.cellGrid.get(x).setText(":) ");
                    }
                }
                new endMatch(true);
                Grid.cellGrid.clear();
            }
            if(loose){
                endMatch endMatch = new endMatch(false);
                
                Grid.cellGrid.clear();//Empty Grid after game ends
                
            }
        }
    }
    
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
    
    
    public static void JButtonListener(JButton button){ //STYLING Jbuutons
          Color def=button.getForeground();//Save default color
          button.setOpaque(false);
          button.setContentAreaFilled(false);
          button.setBorderPainted(false);
          button.setBackground(new Color(93,179,245));
          button.addMouseListener(new java.awt.event.MouseAdapter(){
           
         
            @Override
            public void mouseEntered(MouseEvent e){
                
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBorderPainted(false);
                button.setForeground(Color.red);
                
            
            }
            
            @Override
            public void mouseExited(MouseEvent e){
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setForeground(def);
                }
            
            @Override
            public void mousePressed(MouseEvent e){
                 button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(true);
            
            }
            
            @Override
            public void mouseReleased(MouseEvent e){
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setForeground(def);
            }
        });
        
     
     
     }  
}
