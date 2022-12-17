/*
    Helper class to group sticker indices into pieces.
 */

public class CubePiece {

    private StickerType pieceType;
    // For corner stickers, the stickers are in clockwise order
    private Sticker[] pieceStickers; // has 2 or 3 elements depending on the type of sticker

    private CubePiece() {

    }

    // Should use this instead of constructor
    public static CubePiece createEdgePiece(Sticker sticker1, Sticker sticker2) {
        CubePiece cp = new CubePiece();
        cp.pieceType = StickerType.EDGE;
        cp.pieceStickers = new Sticker[2];
        cp.pieceStickers[0] = sticker1;
        cp.pieceStickers[1] = sticker2;
        return cp;
    }

    // Should use this instead of constructor
    public static CubePiece createCornerPiece(Sticker sticker1, Sticker sticker2, Sticker sticker3) {
        CubePiece cp = new CubePiece();
        cp.pieceType = StickerType.CORNER;
        cp.pieceStickers = new Sticker[3];
        cp.pieceStickers[0] = sticker1;
        cp.pieceStickers[1] = sticker2;
        cp.pieceStickers[2] = sticker3;
        return cp;
    }

    public StickerType getPieceType() {
        return pieceType;
    }

    public Sticker[] getPieceStickers() {
        return pieceStickers;
    }
}
