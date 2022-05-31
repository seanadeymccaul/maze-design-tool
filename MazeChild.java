import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MazeChild extends Maze{

    private byte[] startImage;
    private byte[] endImage;


    public MazeChild() throws SQLException {
        super();
    }

    public MazeChild(String name, int xDimension, int yDimension) throws SQLException {
        super(name, xDimension, yDimension);
    }

    @Override
    public UIPanelDisplayCell[] GetDisplayData() throws IOException {
        super.GetDisplayData();
        return displayData;
    }


    /**
    private ArrayList<Integer> GetChildIndex(int index){
        // if x%2 == 1 then add index - 1 (5 is the same as 6). When y%2 == 0 then we add the one above it (- xDimension)
        ArrayList<Integer> childIndex = new ArrayList<>(); childIndex.add(index);
        int x = 0, y = 0;
        for (int i = 0; i <= index; i++){
            x = i%xDimension;
            if (x == 0){
                y++;
            }
        }
        // say we have x and y
        if (y%2 == 0){
            childIndex.add(index - xDimension);
            if (x%2 == 0) {
                childIndex.add(index + 1);
                childIndex.add(index - xDimension + 1);
            } else {
                childIndex.add(index - 1);
                childIndex.add(index - xDimension - 1);
            }
        } else {
            childIndex.add(index + xDimension);
            if (x%2 == 0) {
                childIndex.add(index + 1);
                childIndex.add(index + xDimension + 1);
            } else {
                childIndex.add(index - 1);
                childIndex.add(index + xDimension - 1);
            }
        }
        Collections.sort(childIndex);
        return childIndex;
    }
     int[] startEndCells = new int[]{0,1,xDimension,xDimension+1,cellCount-1,cellCount-2,cellCount-xDimension-1,
     cellCount-xDimension-2};
     for (int i = 0; i < startEndCells.length; i++){
     this.ReplaceCell(new MazeCell(3,1,1,1,1),i);
     mazeData[startEndCells[i]].setValue(3);
     }
     */
    
}
