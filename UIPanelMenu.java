import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

public class UIPanelMenu extends JPanel {

    private JButton create;
    private JButton save;
    private JButton load;
    private JButton export;

    public UIPanelMenu(){

        // Create Buttons
        create = new JButton("Create"); add(create);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIFormCreator newWindow = new UIFormCreator();
            }
        });

        save = new JButton("Save"); add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIFormSaver newWindow = new UIFormSaver();
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
                    UIFormLoader newWindow = new UIFormLoader();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        export = new JButton("Publish"); add(export);
        export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UIFormExporter newWindow = new UIFormExporter();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

    }
}