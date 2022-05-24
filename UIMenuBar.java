import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class UIMenuBar extends JPanel {

    private JButton create;
    private JButton save;
    private JButton load;
    private JButton export;

    public UIMenuBar(){
        // Create Buttons
        create = new JButton("Create"); add(create);
        save = new JButton("Save"); add(save);
        load = new JButton("Import"); add(load);
        export = new JButton("Export"); add(export);

        // Event listeners
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIMazeCreator newWindow = new UIMazeCreator();
            }
        });

        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIMazeImport newWindow = new UIMazeImport();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}