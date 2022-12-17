/*
    Representation of a cube
 */

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

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

    private final HashSet<CubePiece> allPieces = new HashSet<>();

    private final HashSet<CubePiece> cornerPieces = new HashSet<>();

    private final HashSet<CubePiece> edgePieces = new HashSet<>();


    public Cube() {
        cubeFaces = new CubeFace[6];
        for (int i = 0; i < 6; i++) {
            cubeFaces[i] = new CubeFace(CubeNetPanel.defaultColorScheme[i]);
        }
        loadMemoScheme();
        setPieces();
    }

    // Hardcoded values from cubeFaces. should not be changed
    private void setPieces() {
        // add cornerPieces
        cornerPieces.add(getCornerPieceHelper(0,0,1,0,4,1));
        cornerPieces.add(getCornerPieceHelper(0,1,4,0,3,1));
        cornerPieces.add(getCornerPieceHelper(0,2,3,0,2,1));
        cornerPieces.add(getCornerPieceHelper(0,3,2,0,1,1));
        cornerPieces.add(getCornerPieceHelper(5,0,1,2,2,3));
        cornerPieces.add(getCornerPieceHelper(5,1,2,2,3,3));
        cornerPieces.add(getCornerPieceHelper(5,2,3,2,4,3));
        cornerPieces.add(getCornerPieceHelper(5,3,4,2,1,3));

        // add edgePieces
        edgePieces.add(getEdgePieceHelper(0,0,4,0));
        edgePieces.add(getEdgePieceHelper(0,1,3,0));
        edgePieces.add(getEdgePieceHelper(0,2,2,0));
        edgePieces.add(getEdgePieceHelper(0,3,1,0));
        edgePieces.add(getEdgePieceHelper(1,1,2,3));
        edgePieces.add(getEdgePieceHelper(2,1,3,3));
        edgePieces.add(getEdgePieceHelper(3,1,4,3));
        edgePieces.add(getEdgePieceHelper(1,3,4,1));
        edgePieces.add(getEdgePieceHelper(5,0,2,2));
        edgePieces.add(getEdgePieceHelper(5,1,3,2));
        edgePieces.add(getEdgePieceHelper(5,2,4,2));
        edgePieces.add(getEdgePieceHelper(5,3,1,2));

        // add corner and edge pieces to all pieces
        allPieces.addAll(cornerPieces);
        allPieces.addAll(edgePieces);
    }

    public void saveMemoScheme() {
        File f = new File("src/Memos");
        PrintWriter pw;
        try {
            pw = new PrintWriter(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        for (CubeFace face : getCubeFaces()) {
            Sticker[] cornerStickers = face.getCornerStickers();
            Sticker[] edgeStickers = face.getEdgeStickers();
            for (int i = 0; i < cornerStickers.length; i++) {
                pw.print(cornerStickers[i].getMemo());
                if (cornerStickers[i].getConflicted()) {
                    pw.print("1");
                } else {
                    pw.print("0");
                }
                pw.print(edgeStickers[i].getMemo());
                if (edgeStickers[i].getConflicted()) {
                    pw.print("1");
                } else {
                    pw.print("0");
                }
            }
            pw.println();
        }
        pw.close();
    }

    public void loadMemoScheme() {
        BufferedReader bfr = null;
        try {
            bfr = new BufferedReader(new FileReader("src/Memos"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        try {
            line = bfr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (line == null) {
            return;
        }
        for (CubeFace face : getCubeFaces()) {
            for (int i = 0; i < 4; i++) {
                face.setCornerMemo(i, line.charAt(i * 4));
                if (line.charAt(i * 4 + 1) == '1') {
                    face.getCornerStickers()[i].setConflicted(true);
                } else {
                    face.getCornerStickers()[i].setConflicted(false);
                }
                face.setEdgeMemo(i, line.charAt(i * 4 + 2));
                if (line.charAt(i * 4 + 3) == '1') {
                    face.getEdgeStickers()[i].setConflicted(true);
                } else {
                    face.getEdgeStickers()[i].setConflicted(false);
                }
            }
            try {
                line = bfr.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private CubePiece getEdgePieceHelper(int cubeFaceIndex1, int stickerIndex1, int cubeFaceIndex2, int stickerIndex2) {
//        System.out.println(cubeFaces[cubeFaceIndex1].getEdgeStickers()[stickerIndex1] + "" +
//                cubeFaces[cubeFaceIndex2].getEdgeStickers()[stickerIndex2]);
        return CubePiece.createEdgePiece(cubeFaces[cubeFaceIndex1].getEdgeStickers()[stickerIndex1],
                cubeFaces[cubeFaceIndex2].getEdgeStickers()[stickerIndex2]);
    }

    private CubePiece getCornerPieceHelper(int cubeFaceIndex1, int stickerIndex1, int cubeFaceIndex2, int stickerIndex2, int cubeFaceIndex3, int stickerIndex3) {
//        System.out.println(cubeFaces[cubeFaceIndex1].getCornerStickers()[stickerIndex1] + "" +
//                cubeFaces[cubeFaceIndex2].getCornerStickers()[stickerIndex2] + "" +
//                cubeFaces[cubeFaceIndex3].getCornerStickers()[stickerIndex3]);
        return CubePiece.createCornerPiece(cubeFaces[cubeFaceIndex1].getCornerStickers()[stickerIndex1],
                cubeFaces[cubeFaceIndex2].getCornerStickers()[stickerIndex2],
                cubeFaces[cubeFaceIndex3].getCornerStickers()[stickerIndex3]);
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

    public CubeFace[] getCubeFaces() {
        return cubeFaces;
    }

}
