import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Sticker extends Rectangle {

    private Color trueColor;
    private Color displayColor;
    public static final Color BLINK_COLOR = new Color(126, 238, 234, 255);
    // the letter corresponding to the memo for this sticker
    // '-' is representative of a blank/undecided memo letter
    private char memo;

    private StickerType stickerType;

    private boolean editModeOn;

    private boolean conflicted;


    public Sticker(Color trueColor, char memo, StickerType stickerType) {
        super();
        this.trueColor = trueColor;
        this.displayColor = trueColor;
        this.memo = memo;
        this.stickerType = stickerType;
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

    public StickerType getStickerType() {
        return stickerType;
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
}
