import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIFormSaver extends JFrame {

    private Maze maze;

    public UIFormSaver() throws SQLException {

        new JFrame("Maze Loader");
        setPreferredSize(new Dimension(400,200));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        maze = UI.getInstance().display.GetDisplayedMaze();
        MazeDatabase.getInstance().SaveTable(maze);

        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Maze Saver",SwingConstants.CENTER));
        add(titlePanel,BorderLayout.NORTH);

        JPanel middlePanel = new JPanel();
        middlePanel.add(new JLabel("Maze " + UI.getInstance().display.GetDisplayedMaze().GetName() + " saved successfully!"));
        add(middlePanel,BorderLayout.CENTER);

        JButton button = new JButton("Ok");
        add(button,BorderLayout.SOUTH);
        pack();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }
}
