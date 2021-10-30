import javax.swing.*;
import java.awt.*;

/**
 * BlindSolvingTrainer
 *
 * A program that allows user to train the memorization of corresponding letters
 * of the pieces on the 3x3 Rubik's Cube
 *
 * @author Chrislee3x7
 * @version 10/24/21
 */
public class BlindSolvingTrainer {

    private static BlindSolvingTrainer blindSolvingTrainer;
    private JFrame frame;
    private SettingsPanel settingsPanel;
    private CubeNetPanel cubeNetPanel;
    private ModeCycleJButton changeMemoEditModeButton;

    public BlindSolvingTrainer() {
        cubeNetPanel = new CubeNetPanel();
        settingsPanel = new SettingsPanel();
        setUpFrame();
        cubeNetPanel.setBounds(frame.getBounds());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 5;
        c.weighty = 1;
        frame.add(cubeNetPanel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        frame.add(settingsPanel, c);
        changeMemoEditModeButton = settingsPanel.setUpSwitchMemoEditButton();
        cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
        changeMemoEditModeButton.addActionListener(e -> {
            changeMemoEditModeButton.cycleMode();
            cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
            cubeNetPanel.repaint();
        });
    }

    public static void main(String[] args) {
        blindSolvingTrainer = new BlindSolvingTrainer();
    }

    public void expandSettingsPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        frame.add(settingsPanel, c);
        settingsPanel.setUpSwitchMemoEditButton();
    }

    public void minimizeSettingsPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 1;
        frame.remove(settingsPanel);
    }

    private void setUpFrame() {
        frame = new JFrame("Blind Solving Trainer");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setPreferredSize(new Dimension(1000, 700));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
//        frame.getContentPane().add(cubeNetPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setVisible(true);
        frame.setResizable(true);
    }
}