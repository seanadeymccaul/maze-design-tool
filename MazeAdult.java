import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class MazeAdult extends Maze{

    // Constructor
    public MazeAdult() throws SQLException {
        super();
    }

    public MazeAdult(String name, int xDimension, int yDimension) throws SQLException {
        super(name, xDimension, yDimension);
    }

    @Override
    public UIPanelDisplayCell[] GetDisplayData() throws IOException {
        super.GetDisplayData();
        return this.displayData;
    }


}
