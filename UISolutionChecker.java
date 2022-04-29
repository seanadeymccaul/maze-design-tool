import javax.swing.*;
import java.awt.*;

public class UISolutionChecker extends JPanel {
    public UISolutionChecker(){

        //
        setLayout(new BorderLayout(50,30));
        JButton addLogo = new JButton("Add Logo"); addLogo.setPreferredSize(new Dimension(50,50));
        JButton getSolution = new JButton("Check Solution"); getSolution.setPreferredSize(new Dimension(50,50));
        add(addLogo, BorderLayout.NORTH); add(getSolution, BorderLayout.CENTER);
    }
}
