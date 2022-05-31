import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UIFormLoader extends JFrame {

    public UIFormLoader() throws SQLException {

        // Set up the main JFrame
        new JFrame("Maze Importer");
        setPreferredSize(new Dimension(400,400));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Add components to select a maze
        JPanel settingsPanel = new JPanel(new GridLayout(1, 2, 20, 30));
        settingsPanel.add(new JLabel("Select a maze to load: "));
        JComboBox<String> mazeNameBox = new JComboBox<>(UI_new.getInstance().dbAccess.GetTableNames());
        settingsPanel.add(mazeNameBox);
        JButton button = new JButton("Select");
        add(button, BorderLayout.SOUTH);
        add(settingsPanel, BorderLayout.CENTER);
        pack();

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if adult or child maze selected
                String[] mazeNames = new String[0];
                try {
                    mazeNames = UI_new.getInstance().dbAccess.GetTableNames();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                try {
                    if (UI_new.getInstance().dbAccess.GetMazeType(mazeNames[mazeNameBox.getSelectedIndex()]) == 0){
                        UI_new.getInstance().display.currentMaze = new MazeAdult();
                    } else {
                        UI_new.getInstance().display.currentMaze = new MazeChild();
                    }
                    UI_new.getInstance().display.currentMaze.GenerateSavedMaze(mazeNames[mazeNameBox.getSelectedIndex()]);
                    UI_new.getInstance().display.UpdateDisplay();

                } catch (SQLException exception) {
                    exception.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
