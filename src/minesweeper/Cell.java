
package minesweeper;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Cell extends JButton {
    
    private int type; 
    private int position;
    private boolean discovered;
    private boolean flaged;
    
    private Handler handler;

    /**
     *
     * @param type
     * @param position
     * @param discovered
     * @param flaged
     * @param handler
     */
    public Cell(int type, int position, boolean discovered, boolean flaged,Handler handler) {
        this.type = type;
        this.position = position;
        this.discovered = discovered;
        this.flaged = flaged;
        this.handler = handler;
        
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
    
    

    public int getType() {
        return type;
        /*TYPE 0: EMPTY
          TYPE 1: MINE
          TYPE 2: NUMBER
        */
    }

    public int getPosition() {
        return position;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setFlagged(boolean flaged) {
        this.flaged = flaged;
    }

    public boolean isFlagged() {
        return flaged;
    }
    
    public void clickButton() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        handler.click(this);
    }

    public void rightClickButton(){
        handler.rightClick(this);
    }
    
    
    
    
}

