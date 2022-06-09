import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;

public class UIFormCreator extends JFrame {

    private Maze maze;
    private String startSelection;
    private String endSelection;
    private String logoSelection;

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

        //
        settingsPanel.add(new JLabel("Start image (optional):"));
        JButton startImageButton = new JButton("Select");
        settingsPanel.add(startImageButton);
        startImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPath;
                File workingDirectory = new File(System.getProperty("user.dir"));
                JFileChooser fileChooserStart = new JFileChooser();
                fileChooserStart.setCurrentDirectory(workingDirectory);
                int result = fileChooserStart.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooserStart.getSelectedFile();
                    currentPath = file.getName();
                    startImageButton.setText(currentPath);
                    startSelection = currentPath;
                }
            }
        });

        settingsPanel.add(new JLabel("End image (optional):"));
        JButton endImageButton = new JButton("Select");
        settingsPanel.add(endImageButton);
        endImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPath;
                File workingDirectory = new File(System.getProperty("user.dir"));
                JFileChooser fileChooserStart = new JFileChooser();
                fileChooserStart.setCurrentDirectory(workingDirectory);
                int result = fileChooserStart.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooserStart.getSelectedFile();
                    currentPath = file.getName();
                    endImageButton.setText(currentPath);
                    endSelection = currentPath;
                }
            }
        });

        settingsPanel.add(new JLabel("Logo image (optional):"));
        JButton logoImageButton = new JButton("Select");
        settingsPanel.add(logoImageButton);
        logoImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentPath;
                File workingDirectory = new File(System.getProperty("user.dir"));
                JFileChooser fileChooserStart = new JFileChooser();
                fileChooserStart.setCurrentDirectory(workingDirectory);
                int result = fileChooserStart.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooserStart.getSelectedFile();
                    currentPath = file.getName();
                    logoImageButton.setText(currentPath);
                    logoSelection = currentPath;
                }
            }
        });




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

        // Pack the display
        pack();

        // Action event for create button
        createMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String mazeName = nameField.getText();
                String authorName = authorField.getText();
                if (Objects.equals(authorName, "")){
                    authorName = "Anonymous";
                }
                int xDimension = (Integer)xDimensionSelection.getValue();
                int yDimension = (Integer)yDimensionSelection.getValue();

                if (mazeTypeSelection.getSelectedIndex() == 0){
                    try {

                        maze = new MazeAdult(mazeName,authorName,xDimension,yDimension);

                        if (mazeGenerationSelection.getSelectedIndex() == 0){

                            maze.GenerateAutoMaze();
                            UI.getInstance().display.SetDisplayedMaze(maze);

                        } else if (mazeGenerationSelection.getSelectedIndex() == 1){

                            maze.GenerateBlankMaze();
                            UI.getInstance().display.SetDisplayedMaze(maze);

                        }
                        if (startSelection != null){
                            System.out.println(startSelection);
                            maze.imageList.add(new MazeImage(startSelection,1,1,0));
                        }
                        if (endSelection != null){
                            maze.imageList.add(new MazeImage(endSelection,1,1,(maze.xDimension*maze.yDimension)-1));
                        }
                        if (logoSelection != null){
                            int height = (Integer)widthSelection.getValue();
                            int width = (Integer)heightSelection.getValue();
                            maze.imageList.add(new MazeImage(logoSelection,width,height,(xDimension*yDimension)/3));
                        }
                        UI.getInstance().display.UpdateDisplay();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                } else if (mazeTypeSelection.getSelectedIndex() == 1){
                    try {

                        maze = new MazeChild(mazeName,authorName,xDimension,yDimension);

                        if (mazeGenerationSelection.getSelectedIndex() == 0){

                            maze.GenerateAutoMaze();
                            UI.getInstance().display.SetDisplayedMaze(maze);

                        } else if (mazeGenerationSelection.getSelectedIndex() == 1){

                            maze.GenerateBlankMaze();
                            UI.getInstance().display.SetDisplayedMaze(maze);

                        }
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    if (startSelection != null){
                        System.out.println(startSelection);
                        maze.imageList.add(new MazeImage(startSelection,2,2,0));
                    }
                    if (endSelection != null){
                        maze.imageList.add(new MazeImage(endSelection,2,2,(maze.xDimension*maze.yDimension)-2-(xDimension)));
                    }
                    if (logoSelection != null){
                        int height = (Integer)widthSelection.getValue();
                        int width = (Integer)heightSelection.getValue();
                        // place it in the top right always

                        maze.imageList.add(new MazeImage(logoSelection,width,height,(xDimension*yDimension)/3));
                    }
                    try {
                        UI.getInstance().display.UpdateDisplay();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

                dispose();
            }
        });

    }
}
