import javax.swing.*;
import java.awt.*;

public class TrainingPanel extends JPanel {

    public TrainingPanel() {
        super();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawString("Hello",0,0);

    }
}
