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
    //private SettingsPanel settingsPanel;
    private CubeNetPanel cubeNetPanel;
    private ModeCycleJButton changeMemoEditModeButton;

    private TrainingPanel trainingPanel;

    public BlindSolvingTrainer() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                cubeNetPanel = new CubeNetPanel();
                //settingsPanel = new SettingsPanel();
                trainingPanel = new TrainingPanel();
                setUpFrame();
                startMemoSetUpMode();
            }
        });
    }

    public void startMemoSetUpMode() {
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        GridBagConstraints c = new GridBagConstraints();
        JPanel setMemosPanel = new JPanel();

        frame.setBackground(Color.black);
        System.out.println(frame.getBounds());
        cubeNetPanel.setPreferredSize(frame.getSize());
        frame.add(cubeNetPanel);
        System.out.println(cubeNetPanel.getBounds());

//        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 5;
//        c.weighty = 1;
//        setMemosPanel.add(cubeNetPanel, c);
//
//        c.gridx = 1;
//        c.gridy = 0;
//        c.weightx = 1;
//        c.weighty = 1;
//        //setMemosPanel.add(settingsPanel, c);
//
//        //setUpSettingsPanelButtons();
//
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 1;
//        c.weighty = 1;
//        mainPanel.add(setMemosPanel);
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 1;
//        c.weighty = 1;
        //mainPanel.add(trainingPanel);
        //frame.add(mainPanel);
        //cardLayout.next(mainPanel);
    }

    public void setUpSettingsPanelButtons() {
//        changeMemoEditModeButton = settingsPanel.setUpSwitchMemoEditButton();
//        cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
//        changeMemoEditModeButton.addActionListener(e -> {
//            changeMemoEditModeButton.cycleMode();
//            cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
//            cubeNetPanel.repaint();
//        });
//        JButton loadDefaultMemoSchemeButton = settingsPanel.setUpLoadDefaultMemoSchemeButton();
//        loadDefaultMemoSchemeButton.addActionListener(e -> {
//            cubeNetPanel.setMemoSchemeToDefault();
//            cubeNetPanel.repaint();
//        });
//        JButton startButton = settingsPanel.setUpStartButton();
//        startButton.addActionListener(e -> {
//            closeMemoSetUpMode();
//            setUpTrainingMode();
//        });
    }

    public void setUpTrainingMode() {
        System.out.println("Starting trainer");
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
//        c.gridx = 0;
//        c.gridy = 0;
//        c.weightx = 1;
//        c.weighty = 1;
//        frame.add(trainingPanel);
//        trainingPanel.setPreferredSize(new Dimension(100,100));
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        frame.add(trainingPanel, c);
        trainingPanel.repaint();
    }

    public void closeMemoSetUpMode() {
        frame.remove(cubeNetPanel);
        //frame.remove(settingsPanel);
        frame.repaint();
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
//        frame.add(settingsPanel, c);
//        settingsPanel.setUpSwitchMemoEditButton();
    }

    public void minimizeSettingsPanel() {
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 1;
        //frame.remove(settingsPanel);
    }

    private void setUpFrame() {
        frame = new JFrame("Blind Solving Trainer");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 700));
        frame.setUndecorated(false);
        frame.setMinimumSize(new Dimension(900, 600));
        frame.setResizable(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}