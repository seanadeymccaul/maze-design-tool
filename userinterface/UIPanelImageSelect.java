package userinterface;
import maze.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Image select contained by main UI frame
 */
public class UIPanelImageSelect extends JPanel {

    public boolean current;
    private String currentPath;
    private MazeImage selectedImage;
    public MazeImage GetSelectedImage(){return selectedImage;}
    public void SetSelectedImage(MazeImage m){selectedImage = m;}

    public UIPanelImageSelect(){

        new JPanel();
        setBorder(BorderFactory.createTitledBorder("Image Editor"));
        setPreferredSize(new Dimension(450,300));
        setLayout(new BorderLayout());

        // Button and label for selection
        JPanel currentSelection = new JPanel(new GridLayout(2,1));JButton changeButton = new JButton("Change");
        add(changeButton,BorderLayout.NORTH); JLabel text = new JLabel("Selected image: " + this.currentPath,SwingConstants.CENTER);
        currentSelection.add(text);
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

        // Action events
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                File workingDirectory = new File(System.getProperty("user.dir"));
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(workingDirectory);
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION){
                    File file = fileChooser.getSelectedFile();
                    currentPath = file.getName();
                    text.setText("Selected image: " + currentPath);
                }
            }
        });

        paintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Paint button pressed");
                current = true;
                UI.getInstance().editor.current = false;
                int width = (Integer)widthSpinner.getValue();
                int height = (Integer)heightSpinner.getValue();
                UI.getInstance().imageSelect.SetSelectedImage(new MazeImage(currentPath,width,height));
                System.out.println("Image loaded in selection");
            }
        });


    }


}
