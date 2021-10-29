import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    ModeCycleJButton toggleMemoEditModeButton;

    public SettingsPanel() {
        super();
        setOpaque(true);
        setLayout(new GridBagLayout());
    }

    public void setUpSwitchMemoEditButton() {
        toggleMemoEditModeButton = new ModeCycleJButton("Corners", "Edges");
        toggleMemoEditModeButton.setPreferredSize(new Dimension(100, 80));
        toggleMemoEditModeButton.setActionCommand("test button");
        toggleMemoEditModeButton.addActionListener(e -> {
            String actionCommand = e.getActionCommand();
            if (actionCommand.equals("test button")) {
                System.out.println("Button pressed");
            }
        });
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(toggleMemoEditModeButton, c);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(236, 239, 222));
    }
}
