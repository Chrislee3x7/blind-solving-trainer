import java.awt.*;

public class Sticker extends Rectangle {

    private Color trueColor;
    private Color displayColor;
    public static final Color BLINK_COLOR = new Color(126, 238, 234, 255);
    // the letter corresponding to the memo for this sticker
    // '-' is representative of a blank/undecided memo letter
    private char memo;

    private PieceType pieceType;

    private boolean editModeOn;

    private boolean conflicted;


    public Sticker(Color trueColor, char memo, PieceType pieceType) {
        super();
        this.trueColor = trueColor;
        this.displayColor = trueColor;
        this.memo = memo;
        this.pieceType = pieceType;
        this.editModeOn = false;
    }

    public void setConflicted(boolean newConflicted) {
        conflicted = newConflicted;
    }

    public boolean getConflicted() {
        return conflicted;
    }

    public void setEditModeOn() {
        editModeOn = true;
    }

    public void setEditModeOff() {
        editModeOn = false;
    }

    public boolean getEditModeOn() {
        return editModeOn;
    }

    public PieceType getStickerType() {
        return pieceType;
    }

    public void setDisplayColor(Color newDisplayColor) {
        displayColor = newDisplayColor;
    }

    public Color getDisplayColor() {
        return displayColor;
    }

    public Color getTrueColor() {
        return trueColor;
    }

    public void setTrueColor(Color trueColor) {
        this.trueColor = trueColor;
    }

    public char getMemo() {
        return memo;
    }

    public void setMemo(char memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return memo + " " + displayColor;
    }
}
