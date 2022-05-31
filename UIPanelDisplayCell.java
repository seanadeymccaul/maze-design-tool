import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UIPanelDisplayCell extends JPanel implements MouseListener {

    public MazeCell cell;
    public int index;
    protected int borderWidth;

    public UIPanelDisplayCell(MazeCell cell, int index){
        this.cell = cell;
        this.index = index;
        this.borderWidth = 6 - UI_new.getInstance().display.currentMaze.cellCount / 2000;
        new JPanel();
        addMouseListener(this);
    }
    
    public void CreateBorder() {
        setBorder(BorderFactory.createMatteBorder(cell.getWallAbove() * borderWidth, cell.getWallLeft() * borderWidth,
                cell.getWallBelow() * borderWidth, cell.getWallRight() * borderWidth, Color.BLACK));

    }

    public void PaintCell(){
        if (this.cell.getValue() == 3){
            setBackground(Color.RED);
        } else {
            setBackground(Color.WHITE);
        }
    }

    public void InsertImage(JLabel picLabel) throws IOException {
        add(picLabel);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            UI_new.getInstance().display.currentMaze.InsertLogo("mcdonalds.png",this.index,1,1);
            UI_new.getInstance().display.UpdateDisplay();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            UI_new.getInstance().display.UpdateDisplay();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        if (UI_new.getInstance().display.currentSelection != null) {
            UI_new.getInstance().display.currentMaze.ReplaceCell(UI_new.getInstance().display.currentSelection, this.index);
            try {
                UI_new.getInstance().display.UpdateDisplay();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {
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
