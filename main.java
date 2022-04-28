import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {

    public static void main(String args[]){
        System.out.println("Hello");

        // *************************** DISPLAY ******************************

        // Main window
        JFrame mainFrame = new JFrame("Maze App");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(700,700));
        mainFrame.setLayout(new BorderLayout(50,20));
        mainFrame.setVisible(true);

        // Add menu to top
        JPanel menuPanel = new JPanel(); mainFrame.add(menuPanel, BorderLayout.NORTH); menuPanel.setBackground(Color.BLACK);
        Button create = new Button("Create"); menuPanel.add(create);
        Button save = new Button("Save"); menuPanel.add(save);
        Button load = new Button("Import"); menuPanel.add(load);
        Button export = new Button("Export"); menuPanel.add(export);

        // Add the controls
        JPanel controlPanel = new JPanel(new GridLayout(1,3,50,0)); mainFrame.add(controlPanel, BorderLayout.CENTER); controlPanel.setBackground(Color.lightGray);
        mainFrame.getContentPane().setBackground(Color.lightGray);
        controlPanel.setPreferredSize(new Dimension(200,100));

        // Add maze display
        JPanel mazePanel = new JPanel(); mainFrame.add(mazePanel, BorderLayout.SOUTH); mazePanel.setBackground(Color.BLUE);
        mazePanel.setPreferredSize(new Dimension(100,400));
        JLabel maze = new JLabel("Maze go here"); mazePanel.add(maze);

        // Control panel
        JPanel movementControls = new JPanel(new GridLayout(3,3)); controlPanel.add(movementControls);
        movementControls.add(new JLabel(""));
        Icon upIcon = new ImageIcon("up_arrow.png");
        JButton up = new JButton(upIcon);
        up.setBackground(Color.BLACK);
        up.setForeground(Color.BLUE);
        Icon downIcon = new ImageIcon("down_arrow.png");
        JButton down = new JButton(downIcon);
        Icon leftIcon = new ImageIcon("left_arrow.png");
        JButton left = new JButton(leftIcon);
        Icon rightIcon = new ImageIcon("right_arrow.png");
        JButton right = new JButton(rightIcon);
        movementControls.add(up); movementControls.add(new JLabel("")); movementControls.add(left); movementControls.add(new JLabel(""));
        movementControls.add(right); movementControls.add(new JLabel(""));
        movementControls.add(down); movementControls.add(new JLabel(""));



        JPanel orientationControls = new JPanel(new GridLayout(3,2,50,0)); controlPanel.add(orientationControls);
        //
        orientationControls.add(new JLabel(""));  orientationControls.add(new JLabel(""));
        Icon horizontalIcon = new ImageIcon("horizontal_maze.png");
        Icon verticalIcon = new ImageIcon("vertical_maze.png");
        JButton vertical = new JButton(verticalIcon); orientationControls.add(vertical);
        JButton horizontal = new JButton(horizontalIcon); orientationControls.add(horizontal);
        orientationControls.add(new JLabel("")); orientationControls.add(new JLabel(""));





        JPanel solutionPanel = new JPanel(new BorderLayout(50,30)); controlPanel.add(solutionPanel);
        JButton findImage = new JButton("Add Logo"); findImage.setPreferredSize(new Dimension(50,50));
        solutionPanel.add(findImage, BorderLayout.NORTH);
        JButton checkSolution = new JButton("Check Solution"); checkSolution.setPreferredSize(new Dimension(50,50));
        solutionPanel.add(checkSolution, BorderLayout.CENTER);





        // Pack the display
        mainFrame.pack();

        // ****************************** EVENT HANDLERS ******************************
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create maze panel
                JFrame createMazeFrame = new JFrame("Maze Creator");
                createMazeFrame.setPreferredSize(new Dimension(400,400));

                // Create a panel with border layout
                JPanel createMazePanel = new JPanel(new BorderLayout(10,30)); createMazeFrame.add(createMazePanel);

                // Create text at top
                createMazePanel.add(new JPanel(),BorderLayout.NORTH);

                // Settings in the middle
                JPanel settingsPanel = new JPanel(new GridLayout(3,2,20,30));

                // add radio buttons
                JPanel difficultyPanel = new JPanel(new GridLayout(2,1));
                JRadioButton adult = new JRadioButton("Adult"); JRadioButton child = new JRadioButton("Child");
                ButtonGroup difficultyBg = new ButtonGroup(); difficultyBg.add(adult); difficultyBg.add(child);
                difficultyPanel.add(adult); difficultyPanel.add(child);

                // more radio buttons
                JPanel generatePanel = new JPanel(new GridLayout(2,1));
                JRadioButton auto = new JRadioButton("Generate"); JRadioButton manual = new JRadioButton("Blank");
                ButtonGroup generateBg = new ButtonGroup(); generateBg.add(auto); generateBg.add(manual);
                generatePanel.add(auto); generatePanel.add(manual);

                // add dimensions panel
                JPanel dimensionsPanel = new JPanel(new GridLayout(2,2));
                dimensionsPanel.add(new JSpinner(new SpinnerNumberModel(10,10,50,2)));
                dimensionsPanel.add(new JLabel("height",SwingConstants.CENTER));
                dimensionsPanel.add(new JSpinner(new SpinnerNumberModel(10,10,50,2)));
                dimensionsPanel.add(new JLabel("width",SwingConstants.CENTER));


                settingsPanel.add(new JLabel("Dimensions", SwingConstants.CENTER));
                settingsPanel.add(dimensionsPanel);
                settingsPanel.add(new JLabel("Difficulty", SwingConstants.CENTER));
                settingsPanel.add(difficultyPanel);
                settingsPanel.add(new JLabel("Canvas", SwingConstants.CENTER));
                settingsPanel.add(generatePanel);


                createMazePanel.add(settingsPanel, BorderLayout.CENTER);
                // Button at bottom for generate
                Button generateButton = new Button("Generate Now");
                createMazePanel.add(generateButton, BorderLayout.SOUTH);  //createMazeFrame.setPreferredSize(new Dimension(400,100));
                // Pack the frame
                createMazeFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                createMazeFrame.setVisible(true);
                createMazeFrame.pack();
            }
        });



    }

}
