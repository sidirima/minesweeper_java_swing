/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package minesweeper;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author User
 */
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
    
    
     public static  void update(int flagged){
       
        frame.setTitle(title +"Nάρκες:"+Minesweeper.MINECOUNT + "- Σημαίες:" + flagged);
    }

    public static JFrame getFrame() {
        return frame;
    }
     
     public static void disposeframe(){
         frame.dispose();
     }
}
