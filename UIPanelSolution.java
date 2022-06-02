import javax.swing.*;
import java.awt.*;
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
        add(changeButton,BorderLayout.NORTH); currentSelection.add(new JLabel(this.solutionState,SwingConstants.CENTER));
        currentSelection.add(changeButton); add(currentSelection,BorderLayout.NORTH);

        // Dimension options
        JPanel solutionMetricsPanel = new JPanel(); solutionMetricsPanel.setLayout(new GridLayout(1,2));
        JLabel reached = new JLabel("Cells reached: " + cellsReached + "%"); JLabel deadEnds = new JLabel("Dead ends: " +
                this.deadEnds + "%"); solutionMetricsPanel.add(reached); solutionMetricsPanel.add(deadEnds);
        add(solutionMetricsPanel,BorderLayout.CENTER);

        // Paint button
        JButton paintButton = new JButton("Show Optimal Solution");
        add(paintButton,BorderLayout.SOUTH);

        // Add padding
        JPanel paddingW = new JPanel(); paddingW.setPreferredSize(new Dimension(100,50)); add(paddingW,BorderLayout.WEST);
        JPanel paddingE = new JPanel(); paddingE.setPreferredSize(new Dimension(100,50)); add(paddingE,BorderLayout.EAST);

    }



}
