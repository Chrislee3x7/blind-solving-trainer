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
    private static CubeNet cubeNet;

    public static void main(String[] args) {
        setUpFrame();
        settingsPanel = new SettingsPanel();
        cubeNet = new CubeNet();
        frame.getContentPane().add(cubeNet);
        frame.setBackground(new Color(163, 138, 150));
    }

    private static void setUpFrame() {
        frame = new JFrame("Blind Solving Trainer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.pack();
        frame.setVisible(true);

    }
}
