import javax.swing.*;
import java.awt.*;

public class UIPanelImageSelect extends JPanel {

    private String currentPath;

    public UIPanelImageSelect(){

        new JPanel();
        setBorder(BorderFactory.createTitledBorder("Image Editor"));
        setPreferredSize(new Dimension(450,300));
        setLayout(new BorderLayout());

        // Button and label for selection
        JPanel currentSelection = new JPanel(new GridLayout(2,1));JButton changeButton = new JButton("Change");
        add(changeButton,BorderLayout.NORTH); currentSelection.add(new JLabel("Selected image: " + this.currentPath,SwingConstants.CENTER));
        currentSelection.add(changeButton); add(currentSelection,BorderLayout.NORTH);

        // Dimension options
        JPanel imageOptionsPanel = new JPanel(); imageOptionsPanel.setLayout(new GridLayout(1,2));
        JPanel heightPanel = new JPanel(); heightPanel.add(new JLabel("Height: "));
        JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(1,1,50,1));
        heightPanel.add(heightSpinner); imageOptionsPanel.add(heightPanel); JPanel widthPanel = new JPanel();
        widthPanel.add(new JLabel("Width: "));
        JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(1,1,50,1));
        widthPanel.add(widthSpinner); imageOptionsPanel.add(widthPanel); add(imageOptionsPanel, BorderLayout.CENTER);

        // Paint button
        JButton paintButton = new JButton("Paint Image");
        add(paintButton,BorderLayout.SOUTH);

        // Add padding
        JPanel paddingW = new JPanel(); paddingW.setPreferredSize(new Dimension(100,50)); add(paddingW,BorderLayout.WEST);
        JPanel paddingE = new JPanel(); paddingE.setPreferredSize(new Dimension(100,50)); add(paddingE,BorderLayout.EAST);

    }


}
