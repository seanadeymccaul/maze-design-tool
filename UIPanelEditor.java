import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class UIPanelEditor extends JPanel implements MouseListener {

    public boolean current;
    private MazeCell currentSelection;
    public MazeCell GetCurrentSelection(){return currentSelection;}
    public void SetCurrentSelection(MazeCell m){currentSelection = m;}
    private JPanel option1;
    private JPanel option2;
    private JPanel option3;
    private JPanel option4;
    private JPanel option5;
    private JPanel option6;

    public UIPanelEditor(){

        new JPanel();
        setBorder(BorderFactory.createTitledBorder("Maze Editor"));
        setPreferredSize(new Dimension(150,300));
        setLayout(new BorderLayout());

        // Set up selection panel
        JPanel selectionPanel = new JPanel(); selectionPanel.setLayout(new GridLayout(2,2,30,20));
        option1 = new JPanel(); option1.setBorder(BorderFactory.createMatteBorder(5,0,5,0, Color.BLACK));
        option2 = new JPanel(); option2.setBorder(BorderFactory.createMatteBorder(0,5,0,5, Color.BLACK));
        option3 = new JPanel(); option3.setBorder(BorderFactory.createMatteBorder(5,5,0,0, Color.BLACK));
        option4 = new JPanel(); option4.setBorder(BorderFactory.createMatteBorder(5,0,0,5, Color.BLACK));
        option5 = new JPanel(); option5.setBorder(BorderFactory.createMatteBorder(0,5,5,0, Color.BLACK));
        option6 = new JPanel(); option6.setBorder(BorderFactory.createMatteBorder(0,0,5,5, Color.BLACK));
        option1.addMouseListener(this); selectionPanel.add(option1);
        option2.addMouseListener(this); selectionPanel.add(option2);
        option3.addMouseListener(this); selectionPanel.add(option3);
        option4.addMouseListener(this); selectionPanel.add(option4);
        option5.addMouseListener(this); selectionPanel.add(option5);
        option6.addMouseListener(this); selectionPanel.add(option6);
        selectionPanel.setPreferredSize(new Dimension(300,300)); add(selectionPanel,BorderLayout.CENTER);
        ClearBackgrounds();

        // Add padding
        JPanel paddingW = new JPanel(); paddingW.setPreferredSize(new Dimension(100,50)); add(paddingW,BorderLayout.WEST);
        JPanel paddingE = new JPanel(); paddingE.setPreferredSize(new Dimension(100,50)); add(paddingE,BorderLayout.EAST);


    }

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
            currentSelection = new MazeCell(1,0,0,1,1);
            option1.setBackground(Color.GREEN);
        } else if (e.getSource() == option2){
            currentSelection = new MazeCell(1,1,1,0,0);
            option2.setBackground(Color.GREEN);
        } else if (e.getSource() == option3){
            currentSelection = new MazeCell(1,1,0,1,0);
            option3.setBackground(Color.GREEN);
        } else if (e.getSource() == option4){
            currentSelection = new MazeCell(1,0,1,1,0);
            option4.setBackground(Color.GREEN);
        } else if (e.getSource() == option5){
            currentSelection = new MazeCell(1,1,0,0,1);
            option5.setBackground(Color.GREEN);
        } else if (e.getSource() == option6){
            currentSelection = new MazeCell(1,0,1,0,1);
            option6.setBackground(Color.GREEN);
        }
        current = true;
        UI.getInstance().imageSelect.current = false;
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
