import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TrainingPanel extends JPanel implements KeyListener {

    private BlindSolvingTrainer bst;

    private Trainer trainer;

    private CubePiece currCubePiece;

    private int currOrientation;

    private int currTarget;

    public TrainingPanel(BlindSolvingTrainer bst) {
        super();
        this.bst = bst;
        addKeyListener(this);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setFocusable(true);
                setOpaque(true);
            }
        });
    }

    public void startTrainingSession(int mode, Cube cube) {
        // trainer will be set to null when esc key is pressed
        trainer = new Trainer(mode, cube, this);
    }

    // target and orientation both correspond to the index of the sticker
    // respectively of which they are indicating
    public void setPieceToBeDisplayed(int target, int orientation, CubePiece cubePiece) {
        //System.out.println(cubePiece.getPieceStickers()[target]);
        currCubePiece = cubePiece;
        currTarget = target;
        currOrientation = orientation;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPiece(g);
//        g.setColor(Color.BLACK);
//        g.fillRect(0,0, 40, 40);
//        g.setColor(Color.YELLOW);
//        g.drawString("Hello",0,0);
    }

    private void paintPiece(Graphics g) {
        if (currCubePiece.getPieceType() == PieceType.CORNER) {
            int radius = getHeight() / 7;
            int apothem = (int) (radius * Math.cos(Math.PI / 6));
            double stickerVerticalGap = radius * 0.1;
            double stickerHorizontalGap = (stickerVerticalGap * Math.sqrt(3));

            Polygon face1 = new Polygon();
            face1.addPoint(getWidth() / 2, getHeight() / 2); // center of the hexagon
            face1.addPoint((int) (getWidth() / 2 + apothem), getHeight() / 2 - radius / 2);
            face1.addPoint(getWidth() / 2, getHeight() / 2 - radius); // top of the hexagon
            face1.addPoint((int) (getWidth() / 2 - apothem), getHeight() / 2 - radius / 2);

            Polygon sticker1 = new Polygon();
            sticker1.addPoint(getWidth() / 2, (int) (getHeight() / 2 - stickerVerticalGap));
            sticker1.addPoint((int) (getWidth() / 2 + apothem - stickerHorizontalGap), getHeight() / 2 - radius / 2);
            sticker1.addPoint(getWidth() / 2 , (int) (getHeight() / 2 - radius + stickerVerticalGap));
            sticker1.addPoint((int) (getWidth() / 2 - apothem + stickerHorizontalGap), getHeight() / 2 - radius / 2);

            Polygon face2 = new Polygon();
            face2.addPoint(getWidth() / 2, getHeight() / 2);
            face2.addPoint(getWidth() / 2, getHeight() / 2 + radius);
            face2.addPoint((int) (getWidth() / 2 + apothem), getHeight() / 2 + radius / 2);
            face2.addPoint((int) (getWidth() / 2 + apothem), getHeight() / 2 - radius / 2);

            Polygon sticker2 = new Polygon();
            sticker2.addPoint((int) (getWidth() / 2 + stickerVerticalGap / 2 * Math.sqrt(3)), (int) (getHeight() / 2 + stickerVerticalGap / 2));
            sticker2.addPoint((int) (getWidth() / 2 + stickerHorizontalGap / 2), (int) (getHeight() / 2 + radius - stickerHorizontalGap / 2 * Math.sqrt(3)));
            sticker2.addPoint((int) (getWidth() / 2 + apothem - stickerVerticalGap / 2 * Math.sqrt(3)), (int) (getHeight() / 2 + radius / 2 - stickerVerticalGap / 2));
            sticker2.addPoint((int) (getWidth() / 2 + apothem - stickerHorizontalGap / 2), (int) (getHeight() / 2 - radius / 2 + stickerHorizontalGap / 2 * Math.sqrt(3)));


            Polygon face3 = new Polygon();
            face3.addPoint(getWidth() / 2, getHeight() / 2);
            face3.addPoint(getWidth() / 2, getHeight() / 2 + radius);
            face3.addPoint((int) (getWidth() / 2 - apothem), getHeight() / 2 + radius / 2);
            face3.addPoint((int) (getWidth() / 2 - apothem), getHeight() / 2 - radius / 2);

            Polygon sticker3 = new Polygon();
            sticker3.addPoint((int) (getWidth() / 2 - stickerVerticalGap / 2 * Math.sqrt(3)), (int) (getHeight() / 2 + stickerVerticalGap / 2));
            sticker3.addPoint((int) (getWidth() / 2 - stickerHorizontalGap / 2), (int) (getHeight() / 2 + radius - stickerHorizontalGap / 2 * Math.sqrt(3)));
            sticker3.addPoint((int) (getWidth() / 2 - apothem + stickerVerticalGap / 2 * Math.sqrt(3)), (int) (getHeight() / 2 + radius / 2 - stickerVerticalGap / 2));
            sticker3.addPoint((int) (getWidth() / 2 - apothem + stickerHorizontalGap / 2), (int) (getHeight() / 2 - radius / 2 + stickerHorizontalGap / 2 * Math.sqrt(3)));

            g.setColor(Color.BLACK);
            g.fillPolygon(face1);
            g.fillPolygon(face2);
            g.fillPolygon(face3);
            g.setColor(currCubePiece.getPieceStickers()[currOrientation].getDisplayColor());
            g.fillPolygon(sticker1);
            g.setColor(currCubePiece.getPieceStickers()[(currOrientation + 1) % 3].getDisplayColor());
            g.fillPolygon(sticker2);
            g.setColor(currCubePiece.getPieceStickers()[(currOrientation + 2) % 3].getDisplayColor());
            g.fillPolygon(sticker3);

            paintCornerTargetIndicator(g);

        }
    }

    private void paintCornerTargetIndicator(Graphics g) {
        int indicatorLoc = Math.abs(currTarget + (3 - currOrientation)) % 3;
        System.out.println("currOrientation " + currOrientation + " currTarget: " + currTarget );
        Polygon indicator = new Polygon();
        int indicatorHeight = getHeight() / 20;
        int distFromFromCenter = getHeight() / 5;
        switch (indicatorLoc) {
            case 0:
                indicator.addPoint(getWidth() / 2, (int) (getHeight() / 2 - distFromFromCenter));
                indicator.addPoint((int) (getWidth() / 2 - indicatorHeight / Math.sqrt(3)), (int) (getHeight() / 2 - distFromFromCenter - indicatorHeight));
                indicator.addPoint((int) (getWidth() / 2 + indicatorHeight / Math.sqrt(3)), (int) (getHeight() / 2 - distFromFromCenter - indicatorHeight));
                break;
            case 1:
                indicator.addPoint((int) (getWidth() / 2 + distFromFromCenter / 2 * Math.sqrt(3)), (int) (getHeight() / 2 + distFromFromCenter / 2));
                indicator.addPoint((int) (getWidth() / 2 + distFromFromCenter / 2 * Math.sqrt(3) + indicatorHeight / Math.sqrt(3)), (int) (getHeight() / 2 + distFromFromCenter / 2 + indicatorHeight));
                indicator.addPoint((int) (getWidth() / 2 + distFromFromCenter / 2 * Math.sqrt(3) + 2 * indicatorHeight / Math.sqrt(3)), (int) (getHeight() / 2 + distFromFromCenter / 2));
                break;
            case 2:
                indicator.addPoint((int) (getWidth() / 2 - distFromFromCenter / 2 * Math.sqrt(3)), (int) (getHeight() / 2 + distFromFromCenter / 2));
                indicator.addPoint((int) (getWidth() / 2 - distFromFromCenter / 2 * Math.sqrt(3) - indicatorHeight / Math.sqrt(3)), (int) (getHeight() / 2 + distFromFromCenter / 2 + indicatorHeight));
                indicator.addPoint((int) (getWidth() / 2 - distFromFromCenter / 2 * Math.sqrt(3) - 2 * indicatorHeight / Math.sqrt(3)), (int) (getHeight() / 2 + distFromFromCenter / 2));
                break;
        }
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(indicator);
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to exit training mode?", "Blind Solving Trainer",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.CANCEL_OPTION) {
                return;
            }
            trainer = null;
            bst.changePanel(0);
            return;
        }
        // Basically press any key to go next
        if (trainer.isPaused()) {
            trainer.startPiece();
            return; // :) ur mom
        }
        char keyChar = Character.toUpperCase(e.getKeyChar());
        if (keyChar >= 65 && keyChar <= 90) {
            System.out.println(trainer.checkAnswer(keyChar));
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
