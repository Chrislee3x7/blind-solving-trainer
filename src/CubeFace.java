import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CubeFace extends Rectangle {

    // corner stickers
    // | 0 - 1 |
    // | - - - |
    // | 3 - 2 |
    private Sticker[] cornerStickers;

    // edge stickers
    // | - 0 - |
    // | 3 - 1 |
    // | - 2 - |
    private Sticker[] edgeStickers;

    // | 0 1 2 |
    // | 3 4 5 |
    // | 6 7 8 |
    private Sticker[] allStickers;

    private StickerType memoEditMode;

    private Color color;

    public static final char[][] DEFAULT_MEMOS = new char[][]{
            {'A', 'B', 'C', 'D'},
            {'E', 'F', 'G', 'H'},
            {'I', 'J', 'K', 'L'},
            {'M', 'N', 'O', 'P'},
            {'Q', 'R', 'S', 'T'},
            {'U', 'V', 'W', 'X'}};

//    // number of elements in stickers should be exactly 9
//    public CubeFace(Sticker[] stickers) {
//        this.stickers = stickers;
//    }

    // creates a CubeFace of stickers with '-' as the memo
    public CubeFace(Color color) {
        super();
        this.color = color;
        //this.memoEditMode = memoEditMode;
        cornerStickers = new Sticker[4];
        edgeStickers = new Sticker[4];
        for (int i = 0; i < 4; i++) {
            cornerStickers[i] = new Sticker(color, '-', StickerType.CORNER);
            edgeStickers[i] = new Sticker(color, '-', StickerType.EDGE);
        }
        updateAllStickersArray();
    }

    public Sticker[] getCornerStickers() {
        return cornerStickers;
    }

    public Sticker[] getEdgeStickers() {
        return edgeStickers;
    }

    public void updateAllStickersArray() {
        allStickers = new Sticker[]{cornerStickers[0], edgeStickers[0], cornerStickers[1], edgeStickers[3],
                new Sticker(color, '-', StickerType.CENTER), edgeStickers[1], cornerStickers[3], edgeStickers[2], cornerStickers[2]};
    }

    public void setColor(Color color) {
        for (int i = 0; i < 4; i++) {
            cornerStickers[i].setTrueColor(color);
            edgeStickers[i].setTrueColor(color);
        }
        updateAllStickersArray();
    }

    public void setAllMemosToDefault(int faceIndex) {
        setCornerMemosToDefault(faceIndex);
        setEdgeMemosToDefault(faceIndex);
    }

    public void setEdgeMemosToDefault(int faceIndex) {
        for (int i = 0; i < 4; i++) {
            edgeStickers[i].setMemo(DEFAULT_MEMOS[faceIndex][i]);
        }
        updateAllStickersArray();
    }

    public void setCornerMemosToDefault(int faceIndex) {
        for (int i = 0; i < 4; i++) {
            cornerStickers[i].setMemo(DEFAULT_MEMOS[faceIndex][i]);
        }
        updateAllStickersArray();
    }

    /**
     * Sets the memo for a specific edge sticker
     *
     * @param edgeIndex the index of the edge sticker (0 - 4)
     * @param memo the single char memo
     */
    public void setEdgeMemo(int edgeIndex, char memo) {
        edgeStickers[edgeIndex].setMemo(memo);
        updateAllStickersArray();
    }

    /**
     * Sets the memo for a specific corner sticker
     *
     * @param cornerIndex the index of the corner sticker (0 - 4)
     * @param memo the single char memo
     */
    public void setCornerMemo(int cornerIndex, char memo) {
        cornerStickers[cornerIndex].setMemo(memo);
    }

    public void setAllEdgeMemos(char memo0, char memo1, char memo2, char memo3) {
        char[] memos = new char[]{memo0, memo1, memo2, memo3};
        for (int i = 0; i < 4; i++) {
            setEdgeMemo(i, memos[i]);
        }
    }

    public void setAllCornerMemos(char memo0, char memo1, char memo2, char memo3) {
        char[] memos = new char[]{memo0, memo1, memo2, memo3};
        for (int i = 0; i < 4; i++) {
            setCornerMemo(i, memos[i]);
        }
    }

    public Sticker getSticker(int index) {
        updateAllStickersArray();
        return allStickers[index];
    }

    public void setMemoEditMode(StickerType memoEditMode) {
        this.memoEditMode = memoEditMode;
    }

    public Sticker findClickedSticker(Point clickLoc) {
        if (memoEditMode == StickerType.CORNER) {
            for (Sticker sticker : cornerStickers) {
                if (sticker.contains(clickLoc)) {
                    return sticker;
                }
            }
        } else if (memoEditMode == StickerType.EDGE) {
            for (Sticker sticker: edgeStickers) {
                if (sticker.contains(clickLoc)) {
                    return sticker;
                }
            }
        }
        // search through all stickers
//        for (Sticker sticker : allStickers) {
//            if (sticker.contains(clickLoc)) {
//                return sticker;
//            }
//        }
        return null;
    }

}
