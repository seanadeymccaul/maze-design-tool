import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {

    public static void main(String args[]){

        UI session = new UI();

        /**
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


    **/
    }

}
