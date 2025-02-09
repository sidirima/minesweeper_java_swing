
package minesweeper;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
public class Minesweeper {
    private int difficulty;
    private int skin;
    public static int GRIDSIZE,
            MINECOUNT;
    public static  double WIDTH , HEIGHT ;
    
    public static int SKIN;
    private Handler handler = new Handler();

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
                    HEIGHT=1080   ;     
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

    public int getSkin() {
        return skin;
    }
             
    public static void main(String[] args) {
        MainMenu menu =new MainMenu();
        
        ;
         
    }

    public int getDifficulty() {
        return difficulty;
    }
    

    public static void setGRIDSIZE(int GRIDSIZE) {
        Minesweeper.GRIDSIZE = GRIDSIZE;
    }

  
    
 
    
}
