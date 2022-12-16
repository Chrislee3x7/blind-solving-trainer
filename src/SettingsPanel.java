import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;

public class SettingsPanel extends JPanel {

    private ModeCycleJButton toggleMemoEditModeButton;

    private JButton loadDefaultMemosButton;

    private JButton startButton;

    public SettingsPanel() {
        super();
        setOpaque(true);
        setLayout(new GridBagLayout());
        setFocusable(true);
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                //requestFocus();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    public ModeCycleJButton setUpSwitchMemoEditButton() {
        toggleMemoEditModeButton = new ModeCycleJButton("Mode: Corners", "Mode: Edges");
        toggleMemoEditModeButton.setPreferredSize(new Dimension(100, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 20,10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        add(toggleMemoEditModeButton, c);
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        toggleMemoEditModeButton.setFont(font);
        return toggleMemoEditModeButton;
    }

    public JButton setUpLoadDefaultMemoSchemeButton() {
        loadDefaultMemosButton = new JButton("Load Default Memo Scheme");
        loadDefaultMemosButton.setPreferredSize(new Dimension(100, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 20,10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        add(loadDefaultMemosButton, c);
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        loadDefaultMemosButton.setFont(font);
        return loadDefaultMemosButton;
    }

    public JButton setUpStartButton() {
        startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(100, 80));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(10, 20,10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        add(startButton, c);
        Font font = new Font("Helvetica", Font.PLAIN, 30);
        startButton.setFont(font);
        return startButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(0,0,0,0));
        //setBackground(new Color(236, 239, 222));
        setBorder(new RoundedBorder(20, new Color(239, 222, 226)));
    }

    private static class RoundedBorder extends AbstractBorder {

        private int radius;
        private Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            super.paintBorder(c, g, x, y, width, height);
            Graphics2D g2d = (Graphics2D) g;
            Shape clip = g2d.getClip();
            Area rect = new Area(clip);
            rect.subtract(new Area(new RoundRectangle2D.Double(x, y, width, height, radius, radius)));
            g2d.setClip(rect);
            g2d.setColor(c.getParent().getParent().getParent().getBackground());
            g2d.fillRect(0, 0, width, height);

            g2d.setClip(clip);
            g2d.setColor(color);
            g2d.fillRoundRect(x, y, width, height, radius, radius);
        }
    }
}
