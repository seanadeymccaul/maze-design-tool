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
        JPanel controlPanel = new JPanel(); mainFrame.add(controlPanel, BorderLayout.CENTER); controlPanel.setBackground(Color.GREEN);
        controlPanel.setPreferredSize(new Dimension(200,100));
        JLabel control = new JLabel("Controls go here"); controlPanel.add(control);

        // Add maze display
        JPanel mazePanel = new JPanel(); mainFrame.add(mazePanel, BorderLayout.SOUTH); mazePanel.setBackground(Color.BLUE);
        mazePanel.setPreferredSize(new Dimension(100,400));
        JLabel maze = new JLabel("Maze go here"); mazePanel.add(maze);

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
