import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class UIPanelSolution extends JPanel{

    private String solutionState;
    private int cellsReached;
    private int deadEnds;

    public UIPanelSolution(){

        new JPanel();
        setBorder(BorderFactory.createTitledBorder("Solution Checker"));
        setPreferredSize(new Dimension(450,300));
        setLayout(new BorderLayout());
        this.solutionState = "Solvable/unsolvable";

        // Button and label for solution
        JPanel currentSelection = new JPanel(new GridLayout(2,1));JButton changeButton = new JButton("Check Solution");
        add(changeButton,BorderLayout.NORTH); JPanel d = new JPanel();
        JLabel c = new JLabel(this.solutionState,SwingConstants.CENTER); d.add(c); currentSelection.add(d);
        currentSelection.add(changeButton); add(currentSelection,BorderLayout.NORTH);

        // Dimension options
        JPanel solutionMetricsPanel = new JPanel(); solutionMetricsPanel.setLayout(new GridLayout(1,2));
        JLabel reached = new JLabel("Reached: " + cellsReached + "%"); JLabel deadEnds = new JLabel("Dead ends: " +
                this.deadEnds + "%"); solutionMetricsPanel.add(reached); solutionMetricsPanel.add(deadEnds);
        add(solutionMetricsPanel,BorderLayout.CENTER);

        // Paint button
        JButton paintButton = new JButton("Show Optimal Solution");
        add(paintButton,BorderLayout.SOUTH);

        // Add padding
        JPanel paddingW = new JPanel(); paddingW.setPreferredSize(new Dimension(100,50)); add(paddingW,BorderLayout.WEST);
        JPanel paddingE = new JPanel(); paddingE.setPreferredSize(new Dimension(100,50)); add(paddingE,BorderLayout.EAST);

        // Action event for solution generator
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UI.getInstance().display.GetDisplayedMaze().Solve();

                if (UI.getInstance().display.GetDisplayedMaze().Solve()){

                    c.setText("SOLUTION AVAILABLE");
                    d.setBackground(Color.GREEN);
                    cellsReached = UI.getInstance().display.GetDisplayedMaze().cellsReached;
                    reached.setText("Reached: " + cellsReached + "%");
                    deadEnds.setText("Dead: " + UI.getInstance().display.GetDisplayedMaze().deadEnds + "%");

                } else {

                    c.setText("NO SOLUTION FOUND");
                    d.setBackground(Color.RED);

                }

                UI.getInstance().pack();

            }
        });

        paintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (UI.getInstance().display.GetDisplayedMaze().Solve()){

                    if (UI.getInstance().display.GetDisplayedMaze().paintSolution){
                        UI.getInstance().display.GetDisplayedMaze().paintSolution = false;
                    } else {
                        UI.getInstance().display.GetDisplayedMaze().paintSolution = true;
                    }

                    try {
                        UI.getInstance().display.UpdateDisplay();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }

            }
        });


    }



}
