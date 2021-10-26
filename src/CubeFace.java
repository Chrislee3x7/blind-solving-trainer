import java.awt.*;

public class CubeFace {

    // stickers in order from top to bottom left to right
    // | 0 1 2 |
    // | 3 4 5 |
    // | 6 7 8 |
    private Sticker[] stickers;
    private Color color;

    // number of elements in stickers should be exactly 9
    public CubeFace(Sticker[] stickers) {
        this.stickers = stickers;
//        // get the color of the center sticker index: 4
//        color = stickers[5].getColor();
    }

    public CubeFace(Color color) {
        stickers = new Sticker[9];
//        this.color = color;
        for (int i = 0; i < 9; i++) {
            stickers[i] = new Sticker(color);
        }
    }

}
