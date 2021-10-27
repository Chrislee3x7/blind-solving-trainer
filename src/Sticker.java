import java.awt.*;

public class Sticker {

    private Color color;
    // the letter corresponding to the memo for this sticker
    // '-' is representative of a blank/undecided memo letter
    private char letter;
    // the index on the 3x3 array of a face (0 - 9)
    private int index;

    public Sticker(Color color, char letter, int index) {
        this.color = color;
        this.letter = letter;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public char getLetter() {
        return letter;
    }

    public void setMemo(char letter) {
        this.letter = letter;
    }
}
