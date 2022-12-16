import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TrainingPanel extends JPanel implements KeyListener {

    private BlindSolvingTrainer bst;

    public TrainingPanel(BlindSolvingTrainer bst) {
        super();
        this.bst = bst;
        addKeyListener(this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setFocusable(true);
                setOpaque(true);
                setBorder(BorderFactory.createLineBorder(Color.GREEN));
            }
        });
    }




    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0, 40, 40);
        g.drawString("Hello",0,0);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            bst.changePanel(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
