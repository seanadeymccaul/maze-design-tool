import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * A JPanel object that takes the data of a MazeDataCell and index to create specific JPanel
 */
public class MazeDisplayCell extends JPanel implements MouseListener {

    public MazeDataCell cell;
    public int index;

    public MazeDisplayCell(MazeDataCell cell, int index){
        this.cell = cell;
        this.index = index;
        new JPanel();
        addMouseListener(this);
    }

    public void RemoveBorder(){
        setBorder(BorderFactory.createMatteBorder(0,0,0,0,Color.BLACK));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        int cellHeight = 2000/ UI.getInstance().display.GetDisplayedMaze().GetYDimension();
        int cellWidth = 2000/ UI.getInstance().display.GetDisplayedMaze().GetXDimension();
        int borderWidth = 6 - (UI.getInstance().display.GetDisplayedMaze().GetXDimension()*UI.getInstance().display.GetDisplayedMaze().GetYDimension()) /2000;
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        if (cell.getWallAbove() == 1){
            g2d.fillRect(0,0,cellWidth,borderWidth);
        }
        if (cell.getWallBelow() == 1){
            g2d.fillRect(0,cellHeight-borderWidth,cellWidth,borderWidth);
        }
        if (cell.getWallLeft() == 1){
            g2d.fillRect(0,0,borderWidth,cellHeight);
        }
        if (cell.getWallRight() == 1){
            g2d.fillRect(cellWidth-borderWidth,0,borderWidth,cellHeight);
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked");
        System.out.println("" + UI.getInstance().editor.current);
        // If a panel has been selected for insert
        if (UI.getInstance().editor.current){
            System.out.println("Above, below, left right = " + UI.getInstance().editor.GetCurrentSelection().getWallAbove() +
                    UI.getInstance().editor.GetCurrentSelection().getWallBelow() +
                    UI.getInstance().editor.GetCurrentSelection().getWallLeft() +
                    UI.getInstance().editor.GetCurrentSelection().getWallRight());
            UI.getInstance().display.GetDisplayedMaze().ReplaceCell(UI.getInstance().editor.GetCurrentSelection(),this.index);
            try {
                UI.getInstance().display.UpdateDisplay();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // If a image has been selected for insert
        else if (UI.getInstance().imageSelect.current){
            //
            try {
                UI.getInstance().imageSelect.GetSelectedImage().SetIndex(this.index);
                UI.getInstance().display.GetDisplayedMaze().GetImageList().add(UI.getInstance().imageSelect.GetSelectedImage());
                UI.getInstance().imageSelect.current = false;
                UI.getInstance().display.UpdateDisplay();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        }


    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
