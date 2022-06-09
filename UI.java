import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UI extends JFrame{

    public UIPanelDisplay display;
    public UIPanelEditor editor;
    public UIPanelSolution solution;
    public UIPanelImageSelect imageSelect;

    protected UI() throws SQLException {

        // Set up JFrame
        new JFrame("CAB302 Maze App");
        setPreferredSize(new Dimension(1400,900));
        setLayout(new BorderLayout(50,20));
        setBackground(Color.lightGray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Add control panel with menu, imageSelect, editor, and solutionChecker
        JPanel controlPanel = new JPanel(); controlPanel.setLayout(new BorderLayout(10,0));
        controlPanel.setPreferredSize(new Dimension(1200,200));
        editor = new UIPanelEditor(); controlPanel.add(editor,BorderLayout.CENTER);
        imageSelect = new UIPanelImageSelect(); controlPanel.add(imageSelect,BorderLayout.WEST);
        solution = new UIPanelSolution(); controlPanel.add(solution,BorderLayout.EAST);
        controlPanel.add(new UIPanelMenu(),BorderLayout.NORTH);
        add(controlPanel,BorderLayout.NORTH);

        // Add the display
        display = new UIPanelDisplay();
        JPanel displayHolder = new JPanel(); displayHolder.setLayout(new BorderLayout()); displayHolder.add(display,BorderLayout.CENTER);
        JPanel padding1 = new JPanel(); padding1.setPreferredSize(new Dimension(100,100));
        JPanel padding2 = new JPanel(); padding2.setPreferredSize(new Dimension(100,100));
        JPanel padding3 = new JPanel(); padding3.setPreferredSize(new Dimension(100,100));
        JPanel padding4 = new JPanel(); padding4.setPreferredSize(new Dimension(100,100));
        displayHolder.add(padding1,BorderLayout.EAST); displayHolder.add(padding3,BorderLayout.WEST);
        displayHolder.add(padding2, BorderLayout.NORTH); displayHolder.add(padding4, BorderLayout.SOUTH);
        JScrollPane scrollPane = new JScrollPane(displayHolder);
        scrollPane.setAutoscrolls(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        add(scrollPane,BorderLayout.CENTER);
        pack();
    }

    private static class UIHolder {
        private static UI INSTANCE;

        static {
            try {
                INSTANCE = new UI();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static UI getInstance(){
        return UIHolder.INSTANCE;
    }

    public int GetDisplayDimension(int dimension){




        int cellDimension = 64;
        for (int i = 10; i < dimension; i++){
            if (i%2 == 0){
                // every 2 we move from 10 to the dimension, - 1 from the cellDimension
                cellDimension = cellDimension - 1;
            }
        }
        return cellDimension;
    }

}