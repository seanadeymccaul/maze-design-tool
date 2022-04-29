import javax.swing.*;
import java.awt.*;

public class UI extends JFrame{

    // Instances
    // private JFrame mainFrame;
    private UIMenuBar menuPanel;
    private UIController controlPanel;
    private UIMazeEditor editorPanel;
    private UISolutionChecker solutionPanel;
    private UIMazeDisplay mazePanel;

    // Constructor
    public UI(){
        new JFrame("CAB302 Maze App");

        setPreferredSize(new Dimension(700,700));
        setLayout(new BorderLayout(50,20));
        setBackground(Color.lightGray);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        // Add menu bar
        add(new UIMenuBar(), BorderLayout.NORTH);

        // Add control panel
        JPanel controlsPanel = new JPanel(new GridLayout(1,3,50,0));
        controlsPanel.setPreferredSize(new Dimension(200,100)); add(controlsPanel, BorderLayout.CENTER);
        controlsPanel.add(new UIController()); controlsPanel.add(new UIMazeEditor()); controlsPanel.add(new UISolutionChecker());

        // Add maze display
        add(new UIMazeDisplay(), BorderLayout.SOUTH);

        pack();

    }

}
