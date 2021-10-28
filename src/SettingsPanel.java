import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private int width;
    private CubeNetPanel cubeNetPanel;

    public SettingsPanel() {
        super();
        cubeNetPanel = new CubeNetPanel();
        setBackground(Color.YELLOW);
    }

    public void paintComponent(Graphics g) {
        //g.drawRect(9,9,9,9);
    }
}
