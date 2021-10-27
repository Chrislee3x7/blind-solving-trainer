import java.awt.*;

public class CubeFace {

    // stickers in order from top to bottom, left to right
    // | 0 1 2 |
    // | 3 4 5 |
    // | 6 7 8 |
    private Sticker[] stickers;

    // number of elements in stickers should be exactly 9
    public CubeFace(Sticker[] stickers) {
        this.stickers = stickers;
    }

    // creates a CubeFace of stickers with '-' as the memo
    public CubeFace(Color color) {
        stickers = new Sticker[9];
        for (int i = 0; i < 9; i++) {
            stickers[i] = new Sticker(color, '-', i);
        }
    }

    public void setColor(Color color) {
        for (int i = 0; i < 9; i++) {
            stickers[i].setColor(color);
        }
    }

    public void setAllMemosToDefault() {

    }

    public void setEdgeMemosToDefault() {

    }

    public void setCornerMemosToDefault() {

    }

    /**
     * Sets the memo for a specific edge sticker
     *
     * @param edgeIndex the index of the edge sticker (0 - 4)
     * @param memo the single char memo
     */
    public void setEdgeMemo(int edgeIndex, char memo) {
        int stickersIndex = edgeIndex < 2 ? edgeIndex * 2 + 1 : edgeIndex * 2;
        stickers[stickersIndex].setMemo(memo);
    }

    /**
     * Sets the memo for a specific corner sticker
     *
     * @param cornerIndex the index of the corner sticker (0 - 4)
     * @param memo the single char memo
     */
    public void setCornerMemo(int cornerIndex, char memo) {
        int stickersIndex = cornerIndex < 2 ? cornerIndex * 2 : cornerIndex * 2 + 1;
        stickers[stickersIndex].setMemo(memo);
    }

    public void setAllEdgeMemos(char memo0, char memo1, char memo2, char memo3) {
        
    }

    public void setAllCornerMemos(char memo0, char memo1, char memo2, char memo3) {

    }


    public Sticker getSticker(int stickerIndex) {
        return stickers[stickerIndex];
    }

}
