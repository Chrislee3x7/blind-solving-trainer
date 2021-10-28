import javax.swing.*;
import java.awt.*;

public class Sticker extends Rectangle {

    private Color color;
    // the letter corresponding to the memo for this sticker
    // '-' is representative of a blank/undecided memo letter
    private char memo;

    public Sticker(Color color, char memo, int startX, int startY, int sizeX, int sizeY) {
        super();
//        setBorder(BorderFactory.createEmptyBorder());
//        setContentAreaFilled(false);
//        setBackground(color);
//        setLocation(startX, startY);
//        setSize(sizeX, sizeY);
        this.color = color;
        this.memo = memo;
    }

    public Sticker(Color color, char memo) {
        super();
        this.color = color;
        this.memo = memo;
//        setBorder(BorderFactory.createEmptyBorder());
//        setContentAreaFilled(false);
//        setBackground(color);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public char getMemo() {
        return memo;
    }

    public void setMemo(char memo) {
        this.memo = memo;
    }
}
