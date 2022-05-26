import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIPanelEditor extends JPanel implements MouseListener {

    private JPanel option1;
    private JPanel option2;
    private JPanel option3;
    private JPanel option4;
    private JPanel option5;
    private JPanel option6;

    public UIPanelEditor(){
        // Set up
        setLayout(new GridLayout(1,6,30,10));
        // Add option panels
        option1 = new JPanel(); option1.setBorder(BorderFactory.createMatteBorder(5,0,5,0, Color.BLACK));
        option2 = new JPanel(); option2.setBorder(BorderFactory.createMatteBorder(0,5,0,5, Color.BLACK));
        option3 = new JPanel(); option3.setBorder(BorderFactory.createMatteBorder(5,5,0,0, Color.BLACK));
        option4 = new JPanel(); option4.setBorder(BorderFactory.createMatteBorder(5,0,0,5, Color.BLACK));
        option5 = new JPanel(); option5.setBorder(BorderFactory.createMatteBorder(0,5,5,0, Color.BLACK));
        option6 = new JPanel(); option6.setBorder(BorderFactory.createMatteBorder(0,0,5,5, Color.BLACK));
        option1.addMouseListener(this); add(option1);
        option2.addMouseListener(this); add(option2);
        option3.addMouseListener(this); add(option3);
        option4.addMouseListener(this); add(option4);
        option5.addMouseListener(this); add(option5);
        option6.addMouseListener(this); add(option6);

    }
    //    public Cell(int value, int valueLeft, int valueRight, int valueAbove, int valueBelow){

    public void ClearBackgrounds(){
        option1.setBackground(Color.lightGray);
        option2.setBackground(Color.lightGray);
        option3.setBackground(Color.lightGray);
        option4.setBackground(Color.lightGray);
        option5.setBackground(Color.lightGray);
        option6.setBackground(Color.lightGray);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ClearBackgrounds();
        if (e.getSource() == option1){
            UI_new.getInstance().display.currentSelection = new MazeCell(1,0,0,1,1);
            option1.setBackground(Color.GREEN);
        } else if (e.getSource() == option2){
            UI_new.getInstance().display.currentSelection = new MazeCell(1,1,1,0,0);
            option2.setBackground(Color.GREEN);
        } else if (e.getSource() == option3){
            UI_new.getInstance().display.currentSelection = new MazeCell(1,1,0,1,0);
            option3.setBackground(Color.GREEN);
        } else if (e.getSource() == option4){
            UI_new.getInstance().display.currentSelection = new MazeCell(1,0,1,1,0);
            option4.setBackground(Color.GREEN);
        } else if (e.getSource() == option5){
            UI_new.getInstance().display.currentSelection = new MazeCell(1,1,0,0,1);
            option5.setBackground(Color.GREEN);
        } else if (e.getSource() == option6){
            UI_new.getInstance().display.currentSelection = new MazeCell(1,0,1,0,1);
            option6.setBackground(Color.GREEN);
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
