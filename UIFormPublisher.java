import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UIFormPublisher extends JFrame {

    JCheckBox[] mazeItems;

    public UIFormPublisher() throws SQLException {

        new JFrame("Maze Publisher");
        setPreferredSize(new Dimension(700, 500));
        setLayout(new BorderLayout(10, 30));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        // add title
        JPanel title = new JPanel();
        title.add(new JLabel("Maze Publisher", SwingConstants.CENTER));
        add(title,BorderLayout.NORTH);

        // get all the current mazes
        String[] mazeNames = MazeDatabase_new.getInstance().GetTableNames();
        Maze[] mazes = new Maze[mazeNames.length];
        // then get the info for string array
        String[] mazeInfo = new String[mazeNames.length];
        for (int i = 0; i < mazeNames.length; i++){
            mazes[i] = MazeDatabase_new.getInstance().LoadTable(mazeNames[i]);
            mazeInfo[i] = "[Name] - " + mazes[i].GetName() + " [Author] - " + mazes[i].GetAuthor() + " [CreationDate] - " +
                    mazes[i].GetLastEditTime() + " [LastEditDate] - " + mazes[i].GetLastEditTime();
        }

        // put it in a checkbox list
        JPanel selectionPanel = new JPanel(new GridLayout(mazeNames.length, 1));
        setPreferredSize(new Dimension(700,500));
        mazeItems = new JCheckBox[mazeNames.length];
        for (int j = 0; j < mazeNames.length; j++){
            JPanel mazeItem = new JPanel();
            mazeItems[j] = new JCheckBox(mazeInfo[j]);
            mazeItem.add(mazeItems[j]);
            selectionPanel.add(mazeItem);
        }

        // put a scroll pane
        JScrollPane scrollPane = new JScrollPane(selectionPanel);
        scrollPane.setAutoscrolls(true);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25); scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        scrollPane.setPreferredSize(new Dimension(300,300));
        add(scrollPane,BorderLayout.CENTER);

        // create
        JButton select = new JButton("Publish mazes");
        add(select,BorderLayout.SOUTH);
        pack();

        select.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // get the selected mazes
                ArrayList<String> selected = new ArrayList<>();
                for (int i = 0; i < mazeItems.length; i++){
                    if (mazeItems[i].isSelected()){
                        selected.add(mazeNames[i]);
                    }
                }

                // publish them all
                for (String s : selected){
                    try {
                        MazeDatabase_new.getInstance().LoadTable(s).PublishMaze();
                    } catch (SQLException | IOException ex) {
                        ex.printStackTrace();
                    }
                    dispose();

                }

            }
        });


    }
}
