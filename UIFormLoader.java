import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UIFormLoader extends JFrame {

    private Maze maze;

    public UIFormLoader() throws SQLException, IOException {

        // Set up the main JFrame
        new JFrame("Maze Importer");
        setPreferredSize(new Dimension(700, 700));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Add a scrollbar for display of maze options
        String[] mazeNames = MazeDatabase_new.getInstance().GetTableNames();
        JPanel mazeOptions = new JPanel();
        mazeOptions.setPreferredSize(new Dimension(100, 100));
        mazeOptions.setLayout(new GridLayout(mazeNames.length + 1, 3));
        for (int i = 0; i < mazeNames.length; i++) {
            mazeOptions.add(new JLabel(mazeNames[i]));
            mazeOptions.add(new JLabel(MazeDatabase_new.getInstance().GetString(mazeNames[i],"mazeAuthor")));
            mazeOptions.add(new JLabel(MazeDatabase_new.getInstance().GetString(mazeNames[i],"lastEditTime")));
        }
        JScrollPane scrollPane = new JScrollPane(mazeOptions);
        scrollPane.setAutoscrolls(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.setPreferredSize(new Dimension(300,300));
        add(scrollPane, BorderLayout.NORTH);

        // Add a combo box and button to select a maze name
        JPanel settingsPanel = new JPanel(new GridLayout(2, 1, 20, 30));
        settingsPanel.add(new JLabel("Select a maze to load: "));
        JComboBox<String> mazeNameBox = new JComboBox<>(MazeDatabase_new.getInstance().GetTableNames());
        settingsPanel.add(mazeNameBox);
        JButton button = new JButton("Select");
        add(button, BorderLayout.SOUTH);
        add(settingsPanel, BorderLayout.CENTER);
        pack();

        // Add button listener event for actions
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Check if the selected maze is adult or child and load it respectively
                try {

                    String[] mazeNames = MazeDatabase_new.getInstance().GetTableNames();
                    String selectedMaze = mazeNames[mazeNameBox.getSelectedIndex()];

                    maze = MazeDatabase_new.getInstance().LoadTable(selectedMaze);

                    UI.getInstance().display.SetDisplayedMaze(maze);

                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }}