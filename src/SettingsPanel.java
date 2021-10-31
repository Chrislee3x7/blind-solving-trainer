import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0, 20,0, 20);
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
        c.gridy = 1;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0, 20,0, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        add(loadDefaultMemosButton, c);
        Font font = new Font("Helvetica", Font.PLAIN, 20);
        loadDefaultMemosButton.setFont(font);
        return loadDefaultMemosButton;
    }

    public JButton setUpStartButton() {
        startButton = new JButton("START");
        startButton.setPreferredSize(new Dimension(100, 120));
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.insets = new Insets(0, 20,0, 20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        add(startButton, c);
        Font font = new Font("Helvetica", Font.PLAIN, 30);
        startButton.setFont(font);
        return startButton;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(236, 239, 222));
    }
}
