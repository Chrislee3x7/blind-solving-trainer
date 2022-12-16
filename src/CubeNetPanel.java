import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CubeNetPanel extends JPanel implements MouseListener, KeyListener {

    private Cube cube;

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
    private Sticker editingSticker;
    // for mouseListener to help with press and release comparison
    private Sticker pressedSticker;

    private StickerType memoEditMode;
    private boolean editingMemo = false;



    private ScheduledExecutorService stickerBlinkExecutorService;

    /**
     * Instantiates a CubeNetPanel object with default CubeFace colors
     *
     */
    public CubeNetPanel() {
        super();
        cube = new Cube();
        addMouseListener(this);
        addKeyListener(this);
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                turnOffEditMode(editingSticker);
            }
        });

        setMemoSchemeToSaved();
        setMemoEditMode(StickerType.CORNER);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                setOpaque(true);
                setFocusable(true);
                requestFocusInWindow();
            }
        });
    }

    public void setMemoSchemeToDefault() {
        if (memoEditMode == StickerType.CORNER) {
            setCornerMemoSchemeToDefault();
        } else if (memoEditMode == StickerType.EDGE) {
            setEdgeMemoSchemeToDefault();
        }
    }

    public void setEdgeMemoSchemeToDefault() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset the edge memo scheme to Default?\nYou will lose " +
                        "the current edge memo scheme", "Blind Solving Trainer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        cube.resetEdgeMemos();
        saveMemoScheme();
        setMemoSchemeToSaved();
    }

    public void setCornerMemoSchemeToDefault() {
        int result = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to reset the corner memo scheme to Default?\nYou will lose " +
                        "the current corner memo scheme", "Blind Solving Trainer",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
            return;
        }
        cube.resetCornerMemos();
        saveMemoScheme();
        setMemoSchemeToSaved();
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
        for (CubeFace face : cube.getCubeFaces()) {
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

    public void setMemoSchemeToSaved() {
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
        for (CubeFace face : cube.getCubeFaces()) {
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

    public void updatePanelDimension() {
        panelDimension = getSize();
        singleFaceDimension = panelDimension.width / 5;
        if (panelDimension.height / 4 < singleFaceDimension) {
            singleFaceDimension = (int) (panelDimension.height / 4);
        }
        singleStickerDimension = singleFaceDimension / 3;
        stickerBorderThickness = singleFaceDimension / 30;
        singleFaceDimension = (3 * singleStickerDimension + 3 * stickerBorderThickness);
        //System.out.println("sfd: " + singleFaceDimension);
    }

    public void setMemoEditMode(StickerType memoEditMode) {
        this.memoEditMode = memoEditMode;
    }

    // Painting the JPanel methods
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updatePanelDimension();
        setBackground(new Color(175, 208, 191));
        paintCubeNet(g);
    }

    private void paintCubeNet(Graphics g) {
        //System.out.println(panelDimension);
        int centerVertTopLeftCornerX = (panelDimension.width / 2) - (singleFaceDimension * 2); // centered for face 1
        int centerVertTopLeftCornerY = (panelDimension.height / 2) - (singleFaceDimension / 2); // centered for face 1
        int nextCubeFaceDistance = singleFaceDimension;
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY - nextCubeFaceDistance, 0);
        //hacky extra paintFace needed to fix the weird paint issue
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY - nextCubeFaceDistance, 0);
        paintFace(g, centerVertTopLeftCornerX, centerVertTopLeftCornerY, 1);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY, 2);
        paintFace(g, centerVertTopLeftCornerX + 2 * nextCubeFaceDistance, centerVertTopLeftCornerY, 3);
        paintFace(g, centerVertTopLeftCornerX + 3 * nextCubeFaceDistance, centerVertTopLeftCornerY, 4);
        paintFace(g, centerVertTopLeftCornerX + 1 * nextCubeFaceDistance, centerVertTopLeftCornerY + nextCubeFaceDistance, 5);
        //paintClicks(g);
    }

    private void paintFace(Graphics g, int faceStartX, int faceStartY, int faceIndex) {
        g.setColor(Color.BLACK);
        CubeFace cubeFace = cube.getCubeFaces()[faceIndex];
        cubeFace.setBounds(faceStartX, faceStartY,
                singleFaceDimension + stickerBorderThickness,
                singleFaceDimension + stickerBorderThickness);
        g.fillRect(cubeFace.x, cubeFace.y, cubeFace.width, cubeFace.height);
        g.setColor(Color.magenta);
        //g.drawRect(cubeFace.x, cubeFace.y, cubeFace.width, cubeFace.height);
        for(int i = 0; i < 9; i++) {
            Sticker sticker = cubeFace.getSticker(i);
            int stickerStartX = faceStartX + ((i % 3) * singleFaceDimension / 3);
            int stickerStartY = faceStartY + ((i / 3) * singleFaceDimension / 3);
            paintSticker(g, stickerStartX, stickerStartY, sticker);
//            if (i != 4 && memoEditMode != null) {
//                if (memoEditMode.equals("Corners")  && i % 2 == 0) {
//                    paintMemo(g, sticker);
//                } else if (memoEditMode.equals("Edges")  && i % 2 == 1) {
//                    paintMemo(g, sticker);
//                }
//            }
        }
    }

    private void paintSticker (Graphics g, int stickerStartX, int stickerStartY, Sticker sticker) {
        Color displayColor = sticker.getDisplayColor();
        g.setColor(displayColor);
//        sticker.setLocation(stickerStartX + stickerBorderThickness, stickerStartY - stickerBorderThickness);
//        sticker.setSize(singleStickerDimension - stickerBorderThickness, singleStickerDimension - stickerBorderThickness);

        sticker.setBounds(stickerStartX + stickerBorderThickness, stickerStartY + stickerBorderThickness,
                (singleFaceDimension - (3 * stickerBorderThickness )) / 3,
                (singleFaceDimension  - (3 * stickerBorderThickness)) / 3);
        g.fillRect(sticker.x, sticker.y, sticker.width, sticker.height);
        paintMemo(g, sticker);
    }

    private void paintMemo(Graphics g, Sticker sticker) {
        if (sticker.getStickerType() != memoEditMode) {
            return;
        }
        FontMetrics fm = g.getFontMetrics();
        String memo = sticker.getMemo() + "";
        int stringWidth = fm.stringWidth(memo);
        int stringHeight = fm.getAscent();
        if (sticker.getConflicted()) {
            g.setColor(new Color(250, 94, 94));
            g.setFont(new Font("Helvetica", Font.BOLD, singleStickerDimension / 2));
        } else {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Helvetica", Font.PLAIN, singleStickerDimension / 2));
        }
        g.drawString(memo, sticker.x + (singleStickerDimension) / 2
                        - stringWidth / 2,
                sticker.y + (singleStickerDimension) / 2
                        + stringHeight / 2);
    }

    public void turnOffEditMode(Sticker sticker) {
        if (sticker == null) {
            return;
        }
        stickerBlinkExecutorService.shutdownNow();
        sticker.setEditModeOff();
        sticker.setDisplayColor(sticker.getTrueColor());
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                repaint();
            }});
        //System.out.println("turned off edit mode");
        editingMemo = false;
        editingSticker = null;
    }

    public void turnOnEditMode(Sticker sticker) {
        if (sticker == null) {
            return;
        }
        editingMemo = true;
        sticker.setEditModeOn();
        stickerBlinkExecutorService = Executors.newSingleThreadScheduledExecutor();
        stickerBlinkExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if (editingSticker.getDisplayColor() == editingSticker.getTrueColor()) {
                    editingSticker.setDisplayColor(Sticker.BLINK_COLOR);
                }
                else {
                    editingSticker.setDisplayColor(editingSticker.getTrueColor());
                }
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        repaint();
                    }});
            }
        }, 0, 500, TimeUnit.MILLISECONDS);

    }

    // MouseListener methods
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        requestFocus();

        Point clickLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(clickLoc, this);

        for (CubeFace face : cube.getCubeFaces()) {
            if (!face.contains(clickLoc)) {
                // if click location is not within this face, skip the logic for this face
                continue;
            }
            // otherwise get the sticker for this click loc
            pressedSticker = face.findClickedSticker(clickLoc);
            return;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // no matter what, if there was a selected sticker before this click, it should no longer be selected
        if (editingSticker != null) {
            turnOffEditMode(editingSticker);
            System.out.println("edit mode turned off");
            return;
        }

        Point clickLoc = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(clickLoc, this);
        Sticker releasedSticker;
            for (CubeFace face : cube.getCubeFaces()) {
                if (!face.contains(clickLoc)) {
                    continue;
                }
                releasedSticker = face.findClickedSticker(clickLoc);
                if (editingSticker == null && releasedSticker != null && releasedSticker == pressedSticker && pressedSticker.getStickerType() == memoEditMode) {
                    // if clickedSticker is not null and the stickerType matches the memoEditMode
                    editingSticker = releasedSticker;
                    pressedSticker = null;
                    turnOnEditMode(editingSticker);
                }
            }
            //clickedSticker.setColor(Color.CYAN);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    // KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
        if (editingMemo) {
            String s = e.getKeyChar() + "";
            s = s.toUpperCase();
            char c = s.charAt(0);
            if ((c >= 65 && c <= 90) && c != editingSticker.getMemo()) {
                // if c is different from what it was previously, check if any conflicts
                // are solved
                ArrayList<Sticker> persistentConflicts = cube.getStickerConflicts(editingSticker);
                // if there are more than 2 conflicts, others still remain conflicted
                boolean conflictPersist = persistentConflicts.size() > 2;
                for (Sticker sticker : persistentConflicts) {
                    sticker.setConflicted(conflictPersist);
                }

                editingSticker.setMemo(c);
                // check for conflict stickers
                ArrayList<Sticker> newConflicts = cube.getStickerConflicts(editingSticker);
                // if this is the only sticker with the new memo, no new conflicts
                boolean newConflict = newConflicts.size() > 1;
                for (Sticker sticker : newConflicts) {
                    sticker.setConflicted(newConflict);
                }
                saveMemoScheme();
                repaint();
            }
            turnOffEditMode(editingSticker);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
