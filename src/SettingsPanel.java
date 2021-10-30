import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SettingsPanel extends JPanel {

    private ModeCycleJButton toggleMemoEditModeButton;

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
                requestFocus();
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
        toggleMemoEditModeButton.addActionListener(e -> {

        });
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(toggleMemoEditModeButton, c);
        return toggleMemoEditModeButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(236, 239, 222));
    }
}
