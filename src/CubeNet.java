import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CubeNet extends JPanel implements MouseListener {

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
    private int singleStickerDimension;
    private int stickerBorderThickness;

    // for mouseListener
    private ArrayList<Point> clickedPoints = new ArrayList<>();
    private Sticker pressedSticker;

    /**
     * Instantiates a CubeNet object with default CubeFace colors
     *
     */
    public CubeNet() {
        super();
        setOpaque(true);
        addMouseListener(this);
        setPreferredSize(new Dimension(1000, 700));
        cubeFaces = new CubeFace[6];
        singleFaceDimension = getSize().width / 5;
        //setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        for (int i = 0; i < 6; i++) {
            cubeFaces[i] = new CubeFace(defaultColorScheme[i]);
            cubeFaces[i].setAllMemosToDefault(i);
        }
    }

    public void updatePanelDimension() {
        panelDimension = getSize();
        singleFaceDimension = panelDimension.width / 6;
        singleStickerDimension = singleFaceDimension / 3;
        stickerBorderThickness = singleFaceDimension / 30;
    }

    public void setFaceColor(int faceIndex, Color color) {
        cubeFaces[faceIndex].setColor(color);
    }

    @Override
    public void paintComponent(Graphics g) {
        updatePanelDimension();
        setBackground(new Color(163, 138, 150));
        paintCubeNet(g);
    }



    private void paintCubeNet(Graphics g) {
        int centerVertTopLeftCornerX = (panelDimension.width / 2) - (singleFaceDimension * 2); // centered for face 1
        int centerVertTopLeftCornerY = (panelDimension.height / 2) - (singleFaceDimension / 2); // centered for face 1
        int nextCubeFaceDistance = singleFaceDimension;
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY - nextCubeFaceDistance, 0);
        paintFace(g, centerVertTopLeftCornerX, centerVertTopLeftCornerY, 1);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY, 2);
        paintFace(g, centerVertTopLeftCornerX + 2 * nextCubeFaceDistance, centerVertTopLeftCornerY, 3);
        paintFace(g, centerVertTopLeftCornerX + 3 * nextCubeFaceDistance, centerVertTopLeftCornerY, 4);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY + nextCubeFaceDistance, 5);
        //paintClicks(g);
    }

    private void paintClicks(Graphics g) {
        for (Point p : clickedPoints) {
            g.drawOval(p.x, p.y, 10, 10);
        }
    }

    private void paintFace(Graphics g, int faceStartX, int faceStartY, int faceIndex) {
        g.setColor(Color.BLACK);
        CubeFace cubeFace = cubeFaces[faceIndex];
        cubeFace.setBounds(faceStartX, faceStartY,
                singleFaceDimension + stickerBorderThickness,
                singleFaceDimension + stickerBorderThickness);
        g.fillRect(cubeFace.x, cubeFace.y, cubeFace.width, cubeFace.height);
        for(int i = 0; i < 9; i++) {
            Sticker sticker = cubeFace.getSticker(i);
            int stickerStartX = faceStartX + ((i % 3) * singleFaceDimension / 3);
            int stickerStartY = faceStartY + ((i / 3) * singleFaceDimension / 3);
            paintSticker(g, stickerStartX, stickerStartY, sticker);
            if (i != 4) {
                paintMemo(g, sticker);
            }
        }
    }

    private void paintSticker (Graphics g, int stickerStartX, int stickerStartY, Sticker sticker) {
        Color displayColor = sticker.getColor();
        g.setColor(displayColor);
//        sticker.setLocation(stickerStartX + stickerBorderThickness, stickerStartY - stickerBorderThickness);
//        sticker.setSize(singleStickerDimension - stickerBorderThickness, singleStickerDimension - stickerBorderThickness);

        sticker.setBounds(stickerStartX + stickerBorderThickness, stickerStartY + stickerBorderThickness,
                singleFaceDimension / 3 - stickerBorderThickness,
                singleFaceDimension / 3 - stickerBorderThickness);
        g.fillRect(sticker.x, sticker.y, sticker.width, sticker.height);
    }

    private void paintMemo(Graphics g, Sticker sticker) {
        Font font = new Font("Helvetica", Font.PLAIN, 30);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        String memo = sticker.getMemo() + "";
        int stringWidth = fm.stringWidth(memo);
        int stringHeight = fm.getAscent();
        g.setColor(Color.BLACK);
        g.drawString(memo, sticker.x + (singleStickerDimension - stickerBorderThickness) / 2
                        - stringWidth / 2,
                sticker.y + (singleStickerDimension - stickerBorderThickness) / 2
                        + stringHeight / 2);
    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point clickLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(clickLoc, this);
        Sticker clickedSticker;
        //repaint();
        //clickedPoints.add(clickLoc);
        for (CubeFace face : cubeFaces) {
            if (face.contains(clickLoc)) {
                clickedSticker = face.findClickedSticker(clickLoc);
                if (clickedSticker == null) {
                    return;
                }
                pressedSticker = clickedSticker;
                return;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point clickLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(clickLoc, this);
        Sticker clickedSticker;
        //repaint();
        //clickedPoints.add(clickLoc);
        for (CubeFace face : cubeFaces) {
            if (face.contains(clickLoc)) {
                clickedSticker = face.findClickedSticker(clickLoc);
                if (clickedSticker == null || clickedSticker != pressedSticker) {
                    return;
                }
                clickedSticker.setColor(Color.CYAN);
                repaint();
                return;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
