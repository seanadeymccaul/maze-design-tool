import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIMazeImport extends JFrame {

    public UIMazeImport() throws SQLException {

        new JFrame("Maze Importer");
        setPreferredSize(new Dimension(400,400));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        JPanel settingsPanel = new JPanel(new GridLayout(3, 1, 20, 30));

            settingsPanel.add(new JLabel("Select a maze to load"));

            MazeDatabase example = new MazeDatabase();
            String[] tableNames = example.RetrieveTableNames();

            JComboBox<String> comboBox = new JComboBox<>(tableNames);
            settingsPanel.add(comboBox);

            JButton button = new JButton("Select");
            settingsPanel.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    example.tableName = tableNames[comboBox.getSelectedIndex()];
                    System.out.println(example.tableName);
                }
            });

        add(settingsPanel);
        pack();
    }
}
