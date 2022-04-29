import javax.swing.*;
import java.awt.*;

public class UIMazeCreator extends JFrame {

    public UIMazeCreator() {

        new JFrame("Maze Creation");
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        JPanel settingsPanel = new JPanel(new GridLayout(3, 2, 20, 30));

        // add difficulty panel
        JPanel difficultyPanel = new JPanel(new GridLayout(2, 1));
        JRadioButton adult = new JRadioButton("Adult");
        JRadioButton child = new JRadioButton("Child");
        ButtonGroup difficultyBg = new ButtonGroup();
        difficultyBg.add(adult);
        difficultyBg.add(child);
        difficultyPanel.add(adult);
        difficultyPanel.add(child);

        // add canvas panel
        JPanel generatePanel = new JPanel(new GridLayout(2, 1));
        JRadioButton auto = new JRadioButton("Generate");
        JRadioButton manual = new JRadioButton("Blank");
        ButtonGroup generateBg = new ButtonGroup();
        generateBg.add(auto);
        generateBg.add(manual);
        generatePanel.add(auto);
        generatePanel.add(manual);

        // add dimensions panel
        JPanel dimensionsPanel = new JPanel(new GridLayout(2, 2));
        dimensionsPanel.add(new JSpinner(new SpinnerNumberModel(10, 10, 50, 2)));
        dimensionsPanel.add(new JLabel("height", SwingConstants.CENTER));
        dimensionsPanel.add(new JSpinner(new SpinnerNumberModel(10, 10, 50, 2)));
        dimensionsPanel.add(new JLabel("width", SwingConstants.CENTER));

        // add to the main settings panel
        settingsPanel.add(new JLabel("Dimensions", SwingConstants.CENTER));
        settingsPanel.add(dimensionsPanel);
        settingsPanel.add(new JLabel("Difficulty", SwingConstants.CENTER));
        settingsPanel.add(difficultyPanel);
        settingsPanel.add(new JLabel("Canvas", SwingConstants.CENTER));
        settingsPanel.add(generatePanel);

        // tie it all together
        add(settingsPanel, BorderLayout.CENTER);
        JButton generateButton = new JButton("Generate Now");
        add(generateButton, BorderLayout.SOUTH);

        pack();

    }
}
