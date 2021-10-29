import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ModeCycleJButton extends JButton {

    private ArrayList<String> modesTexts;
    private int modesIndex;
    private ArrayList<StickerType> modeType;

    public ModeCycleJButton(String... modesTexts) {
        super();
        this.modesTexts = new ArrayList<>();
        this.modesTexts.addAll(Arrays.asList(modesTexts));
        //setUpActionListener();
        setText(modesTexts[0]);
        modeType = new ArrayList<>();
        modeType.add(StickerType.CORNER);
        modeType.add(StickerType.EDGE);
        //setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, Color.GRAY));
        setFocusPainted(false);
        modesIndex = 0;
    }

    private void setUpActionListener() {
        addActionListener(e -> {
            cycleMode();
        });
    }

    public StickerType getModeType() {
        return modeType.get(modesIndex);
    }
//
//    public void setModesType(StickerType modeType) {
//        this.modeType = modeType;
//

//    public String getCurrentModeText() {
//        return modesTexts.get(modesTextsIndex);
//    }

    public void cycleMode() {
        modesIndex++;
        if (modesIndex < modesTexts.size()) {
            setText(modesTexts.get(modesIndex));
        } else {
            setText(modesTexts.get(0));
            modesIndex = 0;
        }

    }


}
