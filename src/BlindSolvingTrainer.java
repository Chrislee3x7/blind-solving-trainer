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
    private JButton loadDefaultMemoSchemeButton;
    private JButton startButton;

    private TrainingPanel trainingPanel;

    public BlindSolvingTrainer() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cubeNetPanel = new CubeNetPanel();
                settingsPanel = new SettingsPanel();
                setUpFrame();
                startMemoSetUpMode();
            }
        });
    }

    public void startMemoSetUpMode() {
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
        setUpSettingsPanelButtons();
    }

    public void setUpSettingsPanelButtons() {
        cubeNetPanel.setBounds(frame.getBounds());
        changeMemoEditModeButton = settingsPanel.setUpSwitchMemoEditButton();
        cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
        changeMemoEditModeButton.addActionListener(e -> {
            changeMemoEditModeButton.cycleMode();
            cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
            cubeNetPanel.repaint();
        });
        loadDefaultMemoSchemeButton = settingsPanel.setUpLoadDefaultMemoSchemeButton();
        loadDefaultMemoSchemeButton.addActionListener(e -> {
            cubeNetPanel.setMemoSchemeToDefault();
            cubeNetPanel.repaint();
        });
        startButton = settingsPanel.setUpStartButton();
        startButton.addActionListener(e -> {
            System.out.println("Starting trainer");
            closeMemoSetUpMode();
            trainingPanel = new TrainingPanel();
            frame.add(trainingPanel);
            frame.repaint();
        });
    }

    public void closeMemoSetUpMode() {
        frame.remove(cubeNetPanel);
        frame.remove(settingsPanel);
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
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 700));
        frame.setUndecorated(false);
//        frame.getContentPane().add(cubeNetPanel);
        //frame.setMinimumSize(new Dimension(900, 600));
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }
}