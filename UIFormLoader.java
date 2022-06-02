import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class UIFormLoader extends JFrame {

    public UIFormLoader() throws SQLException, IOException {

        // Set up the main JFrame
        new JFrame("Maze Importer");
        setPreferredSize(new Dimension(700, 700));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Add a scrollbar for display of maze options
        Maze[] mazes = MazeDatabase_new.getInstance().GetMazes();
        JPanel mazeOptions = new JPanel();
        mazeOptions.setPreferredSize(new Dimension(100, 100));
        mazeOptions.setLayout(new GridLayout(mazes.length + 1, 3));
        for (int i = 0; i < mazes.length; i++) {
            mazeOptions.add(new JLabel(mazes[i].GetName()));
            mazeOptions.add(new JLabel(mazes[i].GetAuthor()));
            mazeOptions.add(new JLabel(mazes[i].GetLastEditTime()));
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
                    String mazeType = MazeDatabase_new.getInstance().GetString(selectedMaze,"mazeType");

                    if (Objects.equals(mazeType, "Adult")){
                        UI_new.getInstance().display.currentMaze = new MazeAdult();
                        UI_new.getInstance().display.currentMaze.GenerateSavedMaze(selectedMaze);
                        UI_new.getInstance().display.currentMaze.GenerateDisplayData();
                        UI_new.getInstance().display.UpdateDisplay();
                    } else if (Objects.equals(mazeType, "Child")){
                        UI_new.getInstance().display.currentMaze = new MazeChild();
                        UI_new.getInstance().display.currentMaze.GenerateSavedMaze(selectedMaze);
                        UI_new.getInstance().display.currentMaze.GenerateDisplayData();
                        UI_new.getInstance().display.UpdateDisplay();
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

            }
        });

    }}