import javax.swing.*;
import java.awt.*;

public class CubeNet extends JPanel {

    // array of cube faces that correspond to the six face of the cube
    // indexed 0 - 5: [0: U, 1: L, 2: F, 3: R, 4: B, 5: D]
    private CubeFace[] cubeFaces;
    // indexed 0 - 5: [0: White, 1: Red, 2: Green, 3: Orange, 4: Blue, 5: Yellow
    public static final Color[] defaultColorScheme = new Color[]
            {Color.WHITE, // white
                    new Color(235, 41, 2), // red
                    new Color(9, 207, 2), // green
                    new Color(255, 135, 8), // orange
                    new Color(8, 119, 255), // blue
                    Color.YELLOW}; // yellow
    private Dimension panelDimension;
    private int singleFaceDimension;
    private int stickerBorderThickness;

    /**
     * Instantiates a CubeNet object with default CubeFace colors
     *
     */
    public CubeNet() {
        super();
        setOpaque(true);
        cubeFaces = new CubeFace[6];
        singleFaceDimension = getSize().width / 5;
        //setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        for (int i = 0; i < 6; i++) {
            cubeFaces[i] = new CubeFace(defaultColorScheme[i]);
        }
    }

    public void updatePanelDimension() {
        panelDimension = getSize();
        singleFaceDimension = panelDimension.width / 6;
        stickerBorderThickness = singleFaceDimension / 30;
    }

    public void setFaceColor(int faceIndex, Color color) {
        cubeFaces[faceIndex].setColor(color);
    }

    @Override
    public void paintComponent(Graphics g) {
        updatePanelDimension();
        int centerVertTopLeftCornerX = (panelDimension.width / 2) - (singleFaceDimension * 2); // centered for face 1
        int centerVertTopLeftCornerY = (panelDimension.height / 2) - (singleFaceDimension / 2); // centered for face 1
        int nextCubeFaceDistance = singleFaceDimension;
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY - nextCubeFaceDistance, 0);
        paintFace(g, centerVertTopLeftCornerX, centerVertTopLeftCornerY, 1);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY, 2);
        paintFace(g, centerVertTopLeftCornerX + 2 * nextCubeFaceDistance, centerVertTopLeftCornerY, 3);
        paintFace(g, centerVertTopLeftCornerX + 3 * nextCubeFaceDistance, centerVertTopLeftCornerY, 4);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY + nextCubeFaceDistance, 5);
    }

    private void paintFace(Graphics g, int topLeftCornerX, int topLeftCornerY, int faceIndex) {
        g.setColor(Color.BLACK);
        g.fillRect(topLeftCornerX, topLeftCornerY, singleFaceDimension + stickerBorderThickness,
                singleFaceDimension + stickerBorderThickness);
        for(int i = 0; i < 9; i++) {
            paintSticker(g, topLeftCornerX + ((i % 3) * singleFaceDimension / 3),
                    topLeftCornerY + ((i / 3) * singleFaceDimension / 3), cubeFaces[faceIndex].getSticker(i));
        }
    }

    private void paintSticker (Graphics g, int topLeftCornerX, int topLeftCornerY, Sticker sticker) {
        Color displayColor = sticker.getColor();
        g.setColor(displayColor);
        g.fillRect(topLeftCornerX + stickerBorderThickness, topLeftCornerY + stickerBorderThickness,
                singleFaceDimension / 3 - stickerBorderThickness, singleFaceDimension / 3 - stickerBorderThickness);
    }
}
