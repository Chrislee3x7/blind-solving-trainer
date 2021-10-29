import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private ModeCycleJButton toggleMemoEditModeButton;
    private BlindSolvingTrainer blindSolvingTrainer;

    public SettingsPanel(BlindSolvingTrainer blindSolvingTrainer) {
        super();
        setOpaque(true);
        setLayout(new GridBagLayout());
        this.blindSolvingTrainer = blindSolvingTrainer;
    }

    public ModeCycleJButton setUpSwitchMemoEditButton() {
        toggleMemoEditModeButton = new ModeCycleJButton("Corners", "Edges");
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
