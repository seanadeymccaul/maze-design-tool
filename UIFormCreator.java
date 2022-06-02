import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UIFormCreator extends JFrame {

    public UIFormCreator() {

        // Set up the main JFrame
        new JFrame("Maze Creation");
        setPreferredSize(new Dimension(400, 700));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // Add title
        JPanel titlePanel = new JPanel();
        titlePanel.add(new JLabel("Maze Creator"));
        add(titlePanel,BorderLayout.NORTH);

        // Add an input for the maze name
        JPanel settingsPanel = new JPanel(new GridLayout(11, 2, 20, 30));
        settingsPanel.add(new JLabel("Name of maze"));
        JTextField nameField = new JTextField();
        settingsPanel.add(nameField);

        // Add an input for the author name
        settingsPanel.add(new JLabel("Name of author"));
        JTextField authorField = new JTextField();
        settingsPanel.add(authorField);

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
        JSpinner xDimensionSelection = new JSpinner(new SpinnerNumberModel(10,10,100,2));
        settingsPanel.add(xDimensionSelection);
        JLabel yDimensionLabel = new JLabel("yDimension");
        settingsPanel.add(yDimensionLabel);
        JSpinner yDimensionSelection = new JSpinner(new SpinnerNumberModel(10,10,100,2));
        settingsPanel.add(yDimensionSelection);

        // Start image
        settingsPanel.add(new JLabel("Start image path (optional):"));
        JTextField startImageField = new JTextField();
        settingsPanel.add(startImageField);

        // End image
        settingsPanel.add(new JLabel("End image path (optional):"));
        JTextField endImageField = new JTextField();
        settingsPanel.add(endImageField);

        // Logo image
        settingsPanel.add(new JLabel("Logo image path (optional):"));
        JTextField logoImageField = new JTextField();
        settingsPanel.add(logoImageField);

        // Dimensions for logo image
        JLabel logoWidthLabel = new JLabel("Logo width");
        settingsPanel.add(logoWidthLabel);
        JSpinner widthSelection = new JSpinner(new SpinnerNumberModel(1,1,10,1));
        settingsPanel.add(widthSelection);
        JLabel logoHeightLabel = new JLabel("Logo height");
        settingsPanel.add(logoHeightLabel);
        JSpinner heightSelection = new JSpinner(new SpinnerNumberModel(1,1,10,1));
        settingsPanel.add(heightSelection);

        // Add the settings panel
        add(settingsPanel, BorderLayout.CENTER);
        JButton createMazeButton = new JButton("Create Maze");
        add(createMazeButton, BorderLayout.SOUTH);

        pack();

        createMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mazeName = nameField.getText();
                String authorName = authorField.getText();
                System.out.println(mazeName);
                int xDimension = (Integer)xDimensionSelection.getValue();
                int yDimension = (Integer)yDimensionSelection.getValue();

                if (mazeTypeSelection.getSelectedIndex() == 0){
                    try {
                        UI_new.getInstance().display.currentMaze = new MazeAdult(mazeName,authorName,xDimension,yDimension);
                        // add start image and end image
                        UI_new.getInstance().display.currentMaze.startImagePath = startImageField.getText();
                        UI_new.getInstance().display.currentMaze.endImagePath = endImageField.getText();
                        UI_new.getInstance().display.currentMaze.logoImagePath = logoImageField.getText();
                        UI_new.getInstance().display.currentMaze.logoWidth = (Integer)widthSelection.getValue();
                        UI_new.getInstance().display.currentMaze.logoHeight = (Integer)heightSelection.getValue();
                        if (mazeGenerationSelection.getSelectedIndex() == 0){
                            UI_new.getInstance().display.currentMaze.GenerateAutoMaze();
                            UI_new.getInstance().display.UpdateDisplay();
                        } else if (mazeGenerationSelection.getSelectedIndex() == 1){
                            UI_new.getInstance().display.currentMaze.GenerateBlankMaze();
                            UI_new.getInstance().display.UpdateDisplay();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (mazeTypeSelection.getSelectedIndex() == 1){
                    try {
                        UI_new.getInstance().display.currentMaze = new MazeChild(mazeName,authorName,xDimension,yDimension);
                        UI_new.getInstance().display.currentMaze.startImagePath = startImageField.getText();
                        UI_new.getInstance().display.currentMaze.endImagePath = endImageField.getText();
                        UI_new.getInstance().display.currentMaze.logoImagePath = logoImageField.getText();
                        if (mazeGenerationSelection.getSelectedIndex() == 0){
                            UI_new.getInstance().display.currentMaze.GenerateAutoMaze();
                            UI_new.getInstance().display.UpdateDisplay();
                        } else if (mazeGenerationSelection.getSelectedIndex() == 1){
                            UI_new.getInstance().display.currentMaze.GenerateBlankMaze();
                            UI_new.getInstance().display.UpdateDisplay();
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }


            }
        });

    }
}
