import javax.swing.*;
import java.awt.*;

public class TrainingPanel extends JPanel {

    public TrainingPanel() {
        super();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setOpaque(true);
                setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        System.out.println("tried to paint training panel");
        g.fillRect(0,0, 40, 40);
//        g.drawString("Hello",0,0);
    }
}
