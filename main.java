import javax.swing.*;
import java.awt.*;

public class main {

    public static void main(String args[]){

        // Main window
        JFrame mainFrame = new JFrame("Maze App");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(800,800));
        mainFrame.pack();
        mainFrame.setVisible(true);

        // Menu bar
        JMenuBar mainMenuBar = new JMenuBar();
        JMenu newMenu = new JMenu("New"); mainMenuBar.add(newMenu); newMenu.add("Standard Maze"); newMenu.add("Children's Maze");
        JMenu saveMenu = new JMenu("Save"); mainMenuBar.add(saveMenu);
        JMenu importMenu = new JMenu("Import"); mainMenuBar.add(importMenu); importMenu.add("From file");
        JMenu exportMenu = new JMenu("Export"); mainMenuBar.add(exportMenu); exportMenu.add("To file");
        mainFrame.setJMenuBar(mainMenuBar);
        mainFrame.pack();

        // Main options panel
        JPanel mainOptionsPanel = new JPanel();
        mainFrame.getContentPane().add(mainOptionsPanel);

    }

}
