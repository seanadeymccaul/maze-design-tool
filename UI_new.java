import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UI_new extends JFrame{

    public static UIMenuBar menuBar;
    public JScrollPane scrollPane;
    public UIMazeDisplay mazeDisplay;
    public MazeDatabase dbAccess = new MazeDatabase();

    protected UI_new() throws SQLException {
        // Set up JFrame
        new JFrame("CAB302 Maze App");
        setPreferredSize(new Dimension(1400,900));
        setLayout(new BorderLayout(50,20));
        setBackground(Color.lightGray);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        // Add components
        menuBar = new UIMenuBar();
        add(menuBar, BorderLayout.NORTH);
        mazeDisplay = new UIMazeDisplay();
        scrollPane = new JScrollPane(mazeDisplay);
        scrollPane.setPreferredSize(new Dimension(2000,2000));
        scrollPane.setAutoscrolls(true);
        add(scrollPane, BorderLayout.CENTER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(25);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(25);
        add(new JPanel(), BorderLayout.SOUTH);
        add(new JPanel(), BorderLayout.EAST);
        add(new JPanel(), BorderLayout.WEST);
    }

    private static class UIHolder {
        private static UI_new INSTANCE;

        static {
            try {
                INSTANCE = new UI_new();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static UI_new getInstance(){
        return UIHolder.INSTANCE;
    }
}