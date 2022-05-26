import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIPanelMazeCell extends JPanel implements MouseListener{

    private int index;
    private MazeCell cell;

    public UIPanelMazeCell(MazeCell currentCell, int index) {
        new JPanel();
        this.index = index;
        this.cell = currentCell;
        int top = currentCell.valueAbove * 5;
        int left = currentCell.valueLeft * 5;
        int bottom = currentCell.valueBelow * 5;
        int right = currentCell.valueRight * 5;

        setBorder(BorderFactory.createMatteBorder(top,left,bottom,right,Color.BLACK));
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // edit the current cell to this one
        MazeCell newCell = UI_new.getInstance().display.currentSelection;
        UI_new.getInstance().display.currentMaze.EditCell(UI_new.getInstance().display.currentSelection,this.index);
        UI_new.getInstance().display.UpdateDisplay();
        System.out.println("This cell should be Above = " + newCell.valueAbove + "Below = " + newCell.valueBelow + "Left = " + newCell.valueLeft + "Right = " + newCell.valueRight);
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
