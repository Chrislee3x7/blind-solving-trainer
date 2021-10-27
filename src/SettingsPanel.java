import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JComponent {

    private int width;
    private CubeNet cubeNet;


    public SettingsPanel() {
        super();
        cubeNet = new CubeNet();
        setBackground(Color.YELLOW);
    }

    public void paintComponent(Graphics g) {
        //g.drawRect(9,9,9,9);
    }
}
