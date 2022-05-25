import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIMazeCreator extends JFrame {

    public UIMazeCreator() {

        // Set up the main JFrame
        new JFrame("Maze Creation");
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Add an input for the maze name
        JPanel settingsPanel = new JPanel(new GridLayout(5, 2, 20, 30));
        settingsPanel.add(new JLabel("Name of maze"));
        JTextField nameField = new JTextField();
        settingsPanel.add(nameField);

        // Add a comboBox for the generation method
        JLabel mazeGenerationLabel = new JLabel("Generation method: ");
        settingsPanel.add(mazeGenerationLabel);
        JComboBox<String> mazeGenerationSelection = new JComboBox<>(new String[]{"Automatic", "Blank"});
        settingsPanel.add(mazeGenerationSelection);

        // Add a comboBox for the maze type
        JLabel mazeTypeLabel = new JLabel("Maze Type: ");
        settingsPanel.add(mazeTypeLabel);
        JComboBox<String> mazeTypeSelection = new JComboBox<>(new String[]{"Adult", "Child"});
        settingsPanel.add(mazeTypeSelection);

        // Add spinners for the dimensions
        JLabel xDimensionLabel = new JLabel("xDimension");
        settingsPanel.add(xDimensionLabel);
        JSpinner xDimensionSelection = new JSpinner(new SpinnerNumberModel(10,10,50,2));
        settingsPanel.add(xDimensionSelection);
        JLabel yDimensionLabel = new JLabel("yDimension");
        settingsPanel.add(yDimensionLabel);
        JSpinner yDimensionSelection = new JSpinner(new SpinnerNumberModel(10,10,50,2));
        settingsPanel.add(yDimensionSelection);

        // Add the settings panel
        add(settingsPanel, BorderLayout.CENTER);
        JButton createMazeButton = new JButton("Create Maze");
        add(createMazeButton, BorderLayout.SOUTH);

        pack();

        createMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mazeName = nameField.getText();
                System.out.println(mazeName);
                int xDimension = (Integer)xDimensionSelection.getValue();
                int yDimension = (Integer)yDimensionSelection.getValue();

                if (mazeTypeSelection.getSelectedIndex() == 0){
                    try {
                        UI_new.getInstance().mazeDisplay.currentMaze = new MazeAdult(mazeName,0,xDimension,yDimension);
                        if (mazeGenerationSelection.getSelectedIndex() == 0){
                            UI_new.getInstance().mazeDisplay.currentMaze.GenerateAuto();
                            UI_new.getInstance().mazeDisplay.UpdateDisplay();
                        } else if (mazeGenerationSelection.getSelectedIndex() == 1){
                            UI_new.getInstance().mazeDisplay.currentMaze.GenerateBlank();
                            UI_new.getInstance().mazeDisplay.UpdateDisplay();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                } else if (mazeTypeSelection.getSelectedIndex() == 1){
                    try {
                        UI_new.getInstance().mazeDisplay.currentMaze = new MazeChild(mazeName,1,xDimension,yDimension);
                        if (mazeGenerationSelection.getSelectedIndex() == 0){
                            UI_new.getInstance().mazeDisplay.currentMaze.GenerateAuto();
                            UI_new.getInstance().mazeDisplay.UpdateDisplay();
                        } else if (mazeGenerationSelection.getSelectedIndex() == 1){
                            UI_new.getInstance().mazeDisplay.currentMaze.GenerateBlank();
                            UI_new.getInstance().mazeDisplay.UpdateDisplay();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });

    }
}
