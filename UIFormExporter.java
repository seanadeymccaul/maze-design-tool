import javax.swing.*;
import java.sql.SQLException;

public class UIFormExporter extends JFrame {

    public UIFormExporter() throws SQLException {
        /**
        new JFrame("Maze Exporter");
        setPreferredSize(new Dimension(400,400));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        JPanel settingsPanel = new JPanel(new GridLayout(3, 1, 20, 30));

        settingsPanel.add(new JLabel("Save maze as name:"));

        JTextField textField = new JTextField();


        settingsPanel.add(textField);

        JButton button = new JButton("Confirm");
        settingsPanel.add(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                try {
                    MazeDatabase db = new MazeDatabase();
                    //db.CreateDataColumn(text);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        });

        add(settingsPanel);
        pack();
    }
         */
    }
}