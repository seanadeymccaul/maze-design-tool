import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIFormSaver extends JFrame {

    private Maze maze;

    public UIFormSaver() throws SQLException {

        new JFrame("Maze Loader");
        setPreferredSize(new Dimension(400,400));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        JPanel settingsPanel = new JPanel(new GridLayout(3, 1, 20, 30));

        settingsPanel.add(new JLabel("Save maze as name:"));

        JTextField textField = new JTextField();


        settingsPanel.add(textField);

        JButton button = new JButton("Confirm");
        settingsPanel.add(button);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                try {

                    maze = UI.getInstance().display.GetDisplayedMaze();
                    MazeDatabase_new.getInstance().SaveTable(maze);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(settingsPanel);
        pack();
    }
}
