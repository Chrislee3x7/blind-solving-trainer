import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {

    private int width;
    private int singleFaceDimension;


    public SettingsPanel() {
        super();
        singleFaceDimension = getSize().width / 5;
    }

    @Override
    public void paintComponent(Graphics g) {
        setBackground(Color.BLACK);
        g.setColor(Color.GREEN);
        g.drawRect(200, 200, 200, 200);
        paintEachCubeFace(g, 0, 0, 1);

        paintEachCubeFace(g, (325 - (4 * faceDimensions))/2 + (faceDimensions) + distanceBetween2CubeFace, faceDimensions + distanceBetweenNetAndWindow - fixDistance, 2);

        paintEachCubeFace(g, (325 - (4 * faceDimensions))/2 + (faceDimensions * 2) + distanceBetween2CubeFace * 2,  faceDimensions + distanceBetweenNetAndWindow - fixDistance, 3);

        paintEachCubeFace(g, (325 - (4 * faceDimensions))/2 + (faceDimensions * 3) + distanceBetween2CubeFace * 3 , faceDimensions + distanceBetweenNetAndWindow - fixDistance, 4);

        paintEachCubeFace(g, (325 - (4 * faceDimensions))/2 + (faceDimensions) + distanceBetween2CubeFace, distanceBetweenNetAndWindow - distanceBetween2CubeFace - fixDistance, 0);

        paintEachCubeFace(g, (325 - (4 * faceDimensions))/2 + (faceDimensions) + distanceBetween2CubeFace, 200 - faceDimensions - distanceBetweenNetAndWindow + distanceBetween2CubeFace - fixDistance, 5);
    }

    private void paintEachCubeFace(Graphics g, int topLeftCornerX, int topLeftCornerY, int faceIndex) {
        int stickerPaintedCount = 0;
        for(int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                paintSticker(g, topLeftCornerX + (x * faceDimensions/3), topLeftCornerY + (y * faceDimensions/3), cubeFaceArray[faceIndex].getSticker(stickerPaintedCount));
                stickerPaintedCount++;
            }
        }


    }

    private void paintSticker (Graphics g, int topLeftCornerX, int topLeftCornerY, Sticker sticker) {
        Color displayColor = sticker.getDisplayColor();
        g.setColor(Color.black);
        g.drawRect(topLeftCornerX, topLeftCornerY, faceDimensions/3, faceDimensions/3);
        g.setColor(displayColor);
        g.fillRect(topLeftCornerX, topLeftCornerY, faceDimensions/3, faceDimensions/3);
    }


}
