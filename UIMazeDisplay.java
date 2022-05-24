import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class UIMazeDisplay extends JPanel {

    public UIMazeDisplay() throws SQLException {

        setPreferredSize(new Dimension(400,400));

        Icon horizontalIcon = new ImageIcon("horizontal_maze.png");
        Icon verticalIcon = new ImageIcon("vertical_maze.png");

        MazeAdult example = new MazeAdult();
        example.LoadMaze("names");

        /**
        MazeDatabase db = new MazeDatabase();
        int[] mazeData = db.RetrieveTable("maze_example",5,7);
        System.out.println(mazeData.length);
        Maze currentMaze = new MazeAdult(mazeData,5,7);
        setLayout(new GridLayout(7,5));
        System.out.println("Hello");
        for (int i = 0; i < currentMaze.cells; i++){
            if (currentMaze.mazeData[i] == 0){
                JPanel black = new JPanel();
                black.setBackground(Color.BLACK);
                add(black);
            }
            else{
                JPanel white = new JPanel();
                white.setBackground(Color.WHITE);
                add(white);
            }

        }**/

    }

    public static void UpdateDisplay(){

    }

}
