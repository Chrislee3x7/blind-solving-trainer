import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ModeCycleJButton extends JButton {

    private ArrayList<String> modesTexts;
    private int modesTextsIndex;

    public ModeCycleJButton(String... modesTexts) {
        super();
        this.modesTexts = new ArrayList<>();
        this.modesTexts.addAll(Arrays.asList(modesTexts));
        setUpActionListener();
        setText(modesTexts[0]);
        modesTextsIndex = 0;
    }

    private void setUpActionListener() {
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(getActionCommand())) {
                    cycleMode();
                }
            }
        });
    }

    private void cycleMode() {
        modesTextsIndex++;
        if (modesTextsIndex < modesTexts.size()) {
            setText(modesTexts.get(modesTextsIndex));
        } else {
            setText(modesTexts.get(0));
            modesTextsIndex = 0;
        }
    }


}
