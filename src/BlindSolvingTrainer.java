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
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel setMemosPanel;
    private CubeNetPanel cubeNetPanel;
    private ModeCycleJButton changeMemoEditModeButton;

    private TrainingPanel trainingPanel;

    public BlindSolvingTrainer() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setUpPanels();
                setUpFrame();
                startMemoSetUpMode();
            }
        });
    }

    public void setUpPanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        cubeNetPanel = new CubeNetPanel();
        settingsPanel = new SettingsPanel();
        trainingPanel = new TrainingPanel(this);
        trainingPanel.setName("TrainingPanel");
        setMemosPanel = new JPanel(new GridBagLayout());
        setMemosPanel.setName("SetMemosPanel");
    }

    public void changePanel(int i) {
        switch (i) {
            case 0:
                setPanel(setMemosPanel);
                break;
            case 1:
                setPanel(trainingPanel);
                break;
            default:

        }
    }

    public void setPanel(JPanel panel) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("trying to show a panel of name: " + panel.getName());
                cardLayout.show(mainPanel, panel.getName());
//                cardLayout.next(mainPanel);
//                cardLayout.first(mainPanel);
                panel.requestFocus();
            }
        });
    }

    public void startMemoSetUpMode() {

        GridBagConstraints c = new GridBagConstraints();

        //cubeNetPanel.setPreferredSize(frame.getSize());

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 10;
        c.weighty = 10;
        setMemosPanel.add(cubeNetPanel, c);

        setUpSettingsPanelButtons();
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0;
        c.weighty = 1;
        setMemosPanel.add(settingsPanel, c);
        setMemosPanel.setPreferredSize(frame.getMaximumSize());
        setMemosPanel.setMinimumSize(frame.getMinimumSize());

        mainPanel.setPreferredSize(frame.getMaximumSize());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.red));

        mainPanel.add(setMemosPanel, setMemosPanel.getName());
        mainPanel.add(trainingPanel, trainingPanel.getName());

        frame.add(mainPanel);
    }

    public void setUpSettingsPanelButtons() {
        changeMemoEditModeButton = settingsPanel.setUpSwitchMemoEditButton();
        cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
        changeMemoEditModeButton.addActionListener(e -> {
            changeMemoEditModeButton.cycleMode();
            cubeNetPanel.setMemoEditMode(changeMemoEditModeButton.getModeType());
            cubeNetPanel.repaint();
        });
        JButton loadDefaultMemoSchemeButton = settingsPanel.setUpLoadDefaultMemoSchemeButton();
        loadDefaultMemoSchemeButton.addActionListener(e -> {
            cubeNetPanel.setMemoSchemeToDefault();
            cubeNetPanel.repaint();
        });
        JButton startButton = settingsPanel.setUpStartButton();
        startButton.addActionListener(e -> {
            changePanel(1);
        });
    }

    public static void main(String[] args) {
        blindSolvingTrainer = new BlindSolvingTrainer();
    }

    private void setUpFrame() {
        frame = new JFrame("Blind Solving Trainer");
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 700));
        frame.setUndecorated(false);
        frame.setMinimumSize(new Dimension(1000, 700));
        frame.setResizable(true);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}