import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main {

    public static void main(String args[]){

        UI session = new UI();
        Maze example = new MazeAdult(10,10);
        example.GenerateAuto();
    }

}
