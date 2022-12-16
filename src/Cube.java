/*
    Representation of a cube
 */

import java.awt.*;
import java.util.ArrayList;

public class Cube {

    public static final char[][] DEFAULT_MEMOS = new char[][]{
            {'A', 'B', 'C', 'D'},
            {'E', 'F', 'G', 'H'},
            {'I', 'J', 'K', 'L'},
            {'M', 'N', 'O', 'P'},
            {'Q', 'R', 'S', 'T'},
            {'U', 'V', 'W', 'X'}};

    // array of cube faces that correspond to the six face of the cube
    // indexed 0 - 5: [0: U, 1: L, 2: F, 3: R, 4: B, 5: D]
    private CubeFace[] cubeFaces;

    public CubeFace[] getCubeFaces() {
        return cubeFaces;
    }

    public Cube() {
        cubeFaces = new CubeFace[6];
        for (int i = 0; i < 6; i++) {
            cubeFaces[i] = new CubeFace(CubeNetPanel.defaultColorScheme[i]);
        }
    }

    public void resetEdgeMemos() {
        for (int i = 0; i < 6; i++) {
            cubeFaces[i].setEdgeMemosToDefault(i);
        }
    }

    public void resetCornerMemos() {
        for (int i = 0; i < 6; i++) {
            cubeFaces[i].setCornerMemosToDefault(i);
        }
    }

    public void setFaceColor(int faceIndex, Color faceColor) {
        cubeFaces[faceIndex].setColor(faceColor);
    }

    public ArrayList<Sticker> getStickerConflicts(Sticker sourceSticker) {
        ArrayList<Sticker> conflicts = new ArrayList<>();
        StickerType st = sourceSticker.getStickerType();
        char conflictMemo = sourceSticker.getMemo();
        if (st == StickerType.CORNER) {
            for (CubeFace face : cubeFaces) {
                for (Sticker s : face.getCornerStickers()) {
                    if (s.getMemo() == conflictMemo) {
                        conflicts.add(s);
                    }
                }
            }
        } else if (st == StickerType.EDGE) {
            for (CubeFace face : cubeFaces) {
                for (Sticker s : face.getEdgeStickers()) {
                    if (s.getMemo() == conflictMemo) {
                        conflicts.add(s);
                    }
                }
            }
        }
        return conflicts;
    }

}
