import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class UIFormLoader extends JFrame {

    private Maze maze;
    private JComboBox<String> sortByBox;
    private JComboBox<String> mazeNameBox;

    public UIFormLoader() throws SQLException, IOException {

        // Set up the main JFrame
        new JFrame("Maze Importer");
        setPreferredSize(new Dimension(800, 300));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        //
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Maze Loader"), SwingConstants.CENTER);
        add(titlePanel, BorderLayout.NORTH);

        // use the GetStringColumn and then sort them all


        // Sort maze by
        JPanel sortOptionsPanel = new JPanel(new GridLayout(4, 1));
        sortOptionsPanel.add(new JLabel("Sort Mazes By: "));
        String[] sortOptions = new String[]{"Maze Name", "Author Name", "Creation Time", "Last Edit Time"};
        this.sortByBox = new JComboBox<>(sortOptions);
        sortOptionsPanel.add(this.sortByBox);

        // The mazes selection
        sortOptionsPanel.add(new JLabel("Select a Maze to load: "));

        // Get the mazes from the database
        String[] mazeNames = MazeDatabase_new.getInstance().GetTableNames();
        ArrayList<Maze> mazeList = new ArrayList<>();
        for (int i = 0; i < mazeNames.length; i++) {
            mazeList.add(MazeDatabase_new.getInstance().LoadTable(mazeNames[i]));
        }

        // Sort by name, author, creation date, last edit date
        if (mazeList.size() > 0) {
            Collections.sort(mazeList, new Comparator<Maze>() {
                @Override
                public int compare(Maze o1, Maze o2) {
                    // Do the if statement for teh selection here
                    if (sortByBox.getSelectedIndex() == 0){
                        return o1.GetName().compareTo(o2.GetName());
                    } else if (sortByBox.getSelectedIndex() == 1){
                        return o1.GetAuthor().compareTo(o2.GetAuthor());
                    } else if (sortByBox.getSelectedIndex() == 2){
                        return o1.GetCreationTime().compareTo(o2.GetCreationTime());
                    } else if (sortByBox.getSelectedIndex() == 3){
                        return o1.GetLastEditTime().compareTo(o2.GetLastEditTime());
                    }
                    return 0;
                };
            });
        }

        String[] mazeDetails = new String[mazeNames.length];
        int i = 0;
        for (Maze maze : mazeList){
            mazeDetails[i] = "[Name] - " + maze.GetName() + " [Author] - " + maze.GetAuthor() + " [CreationDate] - " +
                    maze.GetLastEditTime() + " [LastEditDate] - " + maze.GetLastEditTime();
            i++;
        }

        mazeNameBox = new JComboBox<>(mazeDetails);

        sortOptionsPanel.add(mazeNameBox);

        add(sortOptionsPanel);
        //
        JButton button = new JButton("Select");
        add(button,BorderLayout.SOUTH);

        sortByBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mazeNameBox.removeAllItems();
                if (mazeList.size() > 0) {
                    Collections.sort(mazeList, new Comparator<Maze>() {
                        @Override
                        public int compare(Maze o1, Maze o2) {
                            // Do the if statement for teh selection here
                            if (sortByBox.getSelectedIndex() == 0){
                                return o1.GetName().compareTo(o2.GetName());
                            } else if (sortByBox.getSelectedIndex() == 1){
                                return o1.GetAuthor().compareTo(o2.GetAuthor());
                            } else if (sortByBox.getSelectedIndex() == 2){
                                return o1.GetCreationTime().compareTo(o2.GetCreationTime());
                            } else if (sortByBox.getSelectedIndex() == 3){
                                return o1.GetLastEditTime().compareTo(o2.GetLastEditTime());
                            }
                            return 0;
                        };
                    });
                }
                String[] mazeDetails = new String[mazeNames.length];
                int i = 0;
                for (Maze maze : mazeList){
                    System.out.println("Added a maze " + maze.GetName());
                    mazeDetails[i] = "[Name] - " + maze.GetName() + " [Author] - " + maze.GetAuthor() + " [CreationDate] - " +
                            maze.GetLastEditTime() + " [LastEditDate] - " + maze.GetLastEditTime();
                    System.out.println(mazeDetails[i]);
                    i++;
                }
                for (int j = 0; j < mazeDetails.length;j++){
                    mazeNameBox.addItem(mazeDetails[j]);
                }

            }});

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
                                        dispose();
                                     }
                                 });


        pack();








        /**
        // Add a scrollbar for display of maze options
        String[] mazeNames = MazeDatabase_new.getInstance().GetTableNames();
        JPanel mazeOptions = new JPanel();
        mazeOptions.setPreferredSize(new Dimension(500, 1000));
        mazeOptions.setLayout(new GridLayout(mazeNames.length + 2, 3));
        mazeOptions.add(new JLabel("MAZE NAME")); mazeOptions.add(new JLabel("AUTHOR NAME"));
        mazeOptions.add(new JLabel("CREATION TIME"));
        for (int i = 0; i < mazeNames.length; i++) {
            mazeOptions.add(new JLabel(mazeNames[i]));
            mazeOptions.add(new JLabel(MazeDatabase_new.getInstance().GetString(mazeNames[i],"mazeAuthor")));
            mazeOptions.add(new JLabel(MazeDatabase_new.getInstance().GetString(mazeNames[i],"lastEditTime")));
        }

        JScrollPane scrollPane = new JScrollPane(mazeOptions);
        scrollPane.setAutoscrolls(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.setPreferredSize(new Dimension(500,500));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        add(scrollPane, BorderLayout.CENTER);

        // Add a combo box and button to select a maze name
        JPanel settingsPanel = new JPanel(new GridLayout(2, 2, 20, 30));
        settingsPanel.add(new JLabel("Sort mazes by: "));
        settingsPanel.add(new JLabel("Select a maze to load"));
        JComboBox<String> mazeNameBox = new JComboBox<>(MazeDatabase_new.getInstance().GetTableNames());
        settingsPanel.add(mazeNameBox);
        JButton button = new JButton("Select");
        add(button, BorderLayout.SOUTH);
        add(settingsPanel, BorderLayout.NORTH);
        pack();

        // MazeName - AuthorName - Created - Last Edited -

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
        });*/

    }

}