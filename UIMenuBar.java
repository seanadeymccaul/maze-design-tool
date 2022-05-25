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
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIMazeCreator newWindow = new UIMazeCreator();
            }
        });

        save = new JButton("Save"); add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIMazeSave newWindow = new UIMazeSave();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        load = new JButton("Load"); add(load);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIMazeLoad newWindow = new UIMazeLoad();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        export = new JButton("Export"); add(export);
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIMazeExport newWindow = new UIMazeExport();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}