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

    private static JFrame frame;
    private static SettingsPanel settingsPanel;
    private static CubeNetPanel cubeNetPanel;
    private static JLayeredPane layeredPane;

    private static JPanel buttonPanel;

    public static void main(String[] args) {
        cubeNetPanel = new CubeNetPanel();
        setUpFrame();
        cubeNetPanel.setBounds(frame.getBounds());
        frame.add(cubeNetPanel);
//        layeredPane = frame.getLayeredPane();
//        layeredPane.add(cubeNetPanel);
//        layeredPane.setLayer(cubeNetPanel, JLayeredPane.DEFAULT_LAYER);
        //setUpSwitchMemoEditButton();
    }

//    private static void setUpSwitchMemoEditButton() {
//        buttonPanel = new JPanel(new BorderLayout());
//        ModeCycleJButton button = new ModeCycleJButton("Corners", "Edges");
//        //button.setBackground(Color.RED);
//        //button.setOpaque(true);
//        //button.setBounds(1000, 20, 60, 60);
//        buttonPanel.setBounds(0, 0, 1000, 700);
//        buttonPanel.setOpaque(false);
//        buttonPanel.add(button, BorderLayout.EAST);
//        button.setActionCommand("test button");
//        button.addActionListener(e -> {
//            String actionCommand = e.getActionCommand();
//            if (actionCommand.equals("test button")) {
//                System.out.println("Button pressed");
//            }
//        });
//        layeredPane.add(buttonPanel, JLayeredPane.MODAL_LAYER);
//    }

    private static void setUpFrame() {
        frame = new JFrame("Blind Solving Trainer");
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